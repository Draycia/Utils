package net.draycia.miscutils.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

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
}
