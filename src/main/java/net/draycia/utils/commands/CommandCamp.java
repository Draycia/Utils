package net.draycia.utils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import net.draycia.utils.Utils;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

@CommandAlias("camp|logoff|logout|leave|quit")
@CommandPermission("noobania.utils.camp")
public class CommandCamp extends BaseCommand {

    private Utils main;

    public CommandCamp(Utils main) {
        this.main = main;
    }

    @Default
    public void baseCommand(Player player) {
        player.kickPlayer(LegacyComponentSerializer.legacy().serialize(main.getMessage("camp")));
    }

}
