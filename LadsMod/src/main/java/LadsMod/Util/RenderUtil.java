package LadsMod.Util;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

/**
 * Utility class for all things rendering in my mod.
 * @author SesuMoe
 */
public class RenderUtil {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	private static void begin() {
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		
		GlStateManager.disableTexture2D();
		GlStateManager.disableAlpha();
		GlStateManager.disableDepth();
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	}
	
	private static void end() {
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
	}
	
	/**
	 * Draws a box in 2d space.
	 * @param color Float4 array that represents RGBA from 0 to 1
	 */
	public static void drawBox(int x, int y, int width, int height, int color) {
		Tessellator tess = Tessellator.getInstance();
		WorldRenderer wr = tess.getWorldRenderer();
		
		float[] arr = new ColorUtil(color).getFloats();
		
		begin();
		
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

		wr.pos(x, y, 0).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(x, y + height, 0).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		
		wr.pos(x + width, y + height, 0).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(x + width, y, 0).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		
		tess.draw();
		
		end();
	}
	
	public static void renderLine(float partialTicks, Vec3 start, Vec3 end, float size, int color) {
		if(start == null || end == null || mc.theWorld == null)
			return;
		
		Tessellator tess = Tessellator.getInstance();
		WorldRenderer wr = tess.getWorldRenderer();
		
		Vec3 playerOrigin = mc.thePlayer.getPositionEyes(partialTicks).subtract(0.0, mc.thePlayer.getEyeHeight(), 0.0);
		float[] arr = new ColorUtil(color, false).getFloats();
		
		begin();
		
		GlStateManager.translate(-playerOrigin.xCoord, -playerOrigin.yCoord, -playerOrigin.zCoord);
		GL11.glLineWidth(size);
		
		wr.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

		wr.pos(start.xCoord, start.yCoord, start.zCoord).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(end.xCoord, end.yCoord, end.zCoord).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		
		tess.draw();

		end();
	}
	
	/**
	 * Renders a centered box in 3d space. Floats RGBA from 0 to 1
	 */
	public static void renderCenteredBox(float partialTicks, Vec3 pos, float size, int color) {
		if(pos == null || mc.theWorld == null)
			return;
		
		Tessellator tess = Tessellator.getInstance();
		WorldRenderer wr = tess.getWorldRenderer();
		
		Vec3 playerOrigin = mc.thePlayer.getPositionEyes(partialTicks).subtract(0.0, mc.thePlayer.getEyeHeight(), 0.0);
		
		float[] arr = new ColorUtil(color).getFloats();
		
		begin();
		
		GlStateManager.translate(-playerOrigin.xCoord, -playerOrigin.yCoord, -playerOrigin.zCoord);
		wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		
		float hSize = size / 2.0f;
		
		wr.pos(pos.xCoord - hSize, pos.yCoord + hSize, pos.zCoord - hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord + hSize, pos.yCoord + hSize, pos.zCoord - hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord + hSize, pos.yCoord - hSize, pos.zCoord - hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord - hSize, pos.yCoord - hSize, pos.zCoord - hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		
		wr.pos(pos.xCoord - hSize, pos.yCoord - hSize, pos.zCoord + hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord + hSize, pos.yCoord - hSize, pos.zCoord + hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord + hSize, pos.yCoord + hSize, pos.zCoord + hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord - hSize, pos.yCoord + hSize, pos.zCoord + hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		
		wr.pos(pos.xCoord + hSize, pos.yCoord + hSize, pos.zCoord - hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord + hSize, pos.yCoord + hSize, pos.zCoord + hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord + hSize, pos.yCoord - hSize, pos.zCoord + hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord + hSize, pos.yCoord - hSize, pos.zCoord - hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		
		wr.pos(pos.xCoord - hSize, pos.yCoord + hSize, pos.zCoord + hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord - hSize, pos.yCoord + hSize, pos.zCoord - hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord - hSize, pos.yCoord - hSize, pos.zCoord - hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord - hSize, pos.yCoord - hSize, pos.zCoord + hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		
		wr.pos(pos.xCoord + hSize, pos.yCoord + hSize, pos.zCoord - hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord - hSize, pos.yCoord + hSize, pos.zCoord - hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord - hSize, pos.yCoord + hSize, pos.zCoord + hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord + hSize, pos.yCoord + hSize, pos.zCoord + hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		
		wr.pos(pos.xCoord + hSize, pos.yCoord - hSize, pos.zCoord + hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord - hSize, pos.yCoord - hSize, pos.zCoord + hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord - hSize, pos.yCoord - hSize, pos.zCoord - hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		wr.pos(pos.xCoord + hSize, pos.yCoord - hSize, pos.zCoord - hSize).color(arr[0], arr[1], arr[2], arr[3]).endVertex();
		
		tess.draw();

		end();
	}
	
	/**
	 * Renders a colored mask box around a block.
	 * @param pos Block position
	 */
	public static void renderBlockMask(float partialTicks, BlockPos pos, int color) {
		renderCenteredBox(partialTicks, new Vec3(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f), 1.0f, color);
	}
	public static void renderBlockMask(float partialTicks, Vec3 pos, int color) {
		renderCenteredBox(partialTicks, pos, 1.0f, color);
	}
}