package net.draycia.miscutils;

import com.earth2me.essentials.Essentials;
import com.onarandombox.MultiverseCore.MultiverseCore;
import me.clip.placeholderapi.PlaceholderAPI;
import me.ebonjaeger.perworldinventory.PerWorldInventory;
import me.ebonjaeger.perworldinventory.api.PerWorldInventoryAPI;
import net.draycia.miscutils.commands.CommandCamp;
import net.draycia.miscutils.commands.CommandCreateWorld;
import net.draycia.miscutils.commands.CommandPlayTime;
import net.draycia.miscutils.commands.CommandReload;
import net.draycia.miscutils.commands.CommandWorldTrust;
import net.draycia.miscutils.commands.CommandWorldUntrust;
import net.draycia.miscutils.listeners.ListenerAnvil;
import net.draycia.miscutils.listeners.ListenerEntityDamaged;
import net.draycia.miscutils.listeners.ListenerEntitySpawn;
import net.draycia.miscutils.listeners.ListenerPlayerInteract;
import net.draycia.miscutils.listeners.ListenerPlayerJoin;
import net.draycia.miscutils.listeners.ListenerPlayerMove;
import net.draycia.miscutils.listeners.ListenerServerListPing;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
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
        pluginManager.registerEvents(new ListenerEntityDamaged(), this);
        pluginManager.registerEvents(new ListenerEntitySpawn(), this);
        pluginManager.registerEvents(new ListenerPlayerJoin(), this);
        pluginManager.registerEvents(new ListenerAnvil(), this);

        getCommand("worlduntrust").setExecutor(new CommandWorldUntrust(this));
        getCommand("createworld").setExecutor(new CommandCreateWorld(this));
        getCommand("worldtrust").setExecutor(new CommandWorldTrust(this));
        getCommand("mureload").setExecutor(new CommandReload(this));
        getCommand("playtime").setExecutor(new CommandPlayTime());
        getCommand("camp").setExecutor(new CommandCamp(this));

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

    public MultiverseCore getMultiverseCore() {
        Plugin plugin = getServer().getPluginManager().getPlugin("Multiverse-Core");

        if (plugin instanceof MultiverseCore) {
            return (MultiverseCore) plugin;
        }

        throw new RuntimeException("MultiVerse not found!");
    }

    public PerWorldInventoryAPI getPerWorldInventoryAPI() {
        Plugin plugin = getServer().getPluginManager().getPlugin("PerWorldInventory");

        if (plugin instanceof PerWorldInventory) {
            return ((PerWorldInventory) plugin).getApi();
        }

        throw new RuntimeException("PerWorldInventory not found!");
    }

    public Essentials getEssentials() {
        Plugin plugin = getServer().getPluginManager().getPlugin("Essentials");

        if (plugin instanceof Essentials) {
            return (Essentials) plugin;
        }

        throw new RuntimeException("Essentials not found!");
    }

    public Permission getVaultPermissions() {
        return perms;
    }
}
