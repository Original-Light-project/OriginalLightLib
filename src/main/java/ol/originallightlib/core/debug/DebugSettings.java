package ol.originallightlib.core.debug;

import org.bukkit.plugin.java.JavaPlugin;

public class DebugSettings {

    private final JavaPlugin plugin;

    private boolean enabled;
    private boolean testCommands;
    private boolean verboseLog;

    public DebugSettings(JavaPlugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        plugin.reloadConfig();

        this.enabled = plugin.getConfig().getBoolean("debug.enabled", false);
        this.testCommands = plugin.getConfig().getBoolean("debug.test-commands", false);
        this.verboseLog = plugin.getConfig().getBoolean("debug.verbose-log", false);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isTestCommandsEnabled() {
        return enabled && testCommands;
    }

    public boolean isVerboseLogEnabled() {
        return enabled && verboseLog;
    }

    public void log(String message) {
        if (!isVerboseLogEnabled()) {
            return;
        }

        plugin.getLogger().info("[Debug] " + message);
    }
}