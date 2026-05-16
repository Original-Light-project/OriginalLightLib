package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.region.BlockPosition;
import ol.originallightlib.core.region.CuboidRegion;
import ol.originallightlib.core.region.PlayerSelection;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SelectionInfoSubCommand implements SubCommand {

    @Override
    public String name() {
        return "selectioninfo";
    }

    @Override
    public String description() {
        return "查看目前選區資訊";
    }

    @Override
    public String usage() {
        return "/oll selectioninfo";
    }

    @Override
    public String permission() {
        return "originallightlib.selectioninfo";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        PlayerSelection selection = OriginalLightLib.getInstance()
                .getSelectionManager()
                .getSelection(player);

        sender.sendMessage("§8§m━━━━━━━━━━━━━━§r §eSelection Info §8§m━━━━━━━━━━━━━━");

        sendPosition(sender, "pos1", selection.getPos1());
        sendPosition(sender, "pos2", selection.getPos2());

        if (!selection.isComplete()) {
            sender.sendMessage("§c目前選區尚未完整。");
            sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return;
        }

        if (!selection.isSameWorld()) {
            sender.sendMessage("§c兩個選區點不在同一個世界。");
            sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return;
        }

        CuboidRegion region = selection.toRegion();

        if (region == null) {
            sender.sendMessage("§c無法建立選區。");
            sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return;
        }

        sender.sendMessage("§7世界：§e" + region.getWorldName());
        sender.sendMessage("§7X：§e" + region.getMinX() + " §7~ §e" + region.getMaxX());
        sender.sendMessage("§7Y：§e" + region.getMinY() + " §7~ §e" + region.getMaxY());
        sender.sendMessage("§7Z：§e" + region.getMinZ() + " §7~ §e" + region.getMaxZ());
        sender.sendMessage("§7方塊數：§e" + region.getVolume());

        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    private void sendPosition(CommandSender sender, String name, BlockPosition position) {
        if (position == null) {
            sender.sendMessage("§7" + name + "：§c未設定");
            return;
        }

        sender.sendMessage("§7" + name + "：§e" + position.toDisplayString());
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}