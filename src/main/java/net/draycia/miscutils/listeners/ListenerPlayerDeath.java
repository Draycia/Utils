package net.draycia.miscutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ListenerPlayerDeath implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().hasPermission("miscutils.keepinventory")) {
            event.setKeepInventory(true);
            event.setKeepLevel(true);
        }
    }
}
