package me.lucaaa.template.commands.subCommands

import me.lucaaa.template.Main
import org.bukkit.command.CommandSender

class ReloadSubCommand : SubCommandsInterface {
    override val name: String = "reload"
    override val description: String = "Reloads the plugin's configuration files."
    override val usage: String = "/cmd reload"
    override val minArguments: Int = 0
    override val executableByConsole: Boolean = true
    override val neededPermission: String = "plugin.reload"

    override fun run(sender: CommandSender, args: Array<out String>) {
        Main.reloadConfigs()
        sender.sendMessage(Main.messagesManager.getMessage("commands.reload-successful", null, true))
    }
}