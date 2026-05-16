package ol.originallightlib.test;

import ol.originallightlib.core.command.CommandManager;

public final class TestCommandRegistrar {

    private TestCommandRegistrar() {
    }

    public static void register(CommandManager commandManager) {
        commandManager.register(new TestGuiSubCommand());
        commandManager.register(new TestConfigSubCommand());
        commandManager.register(new TestItemSubCommand());

        commandManager.register(new SaveItemSubCommand());
        commandManager.register(new GetItemSubCommand());
        commandManager.register(new ItemGuiSubCommand());

        commandManager.register(new ConfirmSubCommand());
        commandManager.register(new ChatInputSubCommand());
        commandManager.register(new InputTestSubCommand());

        commandManager.register(new PageGuiSubCommand());

        commandManager.register(new SelectionToolSubCommand());
        commandManager.register(new SelectionInfoSubCommand());
        commandManager.register(new SelectionClearSubCommand());
        commandManager.register(new SelectionSaveSubCommand());
        commandManager.register(new SelectionLoadSubCommand());

        commandManager.register(new BatchTestSubCommand());
        commandManager.register(new CooldownTestSubCommand());

        commandManager.register(new ActionBarSubCommand());
        commandManager.register(new BossBarSubCommand());
        commandManager.register(new SoundSubCommand());
        commandManager.register(new TitleSubCommand());
        commandManager.register(new TimeSubCommand());
    }
}