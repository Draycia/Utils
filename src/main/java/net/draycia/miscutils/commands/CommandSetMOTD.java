package net.draycia.miscutils.commands;

import net.draycia.miscutils.listeners.ListenerServerListPing;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandSetMOTD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ListenerServerListPing.setServerMotd(String.join(" ", args));
        sender.sendMessage(ChatColor.GREEN + "Updated server MOTD!");

        return true;
    }
}
