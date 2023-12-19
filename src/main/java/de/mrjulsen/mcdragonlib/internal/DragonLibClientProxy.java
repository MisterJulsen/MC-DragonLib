package de.mrjulsen.mcdragonlib.internal;

import de.mrjulsen.mcdragonlib.client.gui.GuiUtils;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class DragonLibClientProxy {

    public static void setup(final FMLClientSetupEvent event) {
        GuiUtils.init();
    }
    
}
