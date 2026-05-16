package ol.originallightlib.test;

import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.gui.common.ConfirmGui;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ConfirmSubCommand implements SubCommand {

    @Override
    public String name() {
        return "confirm";
    }

    @Override
    public String description() {
        return "開啟確認視窗測試 GUI";
    }

    @Override
    public String usage() {
        return "/oll confirm";
    }

    @Override
    public String permission() {
        return "originallightlib.confirm";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        ConfirmGui.builder()
                .title("§8確認測試")
                .infoItem(Material.PAPER, "&f測試操作")
                .description(
                        "&7這是一個 ConfirmGui 測試視窗。",
                        "",
                        "&a確認：&7會送出成功訊息",
                        "&c取消：&7會送出取消訊息",
                        "&e返回：&7會送出返回訊息"
                )
                .confirmItem(Material.LIME_CONCRETE, "&a確認執行")
                .cancelItem(Material.RED_CONCRETE, "&c取消操作")
                .backItem(Material.ARROW, "&e返回上一頁")
                .onConfirm(target -> {
                    target.sendMessage("§a你確認了這個操作。");
                })
                .onCancel(target -> {
                    target.sendMessage("§c你取消了這個操作。");
                })
                .onBack(target -> {
                    target.sendMessage("§e你選擇返回。");
                })
                .fillBorder(true)
                .open(player);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}