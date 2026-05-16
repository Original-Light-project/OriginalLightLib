package ol.originallightlib.test;

import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.display.ActionBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ActionBarSubCommand implements SubCommand {

    @Override
    public String name() {
        return "actionbar";
    }

    @Override
    public String description() {
        return "測試 ActionBar 顯示";
    }

    @Override
    public String usage() {
        return "/oll actionbar";
    }

    @Override
    public String permission() {
        return "originallightlib.actionbar";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        ActionBar.send(
                player,
                "&8[&eOLL&8] &aActionBar 測試成功，目前玩家：&f%player%",
                "%player%",
                player.getName()
        );

        player.sendMessage("§a已發送 ActionBar 測試訊息。");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}