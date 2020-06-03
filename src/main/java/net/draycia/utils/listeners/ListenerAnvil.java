package net.draycia.utils.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ListenerAnvil implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemGrab(InventoryClickEvent event) {
        if (event.getInventory().getType() != InventoryType.ANVIL) {
            return;
        }

        // If this doesn't work, look here first
        if (event.getSlotType() != InventoryType.SlotType.RESULT) {
            return;
        }

        if (!event.getWhoClicked().hasPermission("noobania.utils.anvil.color")) {
            return;
        }

        if (event.getCurrentItem() == null) {
            return;
        }

        ItemMeta meta = event.getCurrentItem().getItemMeta();

        if (meta == null) {
            return;
        }

        String newName = meta.getDisplayName().replaceAll("&[klmno]", "");
        newName = ChatColor.translateAlternateColorCodes('&', newName);

        meta.setDisplayName(newName);

        event.getCurrentItem().setItemMeta(meta);
    }

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {

        for (HumanEntity entity : event.getInventory().getViewers()) {
            if (entity instanceof Player) {
                if (!entity.hasPermission("noobania.utils.anvil.color")) {
                    return;
                }

                break;
            }
        }

        String newName = event.getInventory().getRenameText();

        if (newName == null || newName.isEmpty()) {
            return;
        }

        ItemStack result = event.getResult();

        if (result == null) {
            return;
        }

        ItemMeta meta = result.getItemMeta();

        if (meta == null) {
            return;
        }

        newName = ChatColor.translateAlternateColorCodes('&', newName);

        meta.setDisplayName(newName);

        result.setItemMeta(meta);
    }
}
