package ol.originallightlib.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiManager {

    private final JavaPlugin plugin;
    private final Map<UUID, Gui> openedGuis = new HashMap<>();

    public GuiManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(new GuiListener(this, plugin), plugin);
    }

    public void openGui(Player player, Gui gui) {
        openedGuis.put(player.getUniqueId(), gui);
    }

    public Gui getOpenedGui(Player player) {
        return openedGuis.get(player.getUniqueId());
    }

    public void closeGui(Player player) {
        openedGuis.remove(player.getUniqueId());
    }
}