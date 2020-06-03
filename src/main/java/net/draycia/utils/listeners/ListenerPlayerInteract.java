package net.draycia.utils.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerPlayerInteract implements Listener {

    @EventHandler
    public void onPortalBreak(final PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) {
            return;
        }

        final ItemStack item = event.getItem();

        if (item != null && (item.getType().equals(Material.WATER_BUCKET) || item.getType().equals(Material.LAVA_BUCKET) || item.getType().equals(Material.PUFFERFISH_BUCKET) || item.getType().equals(Material.SALMON_BUCKET) || item.getType().equals(Material.COD_BUCKET) || item.getType().equals(Material.TROPICAL_FISH_BUCKET))) {
            final Block adjacentBlock = event.getClickedBlock().getRelative(event.getBlockFace());
            if (adjacentBlock.getType() == Material.END_PORTAL) {
                event.setCancelled(true);
            }
        }
    }
}
