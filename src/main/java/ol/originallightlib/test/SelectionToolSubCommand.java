package ol.originallightlib.test;

import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.region.SelectionTool;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SelectionToolSubCommand implements SubCommand {

    @Override
    public String name() {
        return "selectiontool";
    }

    @Override
    public String description() {
        return "取得 OriginalLightLib 選區工具";
    }

    @Override
    public String usage() {
        return "/oll selectiontool";
    }

    @Override
    public String permission() {
        return "originallightlib.selectiontool";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        ItemStack tool = SelectionTool.createTool();

        player.getInventory().addItem(tool);
        player.sendMessage("§a已取得 OriginalLightLib 選區工具。");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}