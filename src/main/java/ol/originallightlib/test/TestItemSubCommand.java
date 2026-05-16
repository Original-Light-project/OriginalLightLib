package ol.originallightlib.test;

import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.item.ItemMatcher;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TestItemSubCommand implements SubCommand {

    @Override
    public String name() {
        return "testitem";
    }

    @Override
    public String description() {
        return "測試 ItemMatcher 物品比對與扣除";
    }

    @Override
    public String usage() {
        return "/oll testitem";
    }

    @Override
    public String permission() {
        return "originallightlib.testitem";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.getType().isAir()) {
            player.sendMessage("§c你必須手持一個物品。");
            return;
        }

        ItemStack targetItem = itemInHand.clone();
        targetItem.setAmount(1);

        int count = ItemMatcher.countItem(player, targetItem);

        player.sendMessage("§7你背包中相同物品數量：§e" + count);

        if (count < 3) {
            player.sendMessage("§c數量不足，至少需要 3 個。");
            return;
        }

        boolean success = ItemMatcher.removeItem(player, targetItem, 3);

        if (!success) {
            player.sendMessage("§c扣除物品失敗。");
            return;
        }

        player.sendMessage("§a已成功扣除 3 個相同物品。");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}