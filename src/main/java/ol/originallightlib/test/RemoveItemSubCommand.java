package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class RemoveItemSubCommand implements SubCommand {

    @Override
    public String name() {
        return "removeitem";
    }

    @Override
    public String description() {
        return "移除 items.yml 中紀錄的物品";
    }

    @Override
    public String usage() {
        return "/oll removeitem [name]";
    }

    @Override
    public String permission() {
        return "originallightlib.removeitem";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (context.length() < 1) {
            sender.sendMessage("§c用法：§e/oll removeitem [name]");
            return;
        }

        String itemId = context.arg(0).toLowerCase();

        if (!OriginalLightLib.getInstance().getItemRegistry().hasItem(itemId)) {
            sender.sendMessage("§c找不到紀錄物品：§e" + itemId);
            return;
        }

        OriginalLightLib.getInstance().getItemRegistry().removeItem(itemId);

        sender.sendMessage("§a已移除紀錄物品：§e" + itemId);
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