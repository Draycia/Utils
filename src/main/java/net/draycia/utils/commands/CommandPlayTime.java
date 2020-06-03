package net.draycia.utils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.mojang.authlib.GameProfile;
import me.minidigger.minimessage.text.MiniMessageParser;
import net.draycia.utils.Utils;
import net.kyori.text.adapter.bukkit.TextAdapter;
import net.minecraft.server.v1_15_R1.DimensionManager;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.PlayerInteractManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@CommandAlias("playtime|played|pt")
@CommandPermission("noobania.utils.playtime")
public class CommandPlayTime extends BaseCommand {

    private Utils main;

    public CommandPlayTime(Utils main) {
        this.main = main;
    }

    @Default
    @CommandCompletion("@players")
    public void onCommand(final CommandSender issuer, @Optional final OfflinePlayer target) {
        Player newTarget;

        if (target != null) {
            newTarget = getPlayerFromOffline(target, Bukkit.getWorlds().get(0).getSpawnLocation());
        } else if (issuer instanceof Player) {
            newTarget = (Player)issuer;
        } else {
            TextAdapter.sendMessage(issuer, main.getMessage("no-player"));
            return;
        }


        TextAdapter.sendMessage(issuer, main.getMessage("playtime-stats", "player", newTarget.getName()));

        int timeInSeconds = newTarget.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;

        int days = (int) TimeUnit.SECONDS.toDays(timeInSeconds);
        long hours = TimeUnit.SECONDS.toHours(timeInSeconds) - (days * 24);
        long minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds) - (TimeUnit.SECONDS.toHours(timeInSeconds) * 60);
        long seconds = TimeUnit.SECONDS.toSeconds(timeInSeconds) - (TimeUnit.SECONDS.toMinutes(timeInSeconds) * 60);

        TextAdapter.sendMessage(issuer, MiniMessageParser.parseFormat(main.getLanguage().getString("playtime-pt"),
                "days", Integer.toString(days), "hours", Long.toString(hours), "minutes",
                Long.toString(minutes), "seconds", Long.toString(seconds)));

        int timesJoined = newTarget.getStatistic(Statistic.LEAVE_GAME) + 1;

        TextAdapter.sendMessage(issuer, main.getMessage("playtime-joined", "joined", Integer.toString(timesJoined)));
    }

    // Method obtained from https://www.spigotmc.org/threads/clear-inventory-of-offlineplayer.284898/#post-2753902 and slightly modified
    static Player getPlayerFromOffline(OfflinePlayer offlinePlayer, Location location) {
        if (offlinePlayer instanceof Player) {
            return (Player)offlinePlayer;
        }

        Player target;
        GameProfile profile = new GameProfile(offlinePlayer.getUniqueId(), offlinePlayer.getName());

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        EntityPlayer entity = new EntityPlayer(server, server.getWorldServer(DimensionManager.OVERWORLD), profile, new PlayerInteractManager(server.getWorldServer(DimensionManager.OVERWORLD)));
        entity.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        entity.world = ((CraftWorld) location.getWorld()).getHandle();
        target = entity.getBukkitEntity();
        if (target != null) { target.loadData(); }
        return target;
    }

}
