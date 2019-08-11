package net.draycia.miscutils.commands;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.onarandombox.MultiverseCore.enums.AllowedPortalType;
import net.draycia.miscutils.MiscUtils;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCreateWorld implements CommandExecutor {

    private MiscUtils main;

    public CommandCreateWorld(MiscUtils main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            return false;
        }

        MVWorldManager manager = main.getMultiverseCore().getMVWorldManager();

        // Create world
        boolean success = manager.addWorld(args[0], World.Environment.NORMAL, "world", WorldType.FLAT, false, null);

        if (!success) {
            sender.sendMessage(ChatColor.RED + "Unable to create world!");
            return true;
        }

        // Set world options
        MultiverseWorld world = manager.getMVWorld(args[0]);

        world.allowPortalMaking(AllowedPortalType.NONE);
        world.setDifficulty(Difficulty.PEACEFUL);

        // Set world border (to 100)
        WorldBorder worldBorder = world.getCBWorld().getWorldBorder();

        worldBorder.setCenter(world.getSpawnLocation());
        worldBorder.setSize(100);

        // Add the world to the main survival inventory group
        main.getPerWorldInventoryAPI().getGroup("default").addWorld(args[0]);

        // Set an essentials warp to the spawn location
        try {
            main.getEssentials().getWarps().setWarp(args[0], world.getSpawnLocation());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Player target = Bukkit.getPlayer(args[1]);

        Permission permission = main.getVaultPermissions();

        // Assign permissions to world owner
        permission.playerAdd(target, "multiverse.access." + args[0]);
        permission.playerAdd(target, "essentials.warps." + args[0]);
        permission.playerAdd(args[0], target, "morevaultplus.open");
        permission.playerAdd(args[0], target, "noobania.world.manage." + args[0]);

        return true;
    }
}
