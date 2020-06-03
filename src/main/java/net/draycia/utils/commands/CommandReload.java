package net.draycia.utils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import net.draycia.utils.Utils;
import net.kyori.text.adapter.bukkit.TextAdapter;
import org.bukkit.command.CommandSender;

@CommandAlias("ureload|utilsreload")
@CommandPermission("noobania.utils.reload")
public class CommandReload extends BaseCommand {

    private Utils main;

    public CommandReload(Utils main) {
        this.main = main;
    }

    @Default
    public void onCommand(CommandSender issuer) {
        main.reloadConfig();
        main.getBroadcastManager().reload();

        TextAdapter.sendMessage(issuer, main.getMessage("reloaded"));
    }

}
