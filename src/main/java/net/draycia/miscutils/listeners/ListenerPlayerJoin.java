package net.draycia.miscutils.listeners;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ListenerPlayerJoin implements Listener {

    private final Color navyBlue = Color.fromRGB(0, 55, 96);
    private ItemStack[] leatherSet = new ItemStack[4];

    public ListenerPlayerJoin() {
        leatherSet[0] = new ItemStack(Material.LEATHER_HELMET,     1);
        leatherSet[1] = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        leatherSet[2] = new ItemStack(Material.LEATHER_LEGGINGS,   1);
        leatherSet[3] = new ItemStack(Material.LEATHER_BOOTS,      1);

        for (ItemStack armorPiece : leatherSet) {
            LeatherArmorMeta meta = (LeatherArmorMeta)armorPiece.getItemMeta();
            meta.setColor(navyBlue);
            armorPiece.setItemMeta(meta);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            player.getInventory().setArmorContents(leatherSet);

            player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD,   1));
            player.getInventory().setItem(1, new ItemStack(Material.STONE_PICKAXE, 1));
            player.getInventory().setItem(2, new ItemStack(Material.STONE_AXE,     1));
            player.getInventory().setItem(3, new ItemStack(Material.STONE_SHOVEL,  1));
            player.getInventory().setItem(4, new ItemStack(Material.STONE_HOE,     1));
            player.getInventory().setItem(5, new ItemStack(Material.COOKED_BEEF,   1));
        }
    }
}
