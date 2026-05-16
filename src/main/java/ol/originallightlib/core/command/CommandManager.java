package ol.originallightlib.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public void register(SubCommand subCommand) {
        subCommands.put(subCommand.name().toLowerCase(), subCommand);
    }

    public Collection<SubCommand> getSubCommands() {
        return subCommands.values();
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (args.length == 0) {
            SubCommand help = subCommands.get("help");

            if (help != null) {
                help.execute(new CommandContext(sender, command, label, new String[0]));
                return true;
            }

            sender.sendMessage("§c請輸入子指令。");
            return true;
        }

        String subName = args[0].toLowerCase();
        SubCommand subCommand = subCommands.get(subName);

        if (subCommand == null) {
            sender.sendMessage("§c未知的子指令：§e" + args[0]);
            sender.sendMessage("§7請使用 §e/" + label + " help §7查看可用指令。");
            return true;
        }

        String permission = subCommand.permission();

        if (permission != null && !permission.isBlank() && !sender.hasPermission(permission)) {
            sender.sendMessage("§c你沒有權限使用這個指令。");
            return true;
        }

        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        subCommand.execute(new CommandContext(sender, command, label, subArgs));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (args.length == 1) {
            return subCommands.keySet().stream()
                    .filter(name -> name.startsWith(args[0].toLowerCase()))
                    .sorted()
                    .toList();
        }

        SubCommand subCommand = subCommands.get(args[0].toLowerCase());

        if (subCommand == null) {
            return List.of();
        }

        String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
        return subCommand.tabComplete(sender, subArgs);
    }
}