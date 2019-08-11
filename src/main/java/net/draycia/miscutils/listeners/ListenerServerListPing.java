package net.draycia.miscutils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ListenerServerListPing implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        event.setMotd(ChatColor.translateAlternateColorCodes('&', "     &b&l&m--------&8&l&m[&7&l&m--&r  &3&lNoobania&r  &7&l&m--&8&l&m]&b&l&m--------&r\n         &d&l&m---&8&l&m[&7&l&m-&f   &b&lEnjoy Your Stay!&f   &7&l&m-&8&l&m]&d&l&m---"));
        event.setMaxPlayers(40);
    }
}
