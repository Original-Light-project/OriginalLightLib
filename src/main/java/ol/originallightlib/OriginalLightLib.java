package ol.originallightlib;

import ol.originallightlib.core.command.CommandManager;
import ol.originallightlib.core.cooldown.CooldownManager;
import ol.originallightlib.core.debug.DebugSettings;
import ol.originallightlib.core.gui.GuiManager;
import ol.originallightlib.core.input.ChatInputManager;
import ol.originallightlib.core.item.ItemRegistry;
import ol.originallightlib.core.message.MessageManager;
import ol.originallightlib.core.region.SelectionListener;
import ol.originallightlib.core.region.SelectionManager;
import ol.originallightlib.integration.IntegrationManager;
import ol.originallightlib.test.HelpSubCommand;
import ol.originallightlib.test.ReloadSubCommand;
import ol.originallightlib.test.TestCommandRegistrar;
import org.bukkit.plugin.java.JavaPlugin;

public final class OriginalLightLib extends JavaPlugin {

    private static OriginalLightLib instance;

    private GuiManager guiManager;
    private CommandManager commandManager;
    private MessageManager messageManager;
    private ItemRegistry itemRegistry;
    private ChatInputManager chatInputManager;
    private SelectionManager selectionManager;
    private CooldownManager cooldownManager;
    private DebugSettings debugSettings;
    private IntegrationManager integrationManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        this.debugSettings = new DebugSettings(this);

        this.guiManager = new GuiManager(this);
        this.guiManager.register();

        this.chatInputManager = new ChatInputManager(this);
        this.chatInputManager.register();

        this.messageManager = new MessageManager(this);
        this.itemRegistry = new ItemRegistry(this);
        this.cooldownManager = new CooldownManager();
        this.integrationManager = new IntegrationManager(this);

        this.selectionManager = new SelectionManager();
        getServer().getPluginManager().registerEvents(
                new SelectionListener(selectionManager),
                this
        );

        this.commandManager = new CommandManager();
        this.commandManager.register(new HelpSubCommand(commandManager));
        this.commandManager.register(new ReloadSubCommand());

        if (debugSettings.isTestCommandsEnabled()) {
            TestCommandRegistrar.register(commandManager);
            debugSettings.log("Test commands registered.");
        } else {
            getLogger().info("Debug test commands are disabled.");
        }

        if (getCommand("oll") != null) {
            getCommand("oll").setExecutor(commandManager);
            getCommand("oll").setTabCompleter(commandManager);
        }

        this.integrationManager.load();

        getLogger().info("OriginalLightLib enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("OriginalLightLib disabled.");
    }

    public static OriginalLightLib getInstance() {
        return instance;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public ItemRegistry getItemRegistry() {
        return itemRegistry;
    }

    public ChatInputManager getChatInputManager() {
        return chatInputManager;
    }

    public SelectionManager getSelectionManager() {
        return selectionManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public DebugSettings getDebugSettings() {
        return debugSettings;
    }

    public IntegrationManager getIntegrationManager() {
        return integrationManager;
    }
}
