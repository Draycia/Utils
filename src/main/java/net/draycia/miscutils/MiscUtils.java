package net.draycia.miscutils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.draycia.miscutils.commands.*;
import net.draycia.miscutils.listeners.ListenerAnvil;
import net.draycia.miscutils.listeners.ListenerPlayerInteract;
import net.draycia.miscutils.listeners.ListenerPlayerJoin;
import net.draycia.miscutils.listeners.ListenerPlayerMove;
import net.draycia.miscutils.listeners.ListenerServerListPing;
import net.draycia.noobaniautils.NoobaniaUtils;
import net.draycia.noobaniautils.time.CooldownManager;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public final class MiscUtils extends JavaPlugin implements Listener {

    public static Permission perms = null;
    public static MiscUtils instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        MiscUtils.instance = this;

        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new ListenerPlayerMove(this), this);
        pluginManager.registerEvents(new ListenerServerListPing(), this);
        pluginManager.registerEvents(new ListenerPlayerInteract(), this);
        pluginManager.registerEvents(new ListenerPlayerJoin(), this);
        pluginManager.registerEvents(new ListenerAnvil(), this);

        NoobaniaUtils noobaniaUtils = (NoobaniaUtils) getServer().getPluginManager().getPlugin("Noobania-Utils");
        CooldownManager cooldownManager = noobaniaUtils.getCooldownManager();

        getCommand("mureload").setExecutor(new CommandReload(this));
        getCommand("camp").setExecutor(new CommandCamp(this));
        getCommand("playtime").setExecutor(new CommandPlayTime());
        getCommand("setmotd").setExecutor(new CommandSetMOTD());
        getCommand("heal").setExecutor(new CommandHeal(cooldownManager));
        getCommand("feed").setExecutor(new CommandFeed(cooldownManager));
        getCommand("resetcooldowns").setExecutor(new CommandResetCooldowns(cooldownManager));

        PlaceholderAPI.registerExpansion(new PlaytimeExpansion());
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
