package net.draycia.miscutils.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import static net.draycia.miscutils.MiscUtils.color;

public class ListenerPlayerInteract implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.isSneaking() || event.getClickedBlock() == null || event.getItem() == null) {
            return;
        }

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.hasPermission("miscutils.debug.chunks")) {
            if (event.getItem().getType() == Material.SLIME_BALL) {
                player.sendMessage(color("&7Slime Chunk&3: &f" + (event.getClickedBlock().getChunk().isSlimeChunk() ? "Yes" : "No")));
                event.setCancelled(true);
            }
        }
    }

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

    @EventHandler
    public void onItemPickup(final EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Villager) && !(event.getEntity() instanceof Player)) {
            event.setCancelled(true);
        }
    }
}
