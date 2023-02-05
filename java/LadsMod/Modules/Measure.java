package LadsMod.Modules;

import LadsMod.Util.ConfigManager;
import LadsMod.Util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static LadsMod.Modules.MeasureInput.coordsStr;


@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class Measure extends Gui {
    public Measure() {
    }

    private BlockPos pos1;
    private BlockPos pos2;
    private final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

    public Boolean doRender() {
        return pos1 != null && pos2 != null;
    }


    public void reset() {
        coordsStr=null;pos1=pos2=null;}

    private Vec3 getVec(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    private Vec3 getMid(Vec3 pos) {
        return new Vec3(pos.xCoord + 0.5f, pos.yCoord + 0.5f, pos.zCoord + 0.5f);
    }

    public void setPos1(BlockPos pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(BlockPos pos2) {this.pos2 = pos2;}


    public Boolean hasPos1() {
        return pos1 != null;
    }

    public Boolean hasPos2() {
        return pos2 != null;
    }

    public BlockPos getPos1() {
        return pos1;
    }

    public BlockPos getPos2() {
        return pos2;
    }

    public void drawDist(float partialTicks) {
        Vec3 p1 = getMid(getVec(pos1));
        int color = -1;

        RenderUtil.renderLine(partialTicks, p1, new Vec3(pos2.getX() + 0.5f, p1.yCoord, p1.zCoord), 1, color);
        RenderUtil.renderLine(partialTicks, new Vec3(pos2.getX() + 0.5f, p1.yCoord, p1.zCoord), new Vec3(pos2.getX() + 0.5f, p1.yCoord, pos2.getZ() + 0.5f), 1, color);
        RenderUtil.renderLine(partialTicks, new Vec3(pos2.getX() + 0.5f, p1.yCoord, pos2.getZ() + 0.5f), getMid(getVec(pos2)), 1, color);
        if (ConfigManager.text3D) {
            Draw3DString(partialTicks);
        } else draw2DString();
    }
    private void draw2DString (){
        coordsStr = Math.abs(pos1.getX()-pos2.getX())+" "+Math.abs(pos1.getY()-pos2.getY())+" "+Math.abs(pos1.getZ()-pos2.getZ());
        if (Math.abs(pos1.getX()-pos2.getX())>0)
            coordsStr=Math.abs(pos1.getX()-pos2.getX())-1+" ";
        else coordsStr = 0+" ";
        coordsStr = coordsStr+Math.abs(pos1.getY()-pos2.getY())+" ";
        if (Math.abs(pos1.getZ()-pos2.getZ())>0)
            coordsStr = coordsStr+(Math.abs(pos1.getZ()-pos2.getZ())-1);
        else coordsStr = coordsStr+0;
    }
    private void Draw3DString (Float partialTicks) {
        if (Math.abs(pos1.getX()-pos2.getX())>1)
            RenderUtil.drawString3d(partialTicks,Math.abs(pos1.getX()-pos2.getX())-1+" ",new Vec3((pos1.getX()+pos2.getX())/2+0.5,pos1.getY()+0.5,pos1.getZ()+0.5),0.5f,-1);
        if (Math.abs(pos1.getZ()-pos2.getZ())>1)
            RenderUtil.drawString3d(partialTicks,Math.abs(pos1.getZ()-pos2.getZ())-1+" ",new Vec3(pos2.getX()+0.5,pos1.getY()+0.5,(pos1.getZ()+pos2.getZ())/2+0.5),0.5f,-1);
        if (Math.abs(pos1.getY()-pos2.getY())>1)
            RenderUtil.drawString3d(partialTicks,Math.abs(pos1.getY()-pos2.getY())+" ",new Vec3(pos2.getX()+0.5,(pos1.getY()+pos2.getY())/2+1,pos2.getZ()+0.5),0.5f,-1);

    }
}