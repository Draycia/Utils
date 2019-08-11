package net.draycia.miscutils.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ListenerEntitySpawn implements Listener {

    @EventHandler
    public void onEntityspawn(EntitySpawnEvent event) {
        if (event.getEntity().getEntitySpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            if (event.getEntityType() == EntityType.PHANTOM) {
                event.setCancelled(true);
            }
        }
    }
}
