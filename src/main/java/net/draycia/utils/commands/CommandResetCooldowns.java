package net.draycia.utils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.draycia.utils.Utils;
import net.kyori.text.adapter.bukkit.TextAdapter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("resetcooldowns")
@CommandPermission("noobania.utils.resetcooldowns")
public class CommandResetCooldowns extends BaseCommand {

    private Utils main;

    public CommandResetCooldowns(Utils main) {
        this.main = main;
    }

    @Default
    @CommandCompletion("@players")
    public void baseCommand(CommandSender issuer, @Optional Player target) {
        Player newTarget;

        if (target != null) {
            newTarget = target;
        } else if (issuer instanceof Player) {
            newTarget = (Player)issuer;
        } else {
            TextAdapter.sendMessage(issuer, main.getMessage("no-player"));
            return; // TODO: send error message, no player specified and sender is not a player
        }

        main.getCooldownManager().resetUserCooldown(newTarget.getUniqueId(), "util-feed");
        main.getCooldownManager().resetUserCooldown(newTarget.getUniqueId(), "util-heal");

        if (newTarget.equals(issuer)) {
            issuer.sendMessage("Your cooldowns (feed/heal) have been reset!");
        } else {
            issuer.sendMessage(newTarget.getName() + "'s cooldowns have been reset!");
            newTarget.sendMessage("Your cooldowns (feed/heal) have been reset!");
        }
    }

}
