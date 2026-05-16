package ol.originallightlib.core.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    private ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    private ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack.clone();
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder of(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder name(String name) {
        if (itemMeta == null) {
            return this;
        }

        itemMeta.setDisplayName(color(name));
        return this;
    }

    public ItemBuilder lore(String... lore) {
        if (itemMeta == null) {
            return this;
        }

        List<String> coloredLore = Arrays.stream(lore)
                .map(this::color)
                .toList();

        itemMeta.setLore(coloredLore);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        if (itemMeta == null) {
            return this;
        }

        List<String> coloredLore = lore.stream()
                .map(this::color)
                .toList();

        itemMeta.setLore(coloredLore);
        return this;
    }

    public ItemBuilder customModelData(int data) {
        if (itemMeta == null) {
            return this;
        }

        itemMeta.setCustomModelData(data);
        return this;
    }

    public ItemBuilder glow() {
        if (itemMeta == null) {
            return this;
        }

        itemMeta.addEnchant(Enchantment.UNBREAKING, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        return this;
    }

    public ItemBuilder flags(ItemFlag... flags) {
        if (itemMeta == null) {
            return this;
        }

        itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemStack build() {
        if (itemMeta != null) {
            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }

    private String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}