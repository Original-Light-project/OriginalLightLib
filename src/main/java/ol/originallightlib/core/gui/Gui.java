package ol.originallightlib.core.gui;

import ol.originallightlib.OriginalLightLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public abstract class Gui {

    private final String title;
    private final int size;

    private final Map<Integer, GuiButton> buttons = new HashMap<>();
    private Inventory inventory;

    public Gui(String title, int size) {
        this.title = title;
        this.size = size;
    }

    public void open(Player player) {
        this.inventory = Bukkit.createInventory(new GuiHolder(this), size, title);

        buttons.clear();
        draw(player);

        for (Map.Entry<Integer, GuiButton> entry : buttons.entrySet()) {
            int slot = entry.getKey();
            GuiButton button = entry.getValue();

            inventory.setItem(slot, GuiItemMarker.mark(button.getIcon()));
        }

        player.openInventory(inventory);
        OriginalLightLib.getInstance().getGuiManager().openGui(player, this);
    }

    protected abstract void draw(Player player);

    protected void setButton(int slot, GuiButton button) {
        buttons.put(slot, button);
    }

    public GuiButton getButton(int slot) {
        return buttons.get(slot);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void onClose(Player player) {
        // 預設不做事，需要時讓子類別覆寫
    }
}