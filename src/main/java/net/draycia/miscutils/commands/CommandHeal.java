package net.draycia.miscutils.commands;

import net.draycia.miscutils.MiscUtils;
import net.draycia.noobaniautils.time.CooldownManager;
import net.draycia.noobaniautils.time.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHeal implements CommandExecutor {

    private CooldownManager cooldownManager;

    public CommandHeal(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        long cooldown = cooldownManager.getUserCooldown(player.getUniqueId(), CommandHeal.class);
        long timeLeft = cooldown - System.currentTimeMillis();

        if (cooldownManager.isOnCooldown(player.getUniqueId(), CommandHeal.class)) {
            player.sendMessage(ChatColor.RED + "The heal command is on cooldown!");
            player.sendMessage(ChatColor.RED + "Remaining time: " + MiscUtils.color(MiscUtils.formatTime(Math.toIntExact(timeLeft / 1000))));
            return true;
        }

        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.sendMessage(ChatColor.GREEN + "Your wounds have been healed.");

        int newCooldown = 3;

        if (player.hasPermission("noobania.utils.cooldown.heal.1")) {
            newCooldown = 1;
        } else if (player.hasPermission("noobania.utils.cooldown.heal.2")) {
            newCooldown = 2;
        }

        cooldownManager.setUserCooldown(player.getUniqueId(), CommandHeal.class, TimeUnit.HOURS, newCooldown);

        return true;
    }

}
