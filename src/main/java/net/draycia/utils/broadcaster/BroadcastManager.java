package net.draycia.utils.broadcaster;

import com.google.common.collect.Iterables;
import me.clip.placeholderapi.PlaceholderAPI;
import me.minidigger.minimessage.text.MiniMessageParser;
import net.draycia.utils.Utils;
import net.kyori.text.adapter.bukkit.TextAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.List;

public class BroadcastManager {

    private Utils utils;
    private List<String> messages;

    private Iterator<String> iterable;
    private String message;

    private int taskId;

    public BroadcastManager(Utils utils) {
        this.utils = utils;

        messages = utils.getConfig().getStringList("Broadcast");
    }

    public void reload() {
        stop();
        messages = utils.getConfig().getStringList("Broadcast");
        start();
    }

    public BroadcastManager start() {
        iterable = Iterables.cycle(messages).iterator();

        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(utils, () -> {
            message = iterable.next();

            for (Player player : Bukkit.getOnlinePlayers()) {
                TextAdapter.sendMessage(player, MiniMessageParser.parseFormat(PlaceholderAPI.setPlaceholders(player, message)));
            }
        }, 0, utils.getConfig().getInt("Broadcast-Interval"));

        return this;
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

}
