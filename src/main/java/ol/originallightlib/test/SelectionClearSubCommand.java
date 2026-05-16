package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SelectionClearSubCommand implements SubCommand {

    @Override
    public String name() {
        return "selectionclear";
    }

    @Override
    public String description() {
        return "清除目前選區";
    }

    @Override
    public String usage() {
        return "/oll selectionclear";
    }

    @Override
    public String permission() {
        return "originallightlib.selectionclear";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        OriginalLightLib.getInstance()
                .getSelectionManager()
                .clearSelection(player);

        player.sendMessage("§a已清除目前選區。");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}