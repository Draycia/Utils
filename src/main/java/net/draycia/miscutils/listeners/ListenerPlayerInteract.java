package net.draycia.miscutils.listeners;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static net.draycia.miscutils.MiscUtils.color;

public class ListenerPlayerInteract implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.isSneaking() || event.getClickedBlock() == null || event.getItem() == null) return;

        // Debug Chunks
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.hasPermission("miscutils.debug.chunks")) {
            Chunk chunk = event.getClickedBlock().getChunk();

            if (event.getItem().getType() == Material.SLIME_BALL) {
                player.sendMessage(color("  &b&l- &7Slime Chunk&3: &f" + (chunk.isSlimeChunk() ? "Yes" : "No")));
                return;
            } else if (event.getItem().getType() != Material.STICK) {
                return;
            }

            player.sendMessage(color("&7&l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));

            player.sendMessage(color("&e&lChunk Information&7&l:"));

            player.sendMessage(color("  &b&l- &7Slime Chunk&3: &f" + (chunk.isSlimeChunk() ? "Yes" : "No")));

            if (player.hasPermission("miscutils.debug.chunks.entities")) {
                player.sendMessage(color("  &b&l- &7Entities&3: &f" + chunk.getEntities().length));
            }

            if (player.hasPermission("miscutils.debug.chunks.tiles")) {
                player.sendMessage(color("  &b&l- &7Tiles&3: &f" + chunk.getTileEntities().length));
            }

            event.setCancelled(true);
        }
        // End Debug Chunks

        // Debug Blocks
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.hasPermission("miscutils.debug.blocks")) {
            Block block = event.getClickedBlock();
            Block aboveBlock = block.getRelative(BlockFace.UP);

            player.sendMessage(color("&e&lBlock Information&7&l:"));
            player.sendMessage(color("  &b&l- &7Coordinates&3: &f" + block.getX() + "&b/&f" + block.getY() + "&b/&f" + block.getZ()));

            String spawnMessage;

            String cropMessage = aboveBlock.getLightLevel() >= 9 ? "Yes" : "No";
            player.sendMessage(color("  &b&l- &7Can Grow Crops&3: &f" + cropMessage + " &3(&fLight Level " + aboveBlock.getLightLevel() + "&3)"));

            if (block.isPassable()) {
                spawnMessage = block.getLightLevel() > 7 ? "Yes" : "No";
            } else {
                spawnMessage = aboveBlock.getLightLevel() > 7 ? "Yes" : "No";
            }

            player.sendMessage(color("  &b&l- &7Can Spawn Mobs&3: &f" + spawnMessage + " &3(&fLight Level " + aboveBlock.getLightLevel() + "&3)"));
            player.sendMessage(color("  &b&l- &7Biome&3: &f" + block.getBiome().name()));
        }
        // End Debug Blocks

        player.sendMessage(color("&7&l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));
    }
}
