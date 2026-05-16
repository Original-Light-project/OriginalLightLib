package ol.originallightlib.core.region;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class SelectionTool {

    private static final NamespacedKey SELECTION_TOOL_KEY =
            new NamespacedKey(OriginalLightLib.getInstance(), "selection_tool");

    private SelectionTool() {
    }

    public static ItemStack createTool() {
        ItemStack tool = ItemBuilder.of(Material.GOLDEN_AXE)
                .name("&6&lOriginalLight 選區工具")
                .lore(
                        "&7左鍵方塊：&f設定第一個點 &8(pos1)",
                        "&7右鍵方塊：&f設定第二個點 &8(pos2)",
                        "",
                        "&8此工具僅用於插件選區設定"
                )
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        return mark(tool);
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
                SELECTION_TOOL_KEY,
                PersistentDataType.BYTE,
                (byte) 1
        );

        clone.setItemMeta(meta);

        return clone;
    }

    public static boolean isSelectionTool(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().isAir()) {
            return false;
        }

        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return false;
        }

        Byte value = meta.getPersistentDataContainer().get(
                SELECTION_TOOL_KEY,
                PersistentDataType.BYTE
        );

        return value != null && value == (byte) 1;
    }
}