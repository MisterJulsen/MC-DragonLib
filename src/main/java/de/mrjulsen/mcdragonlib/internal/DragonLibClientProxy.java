package de.mrjulsen.mcdragonlib.internal;

import de.mrjulsen.mcdragonlib.client.gui.GuiUtils;
import de.mrjulsen.mcdragonlib.setup.IProxy;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class DragonLibClientProxy implements IProxy {

    @Override
    public void setup(FMLCommonSetupEvent event) {
        GuiUtils.init();
    }
    
}
