package de.mrjulsen.mcdragonlib.internal;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class DragonLibClientProxyWrapper {

    public static void setup(final FMLClientSetupEvent event) {
        DragonLibClientProxy.setup(event);
    }
    
}
