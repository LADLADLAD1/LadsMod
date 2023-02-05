package LadsMod.Util;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigManager {
    public static Configuration config;
    public static Boolean hideFarPlayers = false;
    public static Boolean showBoostTimings = true;
    public static Float R = 0.7F;
    public static Float G = 0.2F;
    public static Float B = 0.1F;
    public static Float A = 0.7F;
    public static Integer COLOR = new ColorUtil(R, G, B, A).toHex();
    public static Integer COLOR_FULL_OPACITY = new ColorUtil(R, G, B, 1).toHex();
    public static Boolean FULLBRIGHT = false;
    public static Boolean TOGGLE_SPRINT = false;
    public static Boolean text3D = false;
    public static Integer boostXPos = 1;
    public static Integer boostYPos = 10;
    public static Boolean doWoolBarrier = false;
    public ConfigManager() {
    }
    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }

    }
    private static void loadConfiguration() {
        // to add more variables to use as config, just add another line and use the myBoolean as an example
        doWoolBarrier = config.getBoolean("Wool Barriers","general",false,"Shows barriers as wool");
        boostXPos = config.getInt("Boost X Position","general",1,0,100000,"The X Position for Boost label");
        boostYPos = config.getInt("Boost y Position","general",10,0,100000,"The X Position for Boost label");
        text3D = config.getBoolean("3D Measure Text","general",false,"name is self-explanatory");
        FULLBRIGHT = config.getBoolean("Full Bright","general",false,"Experimental feature");
        TOGGLE_SPRINT = config.getBoolean("Toggle Sprint","general",false,"CHEAT OMG CHEAT ALEJUS ASKED FOR THIS");
        if (FULLBRIGHT)
            Minecraft.getMinecraft().gameSettings.gammaSetting=100;
        else
            Minecraft.getMinecraft().gameSettings.gammaSetting=1;
        showBoostTimings = config.getBoolean("Show Boost Timings","general",true,"Experimental feature");
        hideFarPlayers = config.getBoolean("Hide Far Players","general",false,"Hides players 10 blocks or further.");
        A = config.getFloat("Color A","general",0.7F,0,1,"set the alpha (opacity) value");
        B = config.getFloat("Color B","general",0.1F,0,1,"set the blue value");
        G = config.getFloat("Color G","general",0.2F,0,1,"set the green value");
        R = config.getFloat("Color R","general",0.7F,0,1,"set the red value");
        COLOR = new ColorUtil(R, G, B, A).toHex();
        COLOR_FULL_OPACITY = new ColorUtil(R, G, B, 1).toHex();

        if (config.hasChanged())
            config.save();
    }
    @SubscribeEvent
    public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals("ladsmod")) {
            loadConfiguration();
        }
    }
}
