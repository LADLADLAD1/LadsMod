package LadsMod.Guis;

import LadsMod.Util.ConfigManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

/**
 * Configuration Factory class
 * @author xilef11
 */

public class ModGuiConfig extends GuiConfig {
    public ModGuiConfig(GuiScreen screen) {
        super(screen, (new ConfigElement(ConfigManager.config.getCategory("general"))).getChildElements(),
                "ladsmod",
                false,
                false,
                GuiConfig.getAbridgedConfigPath(ConfigManager.config.toString()));
    }
}
