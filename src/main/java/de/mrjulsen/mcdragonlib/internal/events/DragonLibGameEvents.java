package de.mrjulsen.mcdragonlib.internal.events;

import de.mrjulsen.mcdragonlib.DragonLibConstants;
import de.mrjulsen.mcdragonlib.utils.ScheduledTask;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DragonLibConstants.DRAGONLIB_MODID)
public final class DragonLibGameEvents {

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent event) {
        if (event.phase != Phase.END) {
            return;
        }
        ScheduledTask.runScheduledTasks();
    }

    @SubscribeEvent
    public static void onServerStop(ServerStoppingEvent event) {
        ScheduledTask.cancelAllTasks();
    }
}
