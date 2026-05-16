package ol.originallightlib.core.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
        event.setResult(org.bukkit.event.Event.Result.DENY);

        int rawSlot = event.getRawSlot();
        int topSize = event.getView().getTopInventory().getSize();

        /*
         * 不要清除 event.getCurrentItem()
         *
         * 因為玩家點擊 GUI 上方區域時，
         * currentItem 本來就是 GUI icon。
         *
         * 如果這裡 setCurrentItem(null)，
         * GUI 內的按鈕圖示就會被清掉。
         */

        if (GuiItemMarker.isGuiItem(event.getCursor())) {
            event.setCursor(null);
        }

        Bukkit.getScheduler().runTask(plugin, () -> GuiItemMarker.clearGuiItemsFromPlayer(player));

        if (rawSlot < 0 || rawSlot >= topSize) {
            return;
        }

        if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
            return;
        }

        GuiButton button = gui.getButton(rawSlot);

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
        event.setResult(org.bukkit.event.Event.Result.DENY);

        Bukkit.getScheduler().runTask(plugin, () -> GuiItemMarker.clearGuiItemsFromPlayer(player));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (!(event.getView().getTopInventory().getHolder() instanceof GuiHolder)) {
            return;
        }

        int topSize = event.getView().getTopInventory().getSize();

        for (int rawSlot : event.getRawSlots()) {
            if (rawSlot < topSize) {
                event.setCancelled(true);
                event.setResult(org.bukkit.event.Event.Result.DENY);

                Bukkit.getScheduler().runTask(plugin, () -> GuiItemMarker.clearGuiItemsFromPlayer(player));
                return;
            }
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