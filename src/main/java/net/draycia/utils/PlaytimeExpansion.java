package net.draycia.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.minidigger.minimessage.text.MiniMessageParser;
import net.kyori.text.Component;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class PlaytimeExpansion extends PlaceholderExpansion {

    private Utils main;

    public PlaytimeExpansion(Utils main) {
        this.main = main;
    }

    /**
     * This method should always return true unless we
     * have a dependency we need to make sure is on the server
     * for our placeholders to work!
     * This expansion does not require a dependency so we will always return true
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    /**
     * The name of the person who created this expansion should go here
     */
    @Override
    public String getAuthor() {
        return "Draycia (Vicarious)";
    }

    /**
     * The placeholder identifier should go here
     * This is what tells PlaceholderAPI to call our onPlaceholderRequest method to obtain
     * a value if a placeholder starts with our identifier.
     * This must be unique and can not contain % or _
     */
    @Override
    public String getIdentifier() {
        return "playerutils";
    }

    /**
     * This is the version of this expansion
     */
    @Override
    public String getVersion() {
        return "1.0.0";
    }

    /**
     * This is the method called when a placeholder with our identifier is found and needs a value
     * We specify the value identifier in this method
     */
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equalsIgnoreCase("playtime")) {
            int timeInSeconds = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;

            int days = (int) TimeUnit.SECONDS.toDays(timeInSeconds);
            long hours = TimeUnit.SECONDS.toHours(timeInSeconds) - (days * 24);
            long minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds) - (TimeUnit.SECONDS.toHours(timeInSeconds) * 60);
            long seconds = TimeUnit.SECONDS.toSeconds(timeInSeconds) - (TimeUnit.SECONDS.toMinutes(timeInSeconds) * 60);

            Component component = MiniMessageParser.parseFormat(main.getLanguage().getString("placeholder-pt"),
                    "days", Integer.toString(days), "hours", Long.toString(hours), "minutes",
                    Long.toString(minutes), "seconds", Long.toString(seconds));

            return LegacyComponentSerializer.legacy().serialize(component);
        }

        return null;
    }
}