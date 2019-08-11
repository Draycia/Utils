package net.draycia.miscutils.commands;

import net.draycia.miscutils.MiscUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReload implements CommandExecutor {

    private MiscUtils main;

    public CommandReload(MiscUtils main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        main.reloadConfig();

        sender.sendMessage(ChatColor.GOLD + "Reloaded MiscUtils config!");

        return true;
    }
}
