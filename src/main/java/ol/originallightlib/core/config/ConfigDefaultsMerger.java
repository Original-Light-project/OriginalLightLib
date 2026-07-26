package ol.originallightlib.core.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class ConfigDefaultsMerger {

    private ConfigDefaultsMerger() {
    }

    public static int mergeConfig(JavaPlugin plugin) {
        return merge(plugin, "config.yml");
    }

    public static int merge(JavaPlugin plugin, String resourcePath) {
        if (plugin == null || resourcePath == null || resourcePath.isBlank()) {
            return 0;
        }
        File file = new File(plugin.getDataFolder(), resourcePath);
        if (!file.exists()) {
            plugin.saveResource(resourcePath, false);
            return 0;
        }
        try (InputStream input = plugin.getResource(resourcePath)) {
            if (input == null) {
                plugin.getLogger().warning("Could not merge " + resourcePath + " defaults: bundled resource is missing.");
                return 0;
            }
            YamlConfiguration current = YamlConfiguration.loadConfiguration(file);
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(input, StandardCharsets.UTF_8)
            );
            int added = 0;
            for (String key : defaults.getKeys(true)) {
                if (defaults.isConfigurationSection(key) || current.contains(key)) {
                    continue;
                }
                current.set(key, defaults.get(key));
                added++;
            }
            if (added > 0) {
                current.save(file);
            }
            return added;
        } catch (IOException exception) {
            plugin.getLogger().warning("Could not merge " + resourcePath + " defaults: " + exception.getMessage());
            return 0;
        }
    }
}
