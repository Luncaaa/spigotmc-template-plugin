package me.lucaaa.template.commands.subCommands

import org.bukkit.command.CommandSender

interface SubCommandsInterface {
    // The name of the subcommand
    val name: String

    // The description of the subcommand
    val description: String

    // How the subcommand is used
    val usage: String

    /*How many arguments are required to execute the subcommand (name not included)
    * For example:
    * [] required, () not required
    * /cmd giveSword [player] (customName) -> minArguments would be 1 */
    val minArguments: Int

    // If the command can be executed by console or not
    val executableByConsole: Boolean

    // The permission needed to run this command other than plugin.admin. Can be null (no permission needed)
    val neededPermission: String?

    /**
     * Gets the tab completions for a command in case you want to have any. Returns an empty list by default.
     *
     * @param sender The thing that is sending the command.
     * @param args The command's arguments to complete.
     * @return A list with the completions.
     */
    fun getTabCompletions(sender: CommandSender, args: Array<out String>): MutableList<String> = ArrayList()

    /**
     * The function that will be run when the command is executed.
     *
     * @param sender The thing that sent the command.
     * @param args The command's arguments.
     */
    fun run(sender: CommandSender, args: Array<out String>)
}