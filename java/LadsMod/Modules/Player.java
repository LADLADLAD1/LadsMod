package LadsMod.Modules;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Player {
    public static ArrayList<Player> tickHistory = new ArrayList<Player>();
    public static int maxSavedTicks = 20;
    private boolean onGround = false;
    private boolean isSprinting = Minecraft.getMinecraft().thePlayer.isSprinting();
    private int airtime = 0;

    public static void savePlayerState(Player player) {
        tickHistory.add(player);
        if (tickHistory.size() > maxSavedTicks)
            tickHistory.remove(0);
    }


    public static Player getLatest() {
        if (tickHistory.isEmpty()) return null;
        return tickHistory.get(tickHistory.size() - 1);
    }

    public static Player getBeforeLatest() {
        if(tickHistory.size() < 2) return null;
        return tickHistory.get(tickHistory.size() - 2);
    }

    public Player getPrevious() {
        if (!tickHistory.contains(this)) return null;
        int i = tickHistory.indexOf(this);
        if (i == 0) return null;
        return tickHistory.get(i - 1);
    }
    public boolean isSprinting() {return isSprinting;}
    public boolean isOnGround() {
        return onGround;
    }

    public Player setOnGround(boolean onGround) {
        this.onGround = onGround;
        return this;
    }


    public int getAirtime() {
        return airtime;
    }

    public Player buildAndSave() {
        Player.savePlayerState(this);
        Player prev = getPrevious();
        if (prev != null) {
            if (prev.onGround) airtime = 0;
            else airtime = prev.airtime + 1;
            if (prev.onGround && !onGround) airtime = 1;
        }
        return this;
    }

}
