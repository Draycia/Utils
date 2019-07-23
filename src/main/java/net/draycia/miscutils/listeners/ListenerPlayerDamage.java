package net.draycia.miscutils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ListenerPlayerDamage implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player damager = (Player)event.getDamager();
        Player player = (Player)event.getEntity();

        String permission = "miscutils.pvp";

        if (!damager.hasPermission(permission)) {
            damager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have PVP enabled!"));
            event.setCancelled(true);
        }

        if (!player.hasPermission(permission)) {
            damager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player does not have PVP enabled!"));
            event.setCancelled(true);
        }
    }
}
