package ol.originallightlib.test;

import ol.originallightlib.core.gui.Gui;
import ol.originallightlib.core.gui.GuiButton;
import ol.originallightlib.core.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TestGui extends Gui {

    public TestGui() {
        super("§8OriginalLightLib 測試 GUI", 27);
    }

    @Override
    protected void draw(Player player) {
        setButton(11, new GuiButton(
                ItemBuilder.of(Material.CHEST)
                        .name("&a箱子按鈕")
                        .lore(
                                "&7這是一個使用 ItemBuilder 建立的按鈕",
                                "",
                                "&e點擊測試 lambda method"
                        )
                        .build(),
                event -> handleChestClick(player, event)
        ));

        setButton(13, new GuiButton(
                ItemBuilder.of(Material.DIAMOND)
                        .name("&b鑽石按鈕")
                        .lore(
                                "&7這是一個發光的測試按鈕",
                                "",
                                "&e點擊測試 method reference"
                        )
                        .glow()
                        .build(),
                this::handleDiamondClick
        ));

        setButton(15, new GuiButton(
                ItemBuilder.of(Material.BARRIER)
                        .name("&c關閉")
                        .lore("&7點擊關閉此 GUI")
                        .build(),
                event -> player.closeInventory()
        ));
    }

    private void handleChestClick(Player player, InventoryClickEvent event) {
        player.sendMessage("§a你點擊了 §e箱子按鈕§a。");
        player.sendMessage("§7點擊格子：" + event.getRawSlot());
    }

    private void handleDiamondClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        player.sendMessage("§b你點擊了 §f鑽石按鈕§b。");
        player.sendMessage("§7點擊類型：" + event.getClick().name());
    }

    @Override
    public void onClose(Player player) {
        player.sendMessage("§7你關閉了測試 GUI。");
    }
}