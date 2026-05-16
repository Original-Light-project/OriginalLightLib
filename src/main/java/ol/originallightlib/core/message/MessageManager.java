package ol.originallightlib.core.message;

import ol.originallightlib.core.config.YamlFile;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MessageManager {

    private final YamlFile messagesFile;

    public MessageManager(JavaPlugin plugin) {
        this.messagesFile = new YamlFile(plugin, "messages.yml");

        setupDefaults();
    }

    private void setupDefaults() {
        if (!messagesFile.getConfig().contains("prefix")) {
            messagesFile.getConfig().set("prefix", "&8[&eOriginalLightLib&8] ");
        }

        if (!messagesFile.getConfig().contains("command.reload-success")) {
            messagesFile.getConfig().set("command.reload-success", "%prefix%&a設定檔已重新載入。");
        }

        if (!messagesFile.getConfig().contains("command.no-permission")) {
            messagesFile.getConfig().set("command.no-permission", "%prefix%&c你沒有權限使用這個指令。");
        }

        if (!messagesFile.getConfig().contains("command.unknown")) {
            messagesFile.getConfig().set("command.unknown", "%prefix%&c未知的子指令：&e%command%");
        }

        if (!messagesFile.getConfig().contains("test.config-success")) {
            messagesFile.getConfig().set("test.config-success", "%prefix%&a已寫入 test.yml，目前 count：&e%count%");
        }

        messagesFile.save();
    }

    public void reload() {
        messagesFile.reload();
    }

    public String get(String path, String... placeholders) {
        String message = messagesFile.getConfig().getString(path);

        if (message == null) {
            return Text.color("&c找不到訊息 key：&e" + path);
        }

        String prefix = messagesFile.getConfig().getString("prefix", "");

        return Text.format(
                message,
                mergePlaceholders(placeholders, "%prefix%", prefix)
        );
    }

    public List<String> getList(String path, String... placeholders) {
        List<String> lines = messagesFile.getConfig().getStringList(path);
        String prefix = messagesFile.getConfig().getString("prefix", "");

        return Text.format(
                lines,
                mergePlaceholders(placeholders, "%prefix%", prefix)
        );
    }

    public void send(CommandSender sender, String path, String... placeholders) {
        sender.sendMessage(get(path, placeholders));
    }

    public YamlFile getMessagesFile() {
        return messagesFile;
    }

    private String[] mergePlaceholders(String[] placeholders, String key, String value) {
        String[] merged = new String[placeholders.length + 2];

        System.arraycopy(placeholders, 0, merged, 0, placeholders.length);

        merged[placeholders.length] = key;
        merged[placeholders.length + 1] = value;

        return merged;
    }
}