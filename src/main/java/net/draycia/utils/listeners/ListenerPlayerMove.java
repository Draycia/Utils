package net.draycia.utils.listeners;

import net.draycia.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerPlayerMove implements Listener {

    private Utils main;

    public ListenerPlayerMove(Utils main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        String worldName = event.getTo().getWorld().getName();

        if (!main.getConfig().contains("VoidTeleports." + worldName)) {
            return;
        }

        int yLevel = main.getConfig().getInt("VoidTeleports." + worldName);

        if (event.getTo().getY() <= yLevel) {
            event.getPlayer().teleport(event.getTo().getWorld().getSpawnLocation());
        }
    }
}
