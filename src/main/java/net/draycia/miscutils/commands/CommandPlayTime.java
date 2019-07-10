package net.draycia.miscutils.commands;

import com.mojang.authlib.GameProfile;
import net.draycia.miscutils.MiscUtils;
import net.minecraft.server.v1_14_R1.DimensionManager;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.MinecraftServer;
import net.minecraft.server.v1_14_R1.PlayerInteractManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.entity.Player;

import static net.draycia.miscutils.MiscUtils.color;

public class CommandPlayTime implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(MiscUtils.instance, () -> {
            Player target = (Player)sender;

            if (args.length >= 1) {
                target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(args[0]);
                    target = getOfflinePlayer(offlineTarget, ((Player)sender).getLocation());
                    if (target == null) {
                        sender.sendMessage(color("&cThat player has never joined the server!"));
                        return;
                    }
                }
            }

            int timeInSeconds = target.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
            int timesJoined = target.getStatistic(Statistic.LEAVE_GAME) + 1;

            sender.sendMessage(color("&3[&bPlayTime&3] &b" + target.getName() + "'s stats"));
            sender.sendMessage(color("&bPlayTime: &7" + MiscUtils.formatTime(timeInSeconds)));
            sender.sendMessage(color("&bTimes Joined: &7" + timesJoined));

            if (MiscUtils.perms != null) {
                sender.sendMessage(color("&bRank: &7" + StringUtils.capitalize(MiscUtils.perms.getPrimaryGroup("world", target))));
            }
        });

        return true;
    }

    // Method obtained from https://www.spigotmc.org/threads/clear-inventory-of-offlineplayer.284898/#post-2753902 and slightly modified
    private Player getOfflinePlayer(OfflinePlayer offlinePlayer, Location location) {
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
