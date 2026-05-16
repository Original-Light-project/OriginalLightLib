package ol.originallightlib.test;

import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PageGuiSubCommand implements SubCommand {

    @Override
    public String name() {
        return "pagegui";
    }

    @Override
    public String description() {
        return "開啟分頁 GUI 測試";
    }

    @Override
    public String usage() {
        return "/oll pagegui";
    }

    @Override
    public String permission() {
        return "originallightlib.pagegui";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        List<String> testItems = new ArrayList<>();

        for (int i = 1; i <= 80; i++) {
            testItems.add("Test Item " + i);
        }

        new PaginatedTestGui(testItems).open(player);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}