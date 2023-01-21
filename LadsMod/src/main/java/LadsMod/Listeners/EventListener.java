package LadsMod.Listeners;

import LadsMod.Modules.Debug;
import LadsMod.Modules.TeleportLine;
import LadsMod.Util.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventListener {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void showDist(RenderWorldLastEvent event) {
		if (!TeleportLine.hotBarScroll)
			TeleportLine.drawPos(event,dist);
	}

	@SideOnly(Side.CLIENT) //if true, will hide everyone more than 10 blocks away
	@SubscribeEvent
	public void showPlayer(RenderPlayerEvent.Pre event) {
		if(ConfigManager.config.getBoolean("myBoolean","general",false,"Look! This is a comment."))return;
		event.setCanceled(hidePl(event.entityPlayer));
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void customDebug(RenderGameOverlayEvent.Post event) {
		if (!(event.type== RenderGameOverlayEvent.ElementType.HOTBAR)) return;
		if (Minecraft.getMinecraft().gameSettings.showDebugInfo) {
			Minecraft.getMinecraft().gameSettings.showDebugInfo =false;
			showCustomDebug= !showCustomDebug;
		}

		if (showCustomDebug) {
			ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
			debug.renderDebugInfo(scaledresolution);
		}

	}
	public boolean hidePl(EntityPlayer player) {
		final Vec3 playerPosition = player.getPositionVector();
		final EntityPlayer thePlayer = Minecraft.getMinecraft().thePlayer;
		if (thePlayer == null) {
			return false;
		}
		final Vec3 playerLocation = thePlayer.getPositionVector();
		return playerPosition.distanceTo(playerLocation) > 10;
	}
	private static boolean showCustomDebug = false;
	private final Debug debug = new Debug(Minecraft.getMinecraft());
 	public static Integer dist =5;

}
