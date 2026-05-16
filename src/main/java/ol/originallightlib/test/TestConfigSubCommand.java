package ol.originallightlib.test;

import ol.originallightlib.OriginalLightLib;
import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.SubCommand;
import ol.originallightlib.core.config.YamlFile;
import org.bukkit.command.CommandSender;

import java.util.List;

public class TestConfigSubCommand implements SubCommand {

    @Override
    public String name() {
        return "testconfig";
    }

    @Override
    public String description() {
        return "測試 YamlFile 設定檔讀寫";
    }

    @Override
    public String usage() {
        return "/oll testconfig";
    }

    @Override
    public String permission() {
        return "originallightlib.testconfig";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        YamlFile yamlFile = new YamlFile(OriginalLightLib.getInstance(), "test.yml");

        int currentCount = yamlFile.getConfig().getInt("test.count", 0);
        int newCount = currentCount + 1;

        yamlFile.getConfig().set("test.count", newCount);
        yamlFile.getConfig().set("test.message", "YamlFile 測試成功");
        yamlFile.save();

        OriginalLightLib.getInstance()
                .getMessageManager()
                .send(sender, "test.config-success", "%count%", String.valueOf(newCount));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}