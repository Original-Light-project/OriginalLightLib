package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.cooldown.CooldownManager;
import ol.originallightlib.core.util.TimeFormatter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CooldownTestSubCommand implements SubCommand {

    private static final String TEST_KEY = "cooldown_test";

    @Override
    public String name() {
        return "cooldown";
    }

    @Override
    public String description() {
        return "測試 CooldownManager 冷卻功能";
    }

    @Override
    public String usage() {
        return "/oll cooldown";
    }

    @Override
    public String permission() {
        return "originallightlib.cooldown";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        CooldownManager cooldownManager = OriginalLightLib.getInstance()
                .getCooldownManager();

        if (cooldownManager.hasCooldown(player, TEST_KEY)) {
            long remainingMillis = cooldownManager.getRemainingMillis(player, TEST_KEY);
            String remainingText = TimeFormatter.formatMillis(remainingMillis);

            player.sendMessage("§c你還在冷卻中，剩餘 §e" + remainingText + "§c。");
            return;
        }

        cooldownManager.setCooldown(player, TEST_KEY, 5);

        player.sendMessage("§a成功執行測試冷卻指令。");
        player.sendMessage("§7接下來 §e5 §7秒內再次使用會被阻擋。");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}