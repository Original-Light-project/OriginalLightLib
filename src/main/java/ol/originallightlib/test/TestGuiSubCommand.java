package ol.originallightlib.test;

import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TestGuiSubCommand implements SubCommand {

    @Override
    public String name() {
        return "testgui";
    }

    @Override
    public String description() {
        return "開啟 OriginalLightLib 測試 GUI";
    }

    @Override
    public String usage() {
        return "/oll testgui";
    }

    @Override
    public String permission() {
        return "originallightlib.testgui";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        new TestGui().open(player);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}