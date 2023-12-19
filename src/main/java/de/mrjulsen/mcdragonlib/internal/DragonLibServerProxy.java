package de.mrjulsen.mcdragonlib.internal;

import de.mrjulsen.mcdragonlib.DragonLib;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class DragonLibServerProxy {

    public static void setup(final FMLCommonSetupEvent event) {
        DragonLib.LOGGER.info(" -=- 🐉 Init Mod DRAGON LIB by MRJULSEN 🐉 -=- ");
    }

}
