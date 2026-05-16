package ol.originallightlib.core.display;

import ol.originallightlib.core.message.Text;
import org.bukkit.entity.Player;

import java.util.Collection;

public final class TitleUtil {

    private TitleUtil() {
    }

    public static void send(Player player, String title, String subtitle) {
        send(player, title, subtitle, 10, 40, 10);
    }

    public static void send(
            Player player,
            String title,
            String subtitle,
            int fadeIn,
            int stay,
            int fadeOut
    ) {
        if (player == null || !player.isOnline()) {
            return;
        }

        player.sendTitle(
                Text.color(title),
                Text.color(subtitle),
                fadeIn,
                stay,
                fadeOut
        );
    }

    public static void send(
            Player player,
            String title,
            String subtitle,
            int fadeIn,
            int stay,
            int fadeOut,
            String... placeholders
    ) {
        if (player == null || !player.isOnline()) {
            return;
        }

        player.sendTitle(
                Text.format(title, placeholders),
                Text.format(subtitle, placeholders),
                fadeIn,
                stay,
                fadeOut
        );
    }

    public static void send(Collection<? extends Player> players, String title, String subtitle) {
        send(players, title, subtitle, 10, 40, 10);
    }

    public static void send(
            Collection<? extends Player> players,
            String title,
            String subtitle,
            int fadeIn,
            int stay,
            int fadeOut
    ) {
        if (players == null || players.isEmpty()) {
            return;
        }

        String coloredTitle = Text.color(title);
        String coloredSubtitle = Text.color(subtitle);

        for (Player player : players) {
            if (player == null || !player.isOnline()) {
                continue;
            }

            player.sendTitle(
                    coloredTitle,
                    coloredSubtitle,
                    fadeIn,
                    stay,
                    fadeOut
            );
        }
    }

    public static void send(
            Collection<? extends Player> players,
            String title,
            String subtitle,
            int fadeIn,
            int stay,
            int fadeOut,
            String... placeholders
    ) {
        if (players == null || players.isEmpty()) {
            return;
        }

        String formattedTitle = Text.format(title, placeholders);
        String formattedSubtitle = Text.format(subtitle, placeholders);

        for (Player player : players) {
            if (player == null || !player.isOnline()) {
                continue;
            }

            player.sendTitle(
                    formattedTitle,
                    formattedSubtitle,
                    fadeIn,
                    stay,
                    fadeOut
            );
        }
    }

    public static void clear(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }

        player.resetTitle();
    }

    public static void clear(Collection<? extends Player> players) {
        if (players == null || players.isEmpty()) {
            return;
        }

        for (Player player : players) {
            clear(player);
        }
    }
}