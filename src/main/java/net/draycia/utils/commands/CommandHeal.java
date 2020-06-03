package net.draycia.utils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.minidigger.minimessage.text.MiniMessageParser;
import net.draycia.utils.Utils;
import net.kyori.text.adapter.bukkit.TextAdapter;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@CommandAlias("heal")
@CommandPermission("noobania.utils.heal")
public class CommandHeal extends BaseCommand {

    private Utils main;

    public CommandHeal(Utils main) {
        this.main = main;
    }

    @Default
    @CommandCompletion("@players")
    public void baseCommand(Player player, @Optional Player target) {
        if (!player.hasPermission("noobania.utils.cooldown.heal.unlimited")) {
            long cooldown = main.getCooldownManager().getUserCooldown(player.getUniqueId(), "util-heal");

            if (main.getCooldownManager().isOnCooldown(player.getUniqueId(), "util-heal")) {
                long timeLeft = cooldown - System.currentTimeMillis();

                TextAdapter.sendMessage(player, main.getMessage("heal-on-cooldown"));

                int timeInSeconds = Math.toIntExact(timeLeft / 1000);

                int days = (int) TimeUnit.SECONDS.toDays(timeInSeconds);
                long hours = TimeUnit.SECONDS.toHours(timeInSeconds) - (days * 24);
                long minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds) - (TimeUnit.SECONDS.toHours(timeInSeconds) * 60);
                long seconds = TimeUnit.SECONDS.toSeconds(timeInSeconds) - (TimeUnit.SECONDS.toMinutes(timeInSeconds) * 60);


                TextAdapter.sendMessage(player, MiniMessageParser.parseFormat(main.getLanguage().getString("heal-remaining-time"),
                        "days", Integer.toString(days), "hours", Long.toString(hours), "minutes",
                        Long.toString(minutes), "seconds", Long.toString(seconds)));

                return;
            }
        }

        if (target != null) {
            target.setHealth(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            target.sendMessage(ChatColor.GREEN + "Your wounds have been healed.");
        } else {
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            player.sendMessage(ChatColor.GREEN + "Your wounds have been healed.");
        }

        if (!player.hasPermission("noobania.utils.cooldown.heal.unlimited")) {
            int newCooldown = 3;

            if (player.hasPermission("noobania.utils.cooldown.heal.1")) {
                newCooldown = 1;
            } else if (player.hasPermission("noobania.utils.cooldown.heal.2")) {
                newCooldown = 2;
            }

            main.getCooldownManager().setUserCooldown(player.getUniqueId(), "util-heal", TimeUnit.HOURS, newCooldown);
        }
    }

}
