package net.draycia.miscutils;

import net.draycia.miscutils.commands.CommandCamp;
import net.draycia.miscutils.commands.CommandPlayTime;
import net.draycia.miscutils.listeners.AnvilListener;
import net.draycia.miscutils.listeners.ListenerEntityDamaged;
import net.draycia.miscutils.listeners.ListenerPlayerDeath;
import net.draycia.miscutils.listeners.ListenerPlayerInteract;
import net.draycia.miscutils.listeners.ListenerPlayerJoin;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public final class MiscUtils extends JavaPlugin implements Listener {

    public static Permission perms = null;
    public static MiscUtils instance;

    @Override
    public void onEnable() {
        MiscUtils.instance = this;

        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();

        getServer().getPluginManager().registerEvents(new ListenerPlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new ListenerEntityDamaged(), this);
        getServer().getPluginManager().registerEvents(new ListenerPlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new ListenerPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new AnvilListener(), this);

        getCommand("camp").setExecutor(new CommandCamp());
        getCommand("playtime").setExecutor(new CommandPlayTime());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                if (player.getWorld().getName().equalsIgnoreCase("Spawn")) {
                    if (player.getLocation().getBlockX() < 50) {
                        player.teleport(player.getWorld().getSpawnLocation());
                    }
                }
            }
        }, 0, 10);
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String formatTime(int timeInSeconds) {
        int days = (int) TimeUnit.SECONDS.toDays(timeInSeconds);
        long hours = TimeUnit.SECONDS.toHours(timeInSeconds) - (days * 24);
        long minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds) - (TimeUnit.SECONDS.toHours(timeInSeconds)* 60);
        long seconds = TimeUnit.SECONDS.toSeconds(timeInSeconds) - (TimeUnit.SECONDS.toMinutes(timeInSeconds) *60);

        return "&7" + days + "&fD &7" + hours + "&fH &7" + minutes + "&fM &7" + seconds + "&fS";
    }
}
