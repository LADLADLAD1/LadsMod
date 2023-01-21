package LadsMod.Listeners;

import LadsMod.Modules.RayTeleport;
import LadsMod.Main;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class KeyInputHandler {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKey(KeyInputEvent event) {
        if (Main.zipKey.isPressed()) {
            RayTeleport.SightTeleport();
        }
        if (Main.openDebug.isPressed()) {
            Minecraft.getMinecraft().refreshResources();
        }
    }
}