package ol.originallightlib.core.cooldown;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public void setCooldown(Player player, String key, long seconds) {
        if (player == null || key == null || key.isBlank()) {
            return;
        }

        if (seconds <= 0) {
            removeCooldown(player, key);
            return;
        }

        long expireAt = System.currentTimeMillis() + seconds * 1000L;

        cooldowns
                .computeIfAbsent(player.getUniqueId(), uuid -> new HashMap<>())
                .put(normalizeKey(key), expireAt);
    }

    public boolean hasCooldown(Player player, String key) {
        if (player == null || key == null || key.isBlank()) {
            return false;
        }

        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());

        if (playerCooldowns == null) {
            return false;
        }

        String normalizedKey = normalizeKey(key);
        Long expireAt = playerCooldowns.get(normalizedKey);

        if (expireAt == null) {
            return false;
        }

        if (System.currentTimeMillis() >= expireAt) {
            playerCooldowns.remove(normalizedKey);

            if (playerCooldowns.isEmpty()) {
                cooldowns.remove(player.getUniqueId());
            }

            return false;
        }

        return true;
    }

    public long getRemainingMillis(Player player, String key) {
        if (!hasCooldown(player, key)) {
            return 0L;
        }

        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());

        if (playerCooldowns == null) {
            return 0L;
        }

        Long expireAt = playerCooldowns.get(normalizeKey(key));

        if (expireAt == null) {
            return 0L;
        }

        return Math.max(0L, expireAt - System.currentTimeMillis());
    }

    public double getRemainingSeconds(Player player, String key) {
        return getRemainingMillis(player, key) / 1000.0D;
    }

    public long getRemainingSecondsRoundedUp(Player player, String key) {
        long millis = getRemainingMillis(player, key);

        if (millis <= 0) {
            return 0L;
        }

        return (long) Math.ceil(millis / 1000.0D);
    }

    public void removeCooldown(Player player, String key) {
        if (player == null || key == null || key.isBlank()) {
            return;
        }

        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());

        if (playerCooldowns == null) {
            return;
        }

        playerCooldowns.remove(normalizeKey(key));

        if (playerCooldowns.isEmpty()) {
            cooldowns.remove(player.getUniqueId());
        }
    }

    public void clearCooldowns(Player player) {
        if (player == null) {
            return;
        }

        cooldowns.remove(player.getUniqueId());
    }

    public void clearAll() {
        cooldowns.clear();
    }

    public int getPlayerCooldownCount(Player player) {
        if (player == null) {
            return 0;
        }

        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());

        if (playerCooldowns == null) {
            return 0;
        }

        return playerCooldowns.size();
    }

    private String normalizeKey(String key) {
        return key.toLowerCase();
    }
}