package me.lucaaa.template.commands

import me.lucaaa.template.Main
import me.lucaaa.template.commands.subCommands.HelpSubCommand
import me.lucaaa.template.commands.subCommands.ReloadSubCommand
import me.lucaaa.template.commands.subCommands.SubCommandsInterface
import me.lucaaa.template.managers.MessagesManager
import org.bukkit.command.*

class MainCommand : CommandExecutor, TabCompleter {
    companion object {
        val subCommands: HashMap<String, SubCommandsInterface> = hashMapOf(
            "help" to HelpSubCommand(),
            "reload" to ReloadSubCommand()
        )
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val messagesManager: MessagesManager = Main.messagesManager

        // If there are no arguments, show an error.
        if (args.isEmpty()) {
            sender.sendMessage(messagesManager.getMessage("commands.not-enough-arguments", null, true))
            sender.sendMessage(messagesManager.getMessage("commands.use-help-command", null, true))
            return true
        }

        // Placeholders that include the name of the subcommand.
        val placeholders = hashMapOf("%subcommand%" to args[0])

        // If the subcommand does not exist, show an error.
        if (!subCommands.containsKey(args[0])) {
            sender.sendMessage(messagesManager.getMessage("commands.command-not-found", placeholders, true))
            sender.sendMessage(messagesManager.getMessage("commands.use-help-command", placeholders, true))
            return true
        }

        // If the subcommand exists, get it from the map and add description, usage and minimum arguments to the placeholders
        val subCommand: SubCommandsInterface = subCommands[args[0]] as SubCommandsInterface
        placeholders["%description%"] = subCommand.description
        placeholders["%usage%"] = subCommand.usage
        placeholders["%minArguments%"] = subCommand.minArguments.toString()

        // If the player who ran the command does not have the needed permissions, show an error.
        if (!sender.hasPermission("plugin.admin") && (subCommand.neededPermission != null && !sender.hasPermission(subCommand.neededPermission!!))) {
            sender.sendMessage(messagesManager.getMessage("commands.no-permission", placeholders, true))
            return true
        }

        // If the command was executed by console but only players can execute it, show an error.
        if (sender is ConsoleCommandSender && !subCommand.executableByConsole) {
            sender.sendMessage(messagesManager.getMessage("commands.player-command-only", placeholders, true))
            return true
        }

        // If the user entered fewer arguments than the subcommand needs, an error will appear.
        // args.size - 1 because the name of the subcommand is not included in the minArguments
        if (args.size - 1 < subCommand.minArguments) {
            sender.sendMessage(messagesManager.getMessage("commands.not-enough-arguments", placeholders, true))
            sender.sendMessage(messagesManager.getMessage("commands.command-usage", placeholders, true))
            return true
        }

        // If the command is valid, run it.
        subCommand.run(sender, args)
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String> {
        var completions: MutableList<String> = ArrayList()

        // Tab completions for each subcommand. If the user is going to type the first argument, and it does not need any permission
        // to be executed, complete it. If it needs a permission, check if the user has it and add more completions.
        if (args.size == 1) {
            for ((key, value) in subCommands) {
                if (value.neededPermission == null) {
                    completions.add(key)
                } else if (sender.hasPermission(value.neededPermission!!) || sender.hasPermission("plugin.admin")) {
                    completions.add(key)
                }
            }
        }

        // Command's second argument.
        if (args.size >= 2 && subCommands.containsKey(args[0])) {
            completions = subCommands[args[0]]!!.getTabCompletions(sender, args)
        }

        // Filters the array so only the completions that start with what the user is typing are shown.
        // For example, it can complete "reload", "removeArena" and "help". If the user doesn't type anything, all those
        // options will appear. If the user starts typing "r", only "reload" and "removeArena" will appear.
        // args[args.size-1] -> To get the argument the user is typing (first, second...)
        return completions.filter { completion ->  completion.startsWith(args[args.size-1])}.toMutableList()
    }
}
