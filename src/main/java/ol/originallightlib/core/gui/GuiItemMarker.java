package ol.originallightlib.core.gui;

import ol.originallightlib.OriginalLightLib;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class GuiItemMarker {

    private static final NamespacedKey GUI_ITEM_KEY =
            new NamespacedKey(OriginalLightLib.getInstance(), "gui_item");

    private GuiItemMarker() {
    }

    public static ItemStack mark(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().isAir()) {
            return itemStack;
        }

        ItemStack clone = itemStack.clone();
        ItemMeta meta = clone.getItemMeta();

        if (meta == null) {
            return clone;
        }

        meta.getPersistentDataContainer().set(
                GUI_ITEM_KEY,
                PersistentDataType.BYTE,
                (byte) 1
        );

        clone.setItemMeta(meta);
        return clone;
    }

    public static boolean isGuiItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().isAir()) {
            return false;
        }

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return false;
        }

        Byte value = meta.getPersistentDataContainer().get(
                GUI_ITEM_KEY,
                PersistentDataType.BYTE
        );

        return value != null && value == (byte) 1;
    }

    public static void clearGuiItemsFromPlayer(Player player) {
        if (player == null) {
            return;
        }

        PlayerInventory inventory = player.getInventory();

        ItemStack cursor = player.getItemOnCursor();

        if (isGuiItem(cursor)) {
            player.setItemOnCursor(null);
        }

        ItemStack[] contents = inventory.getContents();

        for (int slot = 0; slot < contents.length; slot++) {
            if (isGuiItem(contents[slot])) {
                contents[slot] = null;
            }
        }

        inventory.setContents(contents);
        player.updateInventory();
    }
}