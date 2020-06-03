package net.draycia.utils.listeners;

import net.draycia.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.List;
import java.util.Random;

public class ListenerServerListPing implements Listener {

    private Utils utils;
    private Random random = new Random();

    public ListenerServerListPing(Utils utils) {
        this.utils = utils;
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        List<String> motds = utils.getConfig().getStringList("MOTD");

        String motd = motds.get(random.nextInt(motds.size()));

        event.setMotd(ChatColor.translateAlternateColorCodes('&', motd));
        event.setMaxPlayers(utils.getConfig().getInt("MaxPlayers"));
    }
}
