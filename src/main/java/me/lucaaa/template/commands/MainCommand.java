package me.lucaaa.template.commands;

import me.lucaaa.template.Main;
import me.lucaaa.template.commands.subCommands.HelpSubCommand;
import me.lucaaa.template.commands.subCommands.ReloadSubCommand;
import me.lucaaa.template.commands.subCommands.SubCommandsFormat;
import me.lucaaa.template.managers.MessagesManager;
import org.bukkit.command.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainCommand implements CommandExecutor, TabCompleter {
    public static HashMap<String, SubCommandsFormat> subCommands = new HashMap<>();

    public MainCommand() {
        subCommands.put("help", new HelpSubCommand());
        subCommands.put("reload", new ReloadSubCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MessagesManager messagesManager = Main.messagesManager;

        // If there are no arguments, show an error.
        if (args.length == 0) {
            sender.sendMessage(messagesManager.getMessage("commands.not-enough-arguments", null, true));
            sender.sendMessage(messagesManager.getMessage("commands.use-help-command", null, true));
            return true;
        }

        // Placeholders that include the name of the subcommand.
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("%subcommand%", args[0]);

        // If the subcommand does not exist, show an error.
        if (!subCommands.containsKey(args[0])) {
            sender.sendMessage(messagesManager.getMessage("commands.command-not-found", placeholders, true));
            sender.sendMessage(messagesManager.getMessage("commands.use-help-command", placeholders, true));
            return true;
        }

        // If the subcommand exists, get it from the map and add description, usage and minimum arguments to the placeholders
        SubCommandsFormat subCommand = subCommands.get(args[0]);
        placeholders.put("%description%", subCommand.description);
        placeholders.put("%usage%", subCommand.usage);
        placeholders.put("%minArguments%", String.valueOf(subCommand.minArguments));

        // If the player who ran the command does not have the needed permissions, show an error.
        if (!sender.hasPermission("plugin.admin") && (subCommand.neededPermission != null && !sender.hasPermission(subCommand.neededPermission))) {
            sender.sendMessage(messagesManager.getMessage("commands.no-permission", placeholders, true));
            return true;
        }

        // If the command was executed by console but only players can execute it, show an error.
        if (sender instanceof ConsoleCommandSender && !subCommand.executableByConsole) {
            sender.sendMessage(messagesManager.getMessage("commands.player-command-only", placeholders, true));
            return true;
        }

        // If the user entered fewer arguments than the subcommand needs, an error will appear.
        // args.size - 1 because the name of the subcommand is not included in the minArguments
        if (args.length - 1 < subCommand.minArguments) {
            sender.sendMessage(messagesManager.getMessage("commands.not-enough-arguments", placeholders, true));
            sender.sendMessage(subCommand.usage);
            return true;
        }

        // If the command is valid, run it.
        try {
            subCommand.run(sender, args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> completions = new ArrayList<>();

        // Tab completions for each subcommand. If the user is going to type the first argument, and it does not need any permission
        // to be executed, complete it. If it needs a permission, check if the user has it and add more completions.
        if (args.length == 1) {
            for (Map.Entry<String, SubCommandsFormat> entry : subCommands.entrySet()) {
                if (entry.getValue().neededPermission == null) {
                    completions.add(entry.getKey());
                } else if (sender.hasPermission(entry.getValue().neededPermission) || sender.hasPermission("plugin.admin")) {
                    completions.add(entry.getKey());
                }
            }
        }

        // Command's second argument.
        if (args.length >= 2 && subCommands.containsKey(args[0])) {
            completions = subCommands.get(args[0]).getTabCompletions(sender, args);
        }

        // Filters the array so only the completions that start with what the user is typing are shown.
        // For example, it can complete "reload", "removeArena" and "help". If the user doesn't type anything, all those
        // options will appear. If the user starts typing "r", only "reload" and "removeArena" will appear.
        // args[args.size-1] -> To get the argument the user is typing (first, second...)
        return completions.stream().filter(completion -> completion.startsWith(args[args.length-1])).toList();
    }
}