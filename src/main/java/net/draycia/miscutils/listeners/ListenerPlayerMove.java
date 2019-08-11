package net.draycia.miscutils.listeners;

import net.draycia.miscutils.MiscUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerPlayerMove implements Listener {

    private MiscUtils main;

    public ListenerPlayerMove(MiscUtils main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        String worldName = event.getTo().getWorld().getName();
        int yLevel = main.getConfig().getInt("VoidTeleports." + worldName);

        if (yLevel > 0 && event.getTo().getY() <= yLevel) {
            event.getPlayer().teleportAsync(event.getTo().getWorld().getSpawnLocation());
        }
    }
}
