package ol.originallightlib.core.region;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class SelectionListener implements Listener {

    private final SelectionManager selectionManager;

    public SelectionListener(SelectionManager selectionManager) {
        this.selectionManager = selectionManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Action action = event.getAction();

        if (action != Action.LEFT_CLICK_BLOCK && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (!SelectionTool.isSelectionTool(itemInHand)) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) {
            return;
        }

        event.setCancelled(true);

        Location location = clickedBlock.getLocation();

        if (action == Action.LEFT_CLICK_BLOCK) {
            selectionManager.setPos1(player, location);

            player.sendMessage("§a已設定第一個選區點：§e"
                    + formatLocation(location));

            sendRegionInfoIfComplete(player);
            return;
        }

        if (action == Action.RIGHT_CLICK_BLOCK) {
            selectionManager.setPos2(player, location);

            player.sendMessage("§a已設定第二個選區點：§e"
                    + formatLocation(location));

            sendRegionInfoIfComplete(player);
        }
    }

    private void sendRegionInfoIfComplete(Player player) {
        PlayerSelection selection = selectionManager.getSelection(player);

        if (!selection.isComplete()) {
            return;
        }

        if (!selection.isSameWorld()) {
            player.sendMessage("§c兩個選區點不在同一個世界，無法形成選區。");
            return;
        }

        CuboidRegion region = selection.toRegion();

        if (region == null) {
            return;
        }

        player.sendMessage("§7目前選區大小：§e" + region.getVolume() + " §7blocks");
    }

    private String formatLocation(Location location) {
        return location.getWorld().getName()
                + " "
                + location.getBlockX()
                + ", "
                + location.getBlockY()
                + ", "
                + location.getBlockZ();
    }
}