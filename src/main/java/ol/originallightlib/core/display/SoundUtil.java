package ol.originallightlib.core.display;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Collection;

public final class SoundUtil {

    private SoundUtil() {
    }

    public static void play(Player player, Sound sound) {
        play(player, sound, 1.0F, 1.0F);
    }

    public static void play(Player player, Sound sound, float volume, float pitch) {
        if (player == null || !player.isOnline() || sound == null) {
            return;
        }

        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static void play(Collection<? extends Player> players, Sound sound) {
        play(players, sound, 1.0F, 1.0F);
    }

    public static void play(Collection<? extends Player> players, Sound sound, float volume, float pitch) {
        if (players == null || players.isEmpty() || sound == null) {
            return;
        }

        for (Player player : players) {
            play(player, sound, volume, pitch);
        }
    }

    public static void click(Player player) {
        play(player, Sound.UI_BUTTON_CLICK, 0.8F, 1.2F);
    }

    public static void success(Player player) {
        play(player, Sound.ENTITY_PLAYER_LEVELUP, 0.8F, 1.5F);
    }

    public static void error(Player player) {
        play(player, Sound.ENTITY_VILLAGER_NO, 0.8F, 1.0F);
    }

    public static void cancel(Player player) {
        play(player, Sound.BLOCK_NOTE_BLOCK_BASS, 0.8F, 0.8F);
    }

    public static void select(Player player) {
        play(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.8F, 1.6F);
    }

    public static void open(Player player) {
        play(player, Sound.BLOCK_CHEST_OPEN, 0.6F, 1.2F);
    }

    public static void close(Player player) {
        play(player, Sound.BLOCK_CHEST_CLOSE, 0.6F, 1.2F);
    }

    public static void complete(Player player) {
        play(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.8F, 1.0F);
    }
}