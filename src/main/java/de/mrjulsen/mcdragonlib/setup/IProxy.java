package de.mrjulsen.mcdragonlib.setup;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public interface IProxy {
    void setup(FMLCommonSetupEvent event);
}