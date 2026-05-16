package ol.originallightlib.core.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {

    String name();

    String description();

    String usage();

    String permission();

    void execute(CommandContext context);

    default List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}