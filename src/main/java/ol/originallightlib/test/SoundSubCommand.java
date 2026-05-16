package ol.originallightlib.test;

import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.display.SoundUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SoundSubCommand implements SubCommand {

    @Override
    public String name() {
        return "sound";
    }

    @Override
    public String description() {
        return "測試 SoundUtil 音效工具";
    }

    @Override
    public String usage() {
        return "/oll sound [type]";
    }

    @Override
    public String permission() {
        return "originallightlib.sound";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        if (context.length() < 1) {
            player.sendMessage("§c用法：§e/oll sound [click/success/error/cancel/select/open/close/complete]");
            return;
        }

        String type = context.arg(0).toLowerCase();

        switch (type) {
            case "click" -> {
                SoundUtil.click(player);
                player.sendMessage("§a播放音效：§eclick");
            }
            case "success" -> {
                SoundUtil.success(player);
                player.sendMessage("§a播放音效：§esuccess");
            }
            case "error" -> {
                SoundUtil.error(player);
                player.sendMessage("§a播放音效：§eerror");
            }
            case "cancel" -> {
                SoundUtil.cancel(player);
                player.sendMessage("§a播放音效：§ecancel");
            }
            case "select" -> {
                SoundUtil.select(player);
                player.sendMessage("§a播放音效：§eselect");
            }
            case "open" -> {
                SoundUtil.open(player);
                player.sendMessage("§a播放音效：§eopen");
            }
            case "close" -> {
                SoundUtil.close(player);
                player.sendMessage("§a播放音效：§eclose");
            }
            case "complete" -> {
                SoundUtil.complete(player);
                player.sendMessage("§a播放音效：§ecomplete");
            }
            default -> {
                SoundUtil.error(player);
                player.sendMessage("§c未知音效類型：§e" + type);
            }
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return List.of(
                            "click",
                            "success",
                            "error",
                            "cancel",
                            "select",
                            "open",
                            "close",
                            "complete"
                    ).stream()
                    .filter(type -> type.startsWith(args[0].toLowerCase()))
                    .toList();
        }

        return List.of();
    }
}