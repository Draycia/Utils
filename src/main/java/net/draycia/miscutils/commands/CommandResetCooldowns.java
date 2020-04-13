package net.draycia.miscutils.commands;

import net.draycia.noobaniautils.time.CooldownManager;
import net.draycia.noobaniautils.time.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandResetCooldowns implements CommandExecutor {

    CooldownManager cooldownManager;

    public CommandResetCooldowns(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage("That player is not online!");
            return true;
        }

        cooldownManager.setUserCooldown(target.getUniqueId(), CommandFeed.class, TimeUnit.TICKS, 0);
        cooldownManager.setUserCooldown(target.getUniqueId(), CommandHeal.class, TimeUnit.TICKS, 0);

        sender.sendMessage(target.getName() + "'s cooldowns have been reset!");
        target.sendMessage("Your cooldowns (feed/heal) have been reset!");

        return true;
    }

}
