package net.draycia.miscutils.commands;

import net.draycia.miscutils.MiscUtils;
import net.draycia.noobaniautils.time.CooldownManager;
import net.draycia.noobaniautils.time.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFeed implements CommandExecutor {

    CooldownManager cooldownManager;

    public CommandFeed(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        long cooldown = cooldownManager.getUserCooldown(player.getUniqueId(), CommandFeed.class);
        long timeLeft = cooldown - System.currentTimeMillis();

        if (cooldownManager.isOnCooldown(player.getUniqueId(), CommandFeed.class)) {
            player.sendMessage(ChatColor.RED + "The feed command is on cooldown!");
            player.sendMessage(ChatColor.RED + "Remaining time: " + MiscUtils.color(MiscUtils.formatTime(Math.toIntExact(timeLeft / 1000))));
            return true;
        }

        player.setFoodLevel(20);
        player.sendMessage(ChatColor.GREEN + "You are no longer hungry.");

        int newCooldown = 3;

        if (player.hasPermission("noobania.utils.cooldown.feed.1")) {
            newCooldown = 1;
        } else if (player.hasPermission("noobania.utils.cooldown.feed.2")) {
            newCooldown = 2;
        }

        cooldownManager.setUserCooldown(player.getUniqueId(), CommandFeed.class, TimeUnit.HOURS, newCooldown);

        return true;
    }

}
