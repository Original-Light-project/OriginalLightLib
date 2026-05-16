package ol.originallightlib.test;

import ol.originallightlib.core.gui.GuiButton;
import ol.originallightlib.core.gui.page.PaginatedGui;
import ol.originallightlib.core.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class PaginatedTestGui extends PaginatedGui<String> {

    public PaginatedTestGui(List<String> items) {
        super("§8分頁 GUI 測試", 54, items);
    }

    @Override
    protected GuiButton createItemButton(Player player, String item, int index) {
        return new GuiButton(
                ItemBuilder.of(Material.BOOK)
                        .name("&a測試項目 #" + (index + 1))
                        .lore(
                                "&7資料內容：&f" + item,
                                "",
                                "&e點擊選擇"
                        )
                        .build(),
                event -> {
                    player.sendMessage("§a你點擊了：§e" + item);
                }
        );
    }
}