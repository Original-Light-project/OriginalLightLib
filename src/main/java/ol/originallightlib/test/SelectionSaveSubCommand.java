package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.config.YamlFile;
import ol.originallightlib.core.region.PlayerSelection;
import ol.originallightlib.core.region.RegionSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SelectionSaveSubCommand implements SubCommand {

    @Override
    public String name() {
        return "selectionsave";
    }

    @Override
    public String description() {
        return "將目前選區儲存到 selections.yml";
    }

    @Override
    public String usage() {
        return "/oll selectionsave [name]";
    }

    @Override
    public String permission() {
        return "originallightlib.selectionsave";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        if (context.length() < 1) {
            player.sendMessage("§c用法：§e/oll selectionsave [name]");
            return;
        }

        String selectionId = context.arg(0).toLowerCase();

        PlayerSelection selection = OriginalLightLib.getInstance()
                .getSelectionManager()
                .getSelection(player);

        if (!selection.isComplete()) {
            player.sendMessage("§c目前選區尚未完整，請先設定 pos1 和 pos2。");
            return;
        }

        if (!selection.isSameWorld()) {
            player.sendMessage("§cpos1 和 pos2 不在同一個世界，無法儲存。");
            return;
        }

        YamlFile selectionsFile = new YamlFile(OriginalLightLib.getInstance(), "selections.yml");

        RegionSerializer.savePlayerSelection(
                selectionsFile.getConfig(),
                "selections." + selectionId,
                selection
        );

        selectionsFile.save();

        player.sendMessage("§a已儲存選區：§e" + selectionId);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}