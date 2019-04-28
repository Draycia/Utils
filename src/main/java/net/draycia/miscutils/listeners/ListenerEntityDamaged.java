package net.draycia.miscutils.listeners;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.text.DecimalFormat;

import static net.draycia.miscutils.MiscUtils.color;

public class ListenerEntityDamaged implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Horse)) return;

        Player player = (Player)event.getDamager();
        Horse horse = (Horse)event.getEntity();

        if (!player.isSneaking()) return;
        if (!player.hasPermission("miscutils.debug.horses")) return;
        if (player.getInventory().getItemInMainHand().getType() != Material.STICK) return;

        DecimalFormat formatOne = new DecimalFormat("#.#");
        DecimalFormat formatTwo = new DecimalFormat("#%");
        DecimalFormat formatFour = new DecimalFormat("#.####");

        player.sendMessage(color("&7&l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));
        player.sendMessage(color("&e&lHorse Information&7&l:"));

        AttributeInstance maxHealthAttribute = horse.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double maxHealth = 30;
        if (maxHealthAttribute != null) maxHealth = maxHealthAttribute.getValue();

        String healthPercentage = "&3(&f" + formatTwo.format((horse.getHealth() / maxHealth)) + "&3)";
        player.sendMessage(color("  &b&l- &7Current Health&3: &f" + horse.getHealth() + "&b/&f" + maxHealth + " " + healthPercentage));

        String maxHealthPercentage = "&3(&f" + formatTwo.format((maxHealth / 30)) + "&3)";
        player.sendMessage(color("  &b&l- &7Max Health&3: &f" + maxHealth + "&b/&f30.0 " + maxHealthPercentage));

        String domesticationPercentage = "&3(&f" + formatTwo.format((horse.getDomestication() / horse.getMaxDomestication()) * 10) + "&3)";
        player.sendMessage(color("  &b&l- &7Domestication&3: &f" + horse.getDomestication() + "&b/&f" + horse.getMaxDomestication() + " " + domesticationPercentage));

        String jumpPercentage = "&3(&f" + formatTwo.format((horse.getJumpStrength() / 2)) + "&3)";
        player.sendMessage(color("  &b&l- &7Jump Strength&3: &f" + formatOne.format(horse.getJumpStrength()) + "&b/&f2.0 " + jumpPercentage));

        AttributeInstance speedAttribute = horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        double speed = 0.1125;
        if (speedAttribute != null) speed = speedAttribute.getValue();

        String speedPercentage = "&3(&f" + formatTwo.format((speed / 0.3375)) + "&3)";
        player.sendMessage(color("  &b&l- &7Speed&3: &f" + formatFour.format(speed) + "&b/&f0.3375 " + speedPercentage));

        AnimalTamer tamer = horse.getOwner();
        String owner = "Not Tamed";
        if (tamer != null && tamer.getName() != null) owner = tamer.getName();

        player.sendMessage(color("  &b&l- &7Owner&3: &f" + owner));

        player.sendMessage(color("&7&l-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));

        event.setCancelled(true);
    }
}
