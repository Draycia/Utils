package net.draycia.miscutils.commands;

import net.draycia.miscutils.MiscUtils;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWorldUntrust implements CommandExecutor {

    private MiscUtils main;

    public CommandWorldUntrust(MiscUtils main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            return false;
        }

        if (!sender.hasPermission("noobania.world.manage." + args[0])) {
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        Permission permission = main.getVaultPermissions();

        permission.playerRemove(target, "multiverse.access." + args[0]);
        permission.playerRemove(target, "essentials.warps." + args[0]);
        permission.playerRemove(args[0], target, "morevaultplus.open");

        return true;
    }
}
