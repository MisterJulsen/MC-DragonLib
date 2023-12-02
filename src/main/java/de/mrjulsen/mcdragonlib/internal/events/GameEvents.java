package de.mrjulsen.mcdragonlib.internal.events;

import de.mrjulsen.mcdragonlib.DragonLibConstants;
import de.mrjulsen.mcdragonlib.utils.ScheduledTask;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DragonLibConstants.DRAGONLIB_MODID)
public final class GameEvents {

    @SubscribeEvent
    public static void onTick(WorldTickEvent event) {
        ScheduledTask.runScheduledTasks(event.world, event.type);
    }

    @SubscribeEvent
    public static void onServerStop(ServerStoppingEvent event) {
        ScheduledTask.cancelAllTasks();
    }
}
