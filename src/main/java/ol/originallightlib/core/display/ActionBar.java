package ol.originallightlib.core.display;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import ol.originallightlib.core.message.Text;
import org.bukkit.entity.Player;

import java.util.Collection;

public final class ActionBar {

    private ActionBar() {
    }

    public static void send(Player player, String message) {
        if (player == null || message == null) {
            return;
        }

        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(Text.color(message))
        );
    }

    public static void send(Player player, String message, String... placeholders) {
        if (player == null || message == null) {
            return;
        }

        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(Text.format(message, placeholders))
        );
    }

    public static void send(Collection<? extends Player> players, String message) {
        if (players == null || players.isEmpty() || message == null) {
            return;
        }

        TextComponent component = new TextComponent(Text.color(message));

        for (Player player : players) {
            if (player == null || !player.isOnline()) {
                continue;
            }

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
        }
    }

    public static void send(Collection<? extends Player> players, String message, String... placeholders) {
        if (players == null || players.isEmpty() || message == null) {
            return;
        }

        TextComponent component = new TextComponent(Text.format(message, placeholders));

        for (Player player : players) {
            if (player == null || !player.isOnline()) {
                continue;
            }

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
        }
    }
}