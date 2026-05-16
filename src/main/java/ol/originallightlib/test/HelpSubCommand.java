package ol.originallightlib.test;

import ol.originallightlib.core.command.CommandContext;
import ol.originallightlib.core.command.CommandManager;
import ol.originallightlib.core.command.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpSubCommand implements SubCommand {

    private final CommandManager commandManager;

    public HelpSubCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String description() {
        return "查看 OriginalLightLib 指令說明";
    }

    @Override
    public String usage() {
        return "/oll help";
    }

    @Override
    public String permission() {
        return "originallightlib.command.help";
    }

    @Override
    public void execute(CommandContext context) {
        CommandSender sender = context.sender();

        sender.sendMessage("§8§m━━━━━━━━━━━━━━§r §eOriginalLightLib §8§m━━━━━━━━━━━━━━");

        for (SubCommand subCommand : commandManager.getSubCommands()) {
            if (subCommand.permission() != null && !subCommand.permission().isBlank()) {
                if (!sender.hasPermission(subCommand.permission())) {
                    continue;
                }
            }

            sender.sendMessage("§e" + subCommand.usage() + " §7- " + subCommand.description());
        }

        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}