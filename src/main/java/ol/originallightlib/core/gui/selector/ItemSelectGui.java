package ol.originallightlib.core.gui.selector;

import ol.originallightlib.core.gui.GuiButton;
import ol.originallightlib.core.gui.page.PaginatedGui;
import ol.originallightlib.core.item.ItemBuilder;
import ol.originallightlib.core.item.ItemRegistry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ItemSelectGui extends PaginatedGui<String> {

    public record Text(String title, String emptyName, List<String> emptyLore, String emptyMessage) {
        public static Text defaults() {
            return new Text("§8選擇紀錄物品", "&c沒有已紀錄的物品",
                    List.of("&7目前沒有任何可選擇的紀錄物品。", "", "&7請先使用對應指令紀錄物品。"),
                    "§c目前沒有任何已紀錄物品。");
        }
    }

    private final ItemRegistry itemRegistry;
    private final BiConsumer<Player, String> selectAction;
    private final Text text;

    public ItemSelectGui(ItemRegistry itemRegistry, BiConsumer<Player, String> selectAction) {
        this(itemRegistry, selectAction, Text.defaults());
    }

    public ItemSelectGui(ItemRegistry itemRegistry, BiConsumer<Player, String> selectAction, Text text) {
        super((text == null ? Text.defaults() : text).title(), 54, getSortedItemIds(itemRegistry));

        this.itemRegistry = itemRegistry;
        this.selectAction = selectAction;
        this.text = text == null ? Text.defaults() : text;
    }

    @Override
    protected GuiButton createItemButton(Player player, String itemId, int index) {
        ItemStack savedItem = itemRegistry.getItem(itemId);

        if (savedItem == null || savedItem.getType().isAir()) {
            return null;
        }

        ItemStack icon = ItemBuilder.of(savedItem)
                .amount(1)
                .build();

        return new GuiButton(icon, event -> {
            if (selectAction != null) {
                selectAction.accept(player, itemId);
            }
        });
    }

    @Override
    protected void drawEmpty(Player player) {
        setButton(22, new GuiButton(
                ItemBuilder.of(Material.BARRIER)
                        .name(text.emptyName())
                        .lore(text.emptyLore())
                        .build(),
                event -> player.sendMessage(text.emptyMessage())
        ));
    }

    private static List<String> getSortedItemIds(ItemRegistry itemRegistry) {
        if (itemRegistry == null) {
            return List.of();
        }

        List<String> itemIds = new ArrayList<>(itemRegistry.getItemIds());
        itemIds.sort(String::compareToIgnoreCase);

        return itemIds;
    }
}
