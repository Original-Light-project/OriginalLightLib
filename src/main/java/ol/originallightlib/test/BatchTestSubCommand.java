package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.task.BatchTask;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class BatchTestSubCommand implements SubCommand {

    @Override
    public String name() {
        return "batchtest";
    }

    @Override
    public String description() {
        return "測試 BatchTask 分批任務";
    }

    @Override
    public String usage() {
        return "/oll batchtest";
    }

    @Override
    public String permission() {
        return "originallightlib.batchtest";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        List<Integer> numbers = new ArrayList<>();

        for (int i = 1; i <= 1000; i++) {
            numbers.add(i);
        }

        sender.sendMessage("§a開始 BatchTask 測試，共 §e1000 §a筆資料。");

        BatchTask.of(numbers)
                .perTick(100)
                .interval(1L)
                .onEach(number -> {
                    // 測試用：這裡不需要每筆都發訊息，否則會洗頻。
                })
                .onTick(task -> {
                    sender.sendMessage("§7進度：§e"
                            + task.getProcessedCount()
                            + "§7/"
                            + task.getTotalCount()
                            + " §8("
                            + String.format("%.1f", task.getProgress() * 100)
                            + "%)");
                })
                .onComplete(() -> {
                    sender.sendMessage("§aBatchTask 測試完成。");
                })
                .start(OriginalLightLib.getInstance());
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}