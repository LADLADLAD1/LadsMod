package LadsMod.Util;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigManager {
    public static Configuration config;
    public static boolean myBoolean = false;
    public static Float R = 0.7F;
    public static Float G = 0.2F;
    public static Float B = 0.1F;
    public static Float A = 0.7F;
    public static Integer COLOR = new ColorUtil(R, G, B, A).toHex();
    public static Integer COLOR_FULL_OPACITY = new ColorUtil(R, G, B, 1).toHex();
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
        myBoolean = config.getBoolean("myBoolean","general",false,"Look! This is a comment.");
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
