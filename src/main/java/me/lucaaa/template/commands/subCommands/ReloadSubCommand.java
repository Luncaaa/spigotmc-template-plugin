package me.lucaaa.template.commands.subCommands;

import me.lucaaa.template.Main;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand extends SubCommandsFormat {
    public ReloadSubCommand() {
        this.name = "reload";
        this.description = "Reloads the plugin's configuration files.";
        this.usage = "/cmd reload";
        this.minArguments = 0;
        this.executableByConsole = true;
        this.neededPermission = "cmd.reload";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        Main.reloadConfigs();
        sender.sendMessage(Main.messagesManager.getMessage("commands.reload-successful", null, true));
    }
}