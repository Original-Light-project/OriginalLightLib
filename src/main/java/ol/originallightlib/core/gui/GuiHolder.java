package ol.originallightlib.core.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class GuiHolder implements InventoryHolder {

    private final Gui gui;

    public GuiHolder(Gui gui) {
        this.gui = gui;
    }

    public Gui getGui() {
        return gui;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return gui.getInventory();
    }
}