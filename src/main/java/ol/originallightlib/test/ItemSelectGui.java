package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.gui.Gui;
import ol.originallightlib.core.gui.GuiButton;
import ol.originallightlib.core.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemSelectGui extends Gui {

    public ItemSelectGui() {
        super("§8選擇紀錄物品", 54);
    }

    @Override
    protected void draw(Player player) {
        Set<String> itemIds = OriginalLightLib.getInstance()
                .getItemRegistry()
                .getItemIds();

        if (itemIds.isEmpty()) {
            setButton(22, new GuiButton(
                    ItemBuilder.of(Material.BARRIER)
                            .name("&c沒有已紀錄的物品")
                            .lore(
                                    "&7請先使用：",
                                    "&e/oll saveitem [name]",
                                    "",
                                    "&7將手持物品紀錄到 items.yml"
                            )
                            .build(),
                    event -> player.sendMessage("§c目前沒有任何已紀錄物品。")
            ));

            return;
        }

        List<String> sortedIds = new ArrayList<>(itemIds);
        sortedIds.sort(String::compareToIgnoreCase);

        int slot = 0;

        for (String itemId : sortedIds) {
            if (slot >= 54) {
                break;
            }

            ItemStack savedItem = OriginalLightLib.getInstance()
                    .getItemRegistry()
                    .getItem(itemId);

            if (savedItem == null || savedItem.getType().isAir()) {
                continue;
            }

            ItemStack icon = ItemBuilder.of(savedItem)
                    .amount(1)
                    .build();

            setButton(slot, new GuiButton(icon, event -> {
                player.sendMessage("§a你選擇了紀錄物品：§e" + itemId);
                player.closeInventory();
            }));

            slot++;
        }
    }
}