package ol.originallightlib.test;

import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.display.SoundUtil;
import ol.originallightlib.core.display.TitleUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TitleSubCommand implements SubCommand {

    @Override
    public String name() {
        return "title";
    }

    @Override
    public String description() {
        return "測試 TitleUtil 標題提示";
    }

    @Override
    public String usage() {
        return "/oll title";
    }

    @Override
    public String permission() {
        return "originallightlib.title";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        TitleUtil.send(
                player,
                "&6&lOriginalLightLib",
                "&aTitleUtil 測試成功，玩家：&f%player%",
                10,
                50,
                10,
                "%player%",
                player.getName()
        );

        SoundUtil.complete(player);
        player.sendMessage("§a已發送 Title 測試。");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}