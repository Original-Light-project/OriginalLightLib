package ol.originallightlib.core.region;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectionManager {

    private final Map<UUID, PlayerSelection> selections = new HashMap<>();

    public PlayerSelection getSelection(Player player) {
        return selections.computeIfAbsent(
                player.getUniqueId(),
                uuid -> new PlayerSelection()
        );
    }

    public void setPos1(Player player, Location location) {
        PlayerSelection selection = getSelection(player);
        selection.setPos1(BlockPosition.fromLocation(location));
    }

    public void setPos2(Player player, Location location) {
        PlayerSelection selection = getSelection(player);
        selection.setPos2(BlockPosition.fromLocation(location));
    }

    public boolean hasSelection(Player player) {
        return selections.containsKey(player.getUniqueId());
    }

    public void clearSelection(Player player) {
        selections.remove(player.getUniqueId());
    }

    public CuboidRegion getRegion(Player player) {
        PlayerSelection selection = getSelection(player);

        if (!selection.isComplete()) {
            return null;
        }

        return selection.toRegion();
    }

    public void setSelection(Player player, PlayerSelection selection) {
        if (player == null || selection == null) {
            return;
        }

        selections.put(player.getUniqueId(), selection);
    }
}