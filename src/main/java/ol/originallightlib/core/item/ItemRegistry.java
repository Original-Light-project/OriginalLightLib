package ol.originallightlib.core.item;

import ol.originallightlib.core.config.YamlFile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class ItemRegistry {

    private final YamlFile itemsFile;

    public ItemRegistry(JavaPlugin plugin) {
        this(plugin, "items.yml");
    }

    public ItemRegistry(JavaPlugin plugin, String fileName) {
        this.itemsFile = new YamlFile(plugin, fileName);
    }

    public void saveItem(String id, ItemStack itemStack) {
        if (id == null || id.isBlank()) {
            return;
        }

        if (itemStack == null || itemStack.getType().isAir()) {
            return;
        }

        ItemStack savedItem = itemStack.clone();
        savedItem.setAmount(1);

        itemsFile.getConfig().set("items." + id.toLowerCase(), savedItem);
        itemsFile.save();
    }

    public ItemStack getItem(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }

        return itemsFile.getConfig().getItemStack("items." + id.toLowerCase());
    }

    public boolean hasItem(String id) {
        if (id == null || id.isBlank()) {
            return false;
        }

        return itemsFile.getConfig().contains("items." + id.toLowerCase());
    }

    public void removeItem(String id) {
        if (id == null || id.isBlank()) {
            return;
        }

        itemsFile.getConfig().set("items." + id.toLowerCase(), null);
        itemsFile.save();
    }

    public Set<String> getItemIds() {
        if (!itemsFile.getConfig().contains("items")) {
            return Set.of();
        }

        if (itemsFile.getConfig().getConfigurationSection("items") == null) {
            return Set.of();
        }

        return itemsFile.getConfig()
                .getConfigurationSection("items")
                .getKeys(false);
    }

    public void reload() {
        itemsFile.reload();
    }

    public YamlFile getItemsFile() {
        return itemsFile;
    }
}