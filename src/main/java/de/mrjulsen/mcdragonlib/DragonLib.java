package de.mrjulsen.mcdragonlib;

import com.mojang.logging.LogUtils;

import de.mrjulsen.mcdragonlib.internal.ClientProxy;
import de.mrjulsen.mcdragonlib.internal.ServerProxy;
import de.mrjulsen.mcdragonlib.internal.content.ModBlocks;
import de.mrjulsen.mcdragonlib.internal.content.ModItems;
import de.mrjulsen.mcdragonlib.setup.IProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DragonLibConstants.DRAGONLIB_MODID)
public final class DragonLib {
    
    public static final Logger LOGGER = LogUtils.getLogger();
    public final IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public DragonLib() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);

        ModBlocks.register(eventBus);
        ModItems.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("#####################################################");
        LOGGER.info("#       🐉 Init Mod DRAGON LIB by MRJULSEN 🐉       #");
        LOGGER.info("#####################################################");

        PROXY.setup(event);
    }
}
