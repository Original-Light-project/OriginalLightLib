package ol.originallightlib.core.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiListener implements Listener {

    private final GuiManager guiManager;
    private final JavaPlugin plugin;

    public GuiListener(GuiManager guiManager, JavaPlugin plugin) {
        this.guiManager = guiManager;
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getView().getTopInventory().getHolder() instanceof GuiHolder holder)) {
            return;
        }

        Gui gui = holder.getGui();

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

        int rawSlot = event.getRawSlot();
        int topSize = event.getView().getTopInventory().getSize();

        if (GuiItemMarker.isGuiItem(event.getCursor())) {
            event.setCursor(null);
        }

        Bukkit.getScheduler().runTask(plugin, () -> GuiItemMarker.clearGuiItemsFromPlayer(player));

        if (rawSlot < 0) {
            return;
        }

        if (rawSlot >= topSize) {
            if (gui.allowsPlayerInventoryInteraction() && gui.isPlayerInventoryClickAllowed(event)) {
                event.setCancelled(false);
                event.setResult(Event.Result.DEFAULT);
            }
            return;
        }

        GuiButton button = gui.getButton(rawSlot);

        if (button == null && gui.isEditableSlot(rawSlot)) {
            if (!gui.isEditableClickAllowed(event)) {
                return;
            }
            event.setCancelled(false);
            event.setResult(Event.Result.DEFAULT);
            gui.onEditableClick(event);
            return;
        }

        ClickType click = event.getClick();
        if (click != ClickType.LEFT && click != ClickType.RIGHT
                && click != ClickType.SHIFT_LEFT && click != ClickType.SHIFT_RIGHT) {
            return;
        }

        if (button == null) {
            return;
        }

        button.onClick(event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryCreative(InventoryCreativeEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getView().getTopInventory().getHolder() instanceof GuiHolder)) {
            return;
        }

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

        Bukkit.getScheduler().runTask(plugin, () -> GuiItemMarker.clearGuiItemsFromPlayer(player));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getView().getTopInventory().getHolder() instanceof GuiHolder holder)) {
            return;
        }

        Gui gui = holder.getGui();
        int topSize = event.getView().getTopInventory().getSize();
        boolean touchesTop = false;

        for (int rawSlot : event.getRawSlots()) {
            if (rawSlot >= topSize) {
                continue;
            }

            touchesTop = true;
            if (!gui.isEditableSlot(rawSlot)) {
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);

                Bukkit.getScheduler().runTask(plugin, () -> GuiItemMarker.clearGuiItemsFromPlayer(player));
                return;
            }
        }

        if (touchesTop) {
            event.setCancelled(false);
            event.setResult(Event.Result.DEFAULT);
            gui.onEditableDrag(event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory().getHolder() instanceof GuiHolder holder)) {
            return;
        }

        Gui gui = holder.getGui();

        gui.onClose(player);
        guiManager.closeGui(player);

        Bukkit.getScheduler().runTask(plugin, () -> GuiItemMarker.clearGuiItemsFromPlayer(player));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        guiManager.closeGui(player);
        GuiItemMarker.clearGuiItemsFromPlayer(player);
    }
}
