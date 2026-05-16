package ol.originallightlib.core.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public final class ItemMatcher {

    private ItemMatcher() {
    }

    public static boolean isSimilar(ItemStack source, ItemStack target) {
        if (source == null || target == null) {
            return false;
        }

        if (source.getType().isAir() || target.getType().isAir()) {
            return false;
        }

        ItemStack sourceClone = source.clone();
        ItemStack targetClone = target.clone();

        sourceClone.setAmount(1);
        targetClone.setAmount(1);

        return sourceClone.isSimilar(targetClone);
    }

    public static int countItem(Player player, ItemStack targetItem) {
        if (player == null || targetItem == null) {
            return 0;
        }

        int count = 0;
        PlayerInventory inventory = player.getInventory();

        for (ItemStack itemStack : inventory.getStorageContents()) {
            if (!isSimilar(itemStack, targetItem)) {
                continue;
            }

            count += itemStack.getAmount();
        }

        return count;
    }

    public static boolean hasItem(Player player, ItemStack targetItem, int amount) {
        if (amount <= 0) {
            return true;
        }

        return countItem(player, targetItem) >= amount;
    }

    public static boolean removeItem(Player player, ItemStack targetItem, int amount) {
        if (player == null || targetItem == null) {
            return false;
        }

        if (amount <= 0) {
            return true;
        }

        if (!hasItem(player, targetItem, amount)) {
            return false;
        }

        PlayerInventory inventory = player.getInventory();
        int remaining = amount;

        ItemStack[] contents = inventory.getStorageContents();

        for (int slot = 0; slot < contents.length; slot++) {
            ItemStack current = contents[slot];

            if (!isSimilar(current, targetItem)) {
                continue;
            }

            int currentAmount = current.getAmount();

            if (currentAmount <= remaining) {
                remaining -= currentAmount;
                contents[slot] = null;
            } else {
                current.setAmount(currentAmount - remaining);
                contents[slot] = current;
                remaining = 0;
            }

            if (remaining <= 0) {
                break;
            }
        }

        inventory.setStorageContents(contents);
        player.updateInventory();

        return true;
    }
}