package net.draycia.miscutils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ListenerServerListPing implements Listener {

    private static String SERVER_MOTD = ChatColor.translateAlternateColorCodes('&', "     &6&l&m--------&8&l&m[&7&l&m--&r  &c&lNoobania&r  &7&l&m--&8&l&m]&6&l&m--------&r\n         &e&l&m---&8&l&m[&7&l&m-&f   &6&lEnjoy Your Stay!&f   &7&l&m-&8&l&m]&e&l&m---");

    public static void setServerMotd(String motd) {
        SERVER_MOTD = ChatColor.translateAlternateColorCodes('&', motd);
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        event.setMotd(SERVER_MOTD);
        event.setMaxPlayers(40);
    }
}
