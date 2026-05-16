package ol.originallightlib.core.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class GuiButton {

    private final ItemStack icon;
    private final Consumer<InventoryClickEvent> clickAction;

    public GuiButton(ItemStack icon, Consumer<InventoryClickEvent> clickAction) {
        this.icon = icon;
        this.clickAction = clickAction;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void onClick(InventoryClickEvent event) {
        if (clickAction != null) {
            clickAction.accept(event);
        }
    }
}