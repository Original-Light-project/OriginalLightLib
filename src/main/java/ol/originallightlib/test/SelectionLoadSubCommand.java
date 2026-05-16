package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.config.YamlFile;
import ol.originallightlib.core.region.PlayerSelection;
import ol.originallightlib.core.region.RegionSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SelectionLoadSubCommand implements SubCommand {

    @Override
    public String name() {
        return "selectionload";
    }

    @Override
    public String description() {
        return "從 selections.yml 載入選區";
    }

    @Override
    public String usage() {
        return "/oll selectionload [name]";
    }

    @Override
    public String permission() {
        return "originallightlib.selectionload";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c只有玩家可以使用這個指令。");
            return;
        }

        if (context.length() < 1) {
            player.sendMessage("§c用法：§e/oll selectionload [name]");
            return;
        }

        String selectionId = context.arg(0).toLowerCase();

        YamlFile selectionsFile = new YamlFile(OriginalLightLib.getInstance(), "selections.yml");

        if (!selectionsFile.getConfig().contains("selections." + selectionId)) {
            player.sendMessage("§c找不到選區紀錄：§e" + selectionId);
            return;
        }

        PlayerSelection selection = RegionSerializer.loadPlayerSelection(
                selectionsFile.getConfig(),
                "selections." + selectionId
        );

        if (selection == null || !selection.isComplete()) {
            player.sendMessage("§c選區資料不完整，無法載入。");
            return;
        }

        if (!selection.isSameWorld()) {
            player.sendMessage("§c選區的兩個點不在同一個世界，無法載入。");
            return;
        }

        OriginalLightLib.getInstance()
                .getSelectionManager()
                .setSelection(player, selection);

        player.sendMessage("§a已載入選區：§e" + selectionId);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            YamlFile selectionsFile = new YamlFile(OriginalLightLib.getInstance(), "selections.yml");

            if (!selectionsFile.getConfig().contains("selections")) {
                return List.of();
            }

            if (selectionsFile.getConfig().getConfigurationSection("selections") == null) {
                return List.of();
            }

            return new ArrayList<>(
                    selectionsFile.getConfig()
                            .getConfigurationSection("selections")
                            .getKeys(false)
            ).stream()
                    .filter(id -> id.startsWith(args[0].toLowerCase()))
                    .sorted()
                    .toList();
        }

        return List.of();
    }
}