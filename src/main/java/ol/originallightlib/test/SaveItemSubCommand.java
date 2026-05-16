package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SaveItemSubCommand implements SubCommand {

    @Override
    public String name() {
        return "saveitem";
    }

    @Override
    public String description() {
        return "將手持物品紀錄到 items.yml";
    }

    @Override
    public String usage() {
        return "/oll saveitem [name]";
    }

    @Override
    public String permission() {
        return "originallightlib.saveitem";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        if (context.length() < 1) {
            player.sendMessage("§c用法：§e/oll saveitem [name]");
            return;
        }

        String itemId = context.arg(0).toLowerCase();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.getType().isAir()) {
            player.sendMessage("§c你必須手持一個物品。");
            return;
        }

        OriginalLightLib.getInstance()
                .getItemRegistry()
                .saveItem(itemId, itemInHand);

        player.sendMessage("§a已將手持物品紀錄為：§e" + itemId);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}