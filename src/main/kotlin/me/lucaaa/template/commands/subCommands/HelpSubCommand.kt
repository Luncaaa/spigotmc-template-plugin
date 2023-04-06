package me.lucaaa.template.commands.subCommands

import me.lucaaa.template.Main
import org.bukkit.command.CommandSender

class HelpSubCommand : SubCommandsInterface {
    override val name: String = "help"
    override val description: String = "Information about the commands the plugin has."
    override val usage: String = "/cmd help"
    override val minArguments: Int = 0
    override val executableByConsole: Boolean = true
    override val neededPermission: String? = null

    override fun run(sender: CommandSender, args: Array<out String>) {
        for (value in Main.subCommands.values) {
            sender.sendMessage(value.usage + " - " + value.description)
        }
    }
}