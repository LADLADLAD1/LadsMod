package LadsMod.Listeners;

import LadsMod.Main;
import LadsMod.Modules.Debug;
import LadsMod.Modules.Player;
import LadsMod.Modules.SeeBarriers;
import LadsMod.Modules.TeleportLine;
import LadsMod.Util.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

public class EventListener {
	private static Boolean sprint =false;
	private final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void showDist(RenderWorldLastEvent event) {
		if (!TeleportLine.hotBarScroll)
			TeleportLine.drawPos(event,dist);
		SeeBarriers.renderBlocks(event);
	}
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void listenHit(AttackEntityEvent event) {
		if (!(event.entity instanceof EntityPlayer)) return;
		if (!(event.target instanceof EntityPlayer)) return;
		EntityPlayer playerHit = (EntityPlayer) event.target;
		if (ticksSinceLastHit<9) return;
		if (ticksSinceLastHit>25||punchCount>3) {
			ticksSinceLastHit = 0;
			punchCount=0;
			boostType = "";
		}
		if (!playerHit.equals(latestHitPlayer)) {
			ticksSinceLastHit = 0;
			punchCount=0;
			boostType = "";
		}

		latestHitPlayer = (EntityPlayer) event.target;
		EntityPlayer player = (EntityPlayer) event.entity;

		if (!(Minecraft.getMinecraft().thePlayer.getName().equalsIgnoreCase(player.getName()))) return; //redundancy check

		int kbVal = EnchantmentHelper.getKnockbackModifier(player);
		if (Minecraft.getMinecraft().thePlayer.isSprinting()) {
			if (wState) {
				wState=false;
				kbVal++;
			}
		}
		if (boostType.length()==0) {
			boostType = "kb"+kbVal;
		} else {
			boostType = boostType+ " "+ticksSinceLastHit+"t kb"+kbVal;
		}
		ticksSinceLastHit =0;
		punchCount++;
	}
	public static Boolean wState = true;

	@SideOnly(Side.CLIENT) //if true, will hide everyone more than 10 blocks away
	@SubscribeEvent
	public void showPlayer(RenderPlayerEvent.Pre event) {
		if(ConfigManager.hideFarPlayers)
			event.setCanceled(hidePl(event.entityPlayer));
	}
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void drawHitCounter(RenderGameOverlayEvent.Post event) {
		if (!(event.type== RenderGameOverlayEvent.ElementType.HOTBAR)) return;
		if (showCustomDebug) return;
		if (!ConfigManager.showBoostTimings) return;
		GlStateManager.pushMatrix();
		if (Player.getLatest()!=null)
			this.fontRenderer.drawStringWithShadow(boostType,ConfigManager.boostXPos,ConfigManager.boostYPos, -1);
		GlStateManager.popMatrix();
	}
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if (Minecraft.getMinecraft().thePlayer != null && event.phase == TickEvent.Phase.START) {
			new Player().setOnGround(Minecraft.getMinecraft().thePlayer.onGround).buildAndSave();
			if (ticksSinceLastHit<60) ticksSinceLastHit++;
		}
		if (Minecraft.getMinecraft().thePlayer != null && event.phase == TickEvent.Phase.END) {
			if (Player.getBeforeLatest()!=null) {
				if ((!Player.getLatest().isSprinting()) && (!Player.getBeforeLatest().isSprinting()) && !Minecraft.getMinecraft().thePlayer.isSprinting()) {
					wState = true;
				}
			}
		}

		int key = Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode();
		if(Main.toggleSprint.isPressed()){
			sprint=!sprint;
			if(!sprint){if(key > 0){
					Main.toggleSprint.setKeyBindState(key, Keyboard.isKeyDown(key));}}}
		if(sprint){
			Main.toggleSprint.setKeyBindState(key, true);
		}
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
	private static Byte ticksSinceLastHit = 0;
	private static Byte punchCount = 0;
	private static String boostType ="";
	private static EntityPlayer latestHitPlayer = null;

}
