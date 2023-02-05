package LadsMod.Modules;

import LadsMod.Listeners.EventListener;
import LadsMod.Main;
import LadsMod.Util.ColorUtil;
import LadsMod.Util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TeleportLine {
    private static boolean lastPress = false;
    public static boolean hotBarScroll = true;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKey(KeyInputEvent event) {
        hotBarScroll = true;
        if (Main.extendLine.isKeyDown()) {
            hotBarScroll = false;
            lastPress = true;
        }
        if (Main.extendLine.isPressed()) {
            hotBarScroll = false;
            lastPress = true;
        }
        if (lastPress && hotBarScroll) {
            lastPress = false;
            //do stuff when released
            Vec3 pos = getPos(EventListener.dist);
            EventListener.dist=5;
            if (Minecraft.getMinecraft().isSingleplayer())
                Minecraft.getMinecraft().thePlayer.setPosition(pos.xCoord,pos.yCoord,pos.zCoord);
            else Minecraft.getMinecraft().thePlayer.sendChatMessage("/tp "+String.format("%.1f",pos.xCoord)+" "+String.format("%.1f",pos.yCoord)+" "+String.format("%.1f",pos.zCoord));
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void handleMouseInput(MouseEvent event) {
        if (!hotBarScroll) {
            int scrollDelta = event.dwheel;
            if (scrollDelta > 0) {
                try {
                    event.setCanceled(true);
                } catch(IllegalArgumentException e) {e.printStackTrace();}
                //do stuff here when scroll up
                EventListener.dist++;
                EventListener.dist = clamp(EventListener.dist,1,50);
            } else if (scrollDelta < 0) {
                try {
                    event.setCanceled(true);
                } catch(IllegalArgumentException e) {e.printStackTrace();}
                //do stuff here when scroll down
                EventListener.dist--;
                EventListener.dist = clamp(EventListener.dist,1,50);
            }
        }
    }
    private int clamp(int value, int min, int max) {return Math.max(min, Math.min(max, value));}
    public static void drawPos(RenderWorldLastEvent event,Integer distance) {
        RenderUtil.renderBlockMask(event.partialTicks,getPos(distance), ColorUtil.hex(3,144,252,0.5F));
    }
    public static Vec3 getPos(int dist) {
        Vec3 lookVec = Minecraft.getMinecraft().thePlayer.getLookVec();
        Vec3 playerPos = Minecraft.getMinecraft().thePlayer.getPositionVector();
        return playerPos.addVector(lookVec.xCoord * dist, lookVec.yCoord * dist +1.5F, lookVec.zCoord * dist);
    }
}
