package net.draycia.utils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.minidigger.minimessage.text.MiniMessageParser;
import net.draycia.utils.Utils;
import net.kyori.text.adapter.bukkit.TextAdapter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@CommandAlias("feed")
@CommandPermission("noobania.utils.feed")
public class CommandFeed extends BaseCommand {

    private Utils main;

    public CommandFeed(Utils main) {
        this.main = main;
    }

    @Default
    @CommandCompletion("@players")
    public void onCommand(Player player, @Optional Player target) {
        if (!player.hasPermission("noobania.utils.cooldown.feed.unlimited")) {
            long cooldown = main.getCooldownManager().getUserCooldown(player.getUniqueId(), "util-feed");

            if (main.getCooldownManager().isOnCooldown(player.getUniqueId(), "util-feed")) {
                long timeLeft = cooldown - System.currentTimeMillis();

                TextAdapter.sendMessage(player, main.getMessage("feed-on-cooldown"));

                int timeInSeconds = Math.toIntExact(timeLeft / 1000);

                int days = (int) TimeUnit.SECONDS.toDays(timeInSeconds);
                long hours = TimeUnit.SECONDS.toHours(timeInSeconds) - (days * 24);
                long minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds) - (TimeUnit.SECONDS.toHours(timeInSeconds) * 60);
                long seconds = TimeUnit.SECONDS.toSeconds(timeInSeconds) - (TimeUnit.SECONDS.toMinutes(timeInSeconds) * 60);


                TextAdapter.sendMessage(player, MiniMessageParser.parseFormat(main.getLanguage().getString("feed-remaining-time"),
                        "days", Integer.toString(days), "hours", Long.toString(hours), "minutes",
                        Long.toString(minutes), "seconds", Long.toString(seconds)));

                return;
            }
        }

        if (target != null) {
            target.setFoodLevel(20);
            target.sendMessage(ChatColor.GREEN + "You are no longer hungry.");
        } else {
            player.setFoodLevel(20);
            player.sendMessage(ChatColor.GREEN + "You are no longer hungry.");
        }

        if (!player.hasPermission("noobania.utils.cooldown.feed.unlimited")) {
            int newCooldown = 3;

            if (player.hasPermission("noobania.utils.cooldown.feed.1")) {
                newCooldown = 1;
            } else if (player.hasPermission("noobania.utils.cooldown.feed.2")) {
                newCooldown = 2;
            }

            main.getCooldownManager().setUserCooldown(player.getUniqueId(), "util-feed", TimeUnit.HOURS, newCooldown);
        }
    }

}
