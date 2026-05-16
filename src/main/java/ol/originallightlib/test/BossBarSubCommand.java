package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.display.BossBarHandle;
import ol.originallightlib.core.display.BossBarUtil;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BossBarSubCommand implements SubCommand {

    @Override
    public String name() {
        return "bossbar";
    }

    @Override
    public String description() {
        return "測試 BossBar 顯示與進度更新";
    }

    @Override
    public String usage() {
        return "/oll bossbar";
    }

    @Override
    public String permission() {
        return "originallightlib.bossbar";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        BossBarHandle handle = BossBarUtil.create(
                player,
                "&aBossBar 測試開始",
                BarColor.GREEN,
                BarStyle.SEGMENTED_10,
                0.0D
        );

        AtomicInteger tick = new AtomicInteger(0);

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                OriginalLightLib.getInstance(),
                () -> {
                    int current = tick.incrementAndGet();
                    double progress = current / 100.0D;

                    handle.setProgress(progress);
                    handle.setTitle(
                            "&aBossBar 測試中：&e%progress%%",
                            "%progress%",
                            String.valueOf(current)
                    );

                    if (current >= 100) {
                        handle.setTitle("&aBossBar 測試完成！");
                        handle.setProgress(1.0D);

                        Bukkit.getScheduler().runTaskLater(
                                OriginalLightLib.getInstance(),
                                handle::remove,
                                20L
                        );
                    }
                },
                0L,
                1L
        );

        Bukkit.getScheduler().runTaskLater(
                OriginalLightLib.getInstance(),
                task::cancel,
                101L
        );

        player.sendMessage("§a已開始 BossBar 測試。");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}