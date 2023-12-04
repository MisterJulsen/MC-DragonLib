package de.mrjulsen.mcdragonlib.internal.events;

import de.mrjulsen.mcdragonlib.DragonLibConstants;
import de.mrjulsen.mcdragonlib.utils.ScheduledTask;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.Type;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DragonLibConstants.DRAGONLIB_MODID)
public final class GameEvents {

    @SubscribeEvent
    public static void onTick(WorldTickEvent event) {
        if (event.type != Type.WORLD || event.phase != Phase.END) {
            return;
        }
        ScheduledTask.runScheduledTasks(event.world);
    }

    @SubscribeEvent
    public static void onServerStop(ServerStoppingEvent event) {
        ScheduledTask.cancelAllTasks();
    }
}
