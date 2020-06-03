package net.draycia.utils;

import co.aikar.commands.PaperCommandManager;
import me.clip.placeholderapi.PlaceholderAPI;
import me.minidigger.minimessage.text.MiniMessageParser;
import net.draycia.cooldowns.CooldownManager;
import net.draycia.cooldowns.Cooldowns;
import net.draycia.utils.broadcaster.BroadcastManager;
import net.draycia.utils.commands.*;
import net.draycia.utils.listeners.*;
import net.kyori.text.Component;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Utils extends JavaPlugin implements Listener {

    private CooldownManager cooldownManager;
    private BroadcastManager broadcastManager;

    private YamlConfiguration language = new YamlConfiguration();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResource("language.yml", false);

        reloadLanguage();

        Cooldowns cooldowns = (Cooldowns) getServer().getPluginManager().getPlugin("Cooldowns");

        cooldownManager = cooldowns.getCooldownManager();
        broadcastManager = new BroadcastManager(this).start();

        setupListeners();
        setupCommands();

        PlaceholderAPI.registerExpansion(new PlaytimeExpansion(this));
    }

    private void setupListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new ListenerPlayerMove(this), this);
        pluginManager.registerEvents(new ListenerServerListPing(this), this);
        pluginManager.registerEvents(new ListenerPlayerInteract(), this);
        pluginManager.registerEvents(new ListenerAnvil(), this);
    }

    private void setupCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(this);

        commandManager.registerCommand(new CommandCamp(this));
        commandManager.registerCommand(new CommandFeed(this));
        commandManager.registerCommand(new CommandHeal(this));
        commandManager.registerCommand(new CommandPlayTime(this));
        commandManager.registerCommand(new CommandReload(this));
        commandManager.registerCommand(new CommandResetCooldowns(this));
    }

    public void reloadLanguage() {
        try {
            language.load(new File(getDataFolder(), "language.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public BroadcastManager getBroadcastManager() {
        return broadcastManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public YamlConfiguration getLanguage() {
        return language;
    }

    public Component getMessage(String key, String... placeholders) {
        return MiniMessageParser.parseFormat(language.getString(key).replace("\\n", "\n"), placeholders);
    }
}
