package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GetItemSubCommand implements SubCommand {

    @Override
    public String name() {
        return "getitem";
    }

    @Override
    public String description() {
        return "取得 items.yml 中紀錄的物品";
    }

    @Override
    public String usage() {
        return "/oll getitem [name]";
    }

    @Override
    public String permission() {
        return "originallightlib.getitem";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        if (context.length() < 1) {
            player.sendMessage("§c用法：§e/oll getitem [name]");
            return;
        }

        String itemId = context.arg(0).toLowerCase();

        ItemStack itemStack = OriginalLightLib.getInstance()
                .getItemRegistry()
                .getItem(itemId);

        if (itemStack == null) {
            player.sendMessage("§c找不到紀錄物品：§e" + itemId);
            return;
        }

        player.getInventory().addItem(itemStack.clone());
        player.sendMessage("§a已取得紀錄物品：§e" + itemId);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(
                    OriginalLightLib.getInstance()
                            .getItemRegistry()
                            .getItemIds()
            ).stream()
                    .filter(id -> id.startsWith(args[0].toLowerCase()))
                    .sorted()
                    .toList();
        }

        return List.of();
    }
}