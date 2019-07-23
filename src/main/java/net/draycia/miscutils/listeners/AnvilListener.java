package net.draycia.miscutils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemGrab(InventoryClickEvent event) {
        if (event.getInventory().getType() != InventoryType.ANVIL) {
            return;
        }

        // If this doesn't work, look here first
        if (event.getSlotType() != InventoryType.SlotType.RESULT) {
            return;
        }

        if (event.getWhoClicked().hasPermission("miscutils.anvil.color")) {
            return;
        }

        if (event.getCurrentItem() == null) {
            return;
        }

        ItemMeta meta = event.getCurrentItem().getItemMeta();

        if (meta == null) {
            return;
        }

        String newName = meta.getDisplayName().replaceAll("&[lmno]", "");
        newName = ChatColor.translateAlternateColorCodes('&', newName);

        meta.setDisplayName(newName);
    }
}
