package ol.originallightlib.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandContext {

    private final CommandSender sender;
    private final Command command;
    private final String label;
    private final String[] args;

    public CommandContext(CommandSender sender, Command command, String label, String[] args) {
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;
    }

    public CommandSender sender() {
        return sender;
    }

    public Command command() {
        return command;
    }

    public String label() {
        return label;
    }

    public String[] args() {
        return args;
    }

    public int length() {
        return args.length;
    }

    public String arg(int index) {
        if (index < 0 || index >= args.length) {
            return null;
        }

        return args[index];
    }
}