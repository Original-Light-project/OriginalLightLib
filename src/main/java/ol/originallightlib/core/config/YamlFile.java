package ol.originallightlib.core.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class YamlFile {

    private final JavaPlugin plugin;
    private final String fileName;
    private final File file;

    private YamlConfiguration config;

    public YamlFile(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.file = new File(plugin.getDataFolder(), fileName);

        load();
    }

    public void load() {
        if (!file.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                file.createNewFile();
            } catch (IOException exception) {
                plugin.getLogger().severe("無法建立設定檔：" + fileName);
                exception.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException exception) {
            plugin.getLogger().severe("無法儲存設定檔：" + fileName);
            exception.printStackTrace();
        }
    }

    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }

    public String getFileName() {
        return fileName;
    }
}