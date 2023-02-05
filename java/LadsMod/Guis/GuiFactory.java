package LadsMod.Guis;

import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

/**
 * Configuration Factory class
 * @author xilef11
 */


public class GuiFactory implements IModGuiFactory {
    public GuiFactory() {
    }

    public void initialize(Minecraft minecraftInstance) {
    }

    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ModGuiConfig.class;
    }

    public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    public IModGuiFactory.RuntimeOptionGuiHandler getHandlerFor(IModGuiFactory.RuntimeOptionCategoryElement element) {
        return null;
    }
}
