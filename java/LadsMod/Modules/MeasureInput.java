package LadsMod.Modules;

import LadsMod.Main;
import LadsMod.Util.ColorUtil;
import LadsMod.Util.RenderUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MeasureInput {
    private static boolean lastPress = false;
    private static Long startPressTime;
    private static boolean notDown = true;

    private static BlockPos blockAtFirstClick;
    private static Boolean done = false;
    private static Integer timesClicked=0;
    private final Measure measure = new Measure();
    public static String coordsStr =null;
    private FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        notDown = true;
        if (Main.measure.isPressed()) {
            notDown = false;
            if (!lastPress) startPressTime=System.currentTimeMillis();
            lastPress = true;
        }
        if (Main.measure.isKeyDown()) {
            notDown = false;
            lastPress = true;
        }
        if (lastPress && notDown) {
            lastPress = false;
            //do stuff when released
            onKeyRelease();
        }
    }
    private void onKeyRelease() {
        if (timesClicked==0 && System.currentTimeMillis()-startPressTime<300)
            measure.reset();
        if (!measure.doRender())
            measure.reset();
        timesClicked=0;
        done=true;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void handleMouseInput(MouseEvent event) {
        if (!notDown) { //holding key
            BlockPos objective = Minecraft.getMinecraft().thePlayer.rayTrace(20, 1).getBlockPos();
            IBlockState blockState = Minecraft.getMinecraft().theWorld.getBlockState(objective);
            //button 0 = left click, button 1 = right click
            //listen to mouse when holding

            if(event.buttonstate && event.button==0) { //holding key, + left click
                try {
                    event.setCanceled(true);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

                if (!blockState.getBlock().isAir(Minecraft.getMinecraft().theWorld, objective)) { //check if the raytraced block is air
                    timesClicked++;
                    if (timesClicked==1) {//first click while holding
                        done=false;
                        measure.reset();
                        blockAtFirstClick = objective;
                        measure.setPos1(objective);}
                    else if (timesClicked==2) { //second click while holding
                        done=true;
                        measure.setPos2(objective);
                        System.out.println("setPos2 line1");
                    }
                }
            }
            if (!event.buttonstate &&event.button==0) {
                // mouse release
                if (!(objective.equals(blockAtFirstClick))) {
                    done=true;
                    measure.setPos2(objective);
                }
            }
        }
        //when you press in this example left mb, it will say buttonState true, button 0, when you release, it will say buttonState false, button 0
        //which is called once per click/release.
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void drawLine(RenderWorldLastEvent event) {
        if (measure.doRender())
            measure.drawDist(event.partialTicks);
        if (!notDown&&measure.hasPos1()) {
            RenderUtil.renderBlockMask(event.partialTicks,measure.getPos1(), new ColorUtil(1, 1, 0, 0.2F).toHex());
            if (measure.hasPos2()&&done)
                RenderUtil.renderBlockMask(event.partialTicks,measure.getPos2(), new ColorUtil(1, 1, 0, 0.2F).toHex());
            if (!done) {
                BlockPos bp = Minecraft.getMinecraft().thePlayer.rayTrace(20, 1).getBlockPos();
                measure.setPos2(bp);
                RenderUtil.renderBlockMask(event.partialTicks,bp, new ColorUtil(1, 1, 0, 0.2F).toHex());
            }
        }
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void drawStrDist(RenderGameOverlayEvent.Post event) {
        if (!(event.type== RenderGameOverlayEvent.ElementType.HOTBAR)) return;
        if (coordsStr!=null && measure.doRender()) {
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            GlStateManager.pushMatrix();
            this.fontRenderer.drawString(coordsStr,scaledResolution.getScaledWidth()/2,scaledResolution.getScaledHeight()/2+16, -1);
            GlStateManager.popMatrix();
        }
    }
}
