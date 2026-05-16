package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.input.InputValidator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class InputTestSubCommand implements SubCommand {

    @Override
    public String name() {
        return "inputtest";
    }

    @Override
    public String description() {
        return "測試 ChatInput 與 InputValidator";
    }

    @Override
    public String usage() {
        return "/oll inputtest";
    }

    @Override
    public String permission() {
        return "originallightlib.inputtest";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        player.closeInventory();

        OriginalLightLib.getInstance()
                .getChatInputManager()
                .awaitInput(
                        player,
                        "§e請輸入一個 1 ~ 100 的整數：",
                        (targetPlayer, input) -> {
                            Integer value = InputValidator.parseInt(input);

                            if (value == null) {
                                targetPlayer.sendMessage("§c輸入無效，請輸入整數。");
                                return;
                            }

                            if (!InputValidator.isInRange(value, 1, 100)) {
                                targetPlayer.sendMessage("§c數字超出範圍，請輸入 1 ~ 100。");
                                return;
                            }

                            targetPlayer.sendMessage("§a輸入成功，你輸入的數字是：§e" + value);
                        },
                        targetPlayer -> {
                            targetPlayer.sendMessage("§7已取消輸入。");
                        },
                        60
                );
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}