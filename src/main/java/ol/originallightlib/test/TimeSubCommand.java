package ol.originallightlib.test;

import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.input.InputValidator;
import ol.originallightlib.core.util.TimeFormatter;
import org.bukkit.command.CommandSender;

import java.util.List;

public class TimeSubCommand implements SubCommand {

    @Override
    public String name() {
        return "time";
    }

    @Override
    public String description() {
        return "測試 TimeFormatter 時間格式化工具";
    }

    @Override
    public String usage() {
        return "/oll time [seconds]";
    }

    @Override
    public String permission() {
        return "originallightlib.time";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (context.length() < 1) {
            sender.sendMessage("§c用法：§e/oll time [seconds]");
            return;
        }

        Long seconds = InputValidator.parseLong(context.arg(0));

        if (seconds == null || seconds < 0) {
            sender.sendMessage("§c請輸入有效的非負整數秒數。");
            return;
        }

        sender.sendMessage("§8§m━━━━━━━━━━━━━━§r §eTimeFormatter §8§m━━━━━━━━━━━━━━");
        sender.sendMessage("§7輸入秒數：§e" + seconds);
        sender.sendMessage("§7中文格式：§e" + TimeFormatter.formatSeconds(seconds));
        sender.sendMessage("§7短格式：§e" + TimeFormatter.formatSecondsShort(seconds));
        sender.sendMessage("§7時鐘格式：§e" + TimeFormatter.formatClock(seconds));
        sender.sendMessage("§7Tick 格式：§e" + TimeFormatter.formatTicks(seconds * 20L));
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return List.of("5", "30", "60", "90", "3600")
                    .stream()
                    .filter(value -> value.startsWith(args[0]))
                    .toList();
        }

        return List.of();
    }
}