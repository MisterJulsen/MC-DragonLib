package de.mrjulsen.mcdragonlib;

import com.mojang.logging.LogUtils;

import de.mrjulsen.mcdragonlib.internal.DragonLibClientProxy;
import de.mrjulsen.mcdragonlib.internal.DragonLibServerProxy;
import de.mrjulsen.mcdragonlib.internal.content.DragonLibBlocks;
import de.mrjulsen.mcdragonlib.internal.content.DragonLibItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DragonLibConstants.DRAGONLIB_MODID)
public final class DragonLib {
    
    public static final Logger LOGGER = LogUtils.getLogger();

    public DragonLib() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(DragonLibServerProxy::setup);
        eventBus.addListener(DragonLibClientProxy::setup);

        DragonLibBlocks.register(eventBus);
        DragonLibItems.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
