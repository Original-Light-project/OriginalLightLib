package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatInputSubCommand implements SubCommand {

    @Override
    public String name() {
        return "chatinput";
    }

    @Override
    public String description() {
        return "測試聊天輸入系統";
    }

    @Override
    public String usage() {
        return "/oll chatinput";
    }

    @Override
    public String permission() {
        return "originallightlib.chatinput";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        OriginalLightLib.getInstance()
                .getChatInputManager()
                .awaitInput(
                        player,
                        "§e請在聊天欄輸入一段文字，系統會讀取你的下一次輸入。",
                        (targetPlayer, input) -> {
                            targetPlayer.sendMessage("§a你剛剛輸入了：§f" + input);
                        },
                        targetPlayer -> {
                            targetPlayer.sendMessage("§c你取消了這次輸入。");
                        },
                        60
                );
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}