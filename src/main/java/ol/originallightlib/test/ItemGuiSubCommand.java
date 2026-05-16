package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.gui.selector.ItemSelectGui;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ItemGuiSubCommand implements SubCommand {

    @Override
    public String name() {
        return "itemgui";
    }

    @Override
    public String description() {
        return "開啟紀錄物品選擇 GUI";
    }

    @Override
    public String usage() {
        return "/oll itemgui";
    }

    @Override
    public String permission() {
        return "originallightlib.itemgui";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        new ItemSelectGui(
                OriginalLightLib.getInstance().getItemRegistry(),
                (targetPlayer, itemId) -> {
                    targetPlayer.sendMessage("§a你選擇了紀錄物品：§e" + itemId);
                    targetPlayer.closeInventory();
                }
        ).open(player);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}