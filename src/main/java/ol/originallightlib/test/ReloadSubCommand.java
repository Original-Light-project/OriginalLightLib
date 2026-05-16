package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadSubCommand implements SubCommand {

    @Override
    public String name() {
        return "reload";
    }

    @Override
    public String description() {
        return "重新載入 OriginalLightLib 設定檔";
    }

    @Override
    public String usage() {
        return "/oll reload";
    }

    @Override
    public String permission() {
        return "originallightlib.reload";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        OriginalLightLib plugin = OriginalLightLib.getInstance();

        plugin.reloadConfig();
        plugin.getDebugSettings().reload();

        plugin.getMessageManager().reload();
        plugin.getItemRegistry().reload();

        plugin.getMessageManager()
                .send(sender, "command.reload-success");

        sender.sendMessage("§7提醒：如果你修改了 §edebug.test-commands§7，請重啟伺服器以完整套用指令註冊狀態。");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}