package me.lucaaa.template.commands.subCommands;

import me.lucaaa.template.Main;
import org.bukkit.command.CommandSender;

public class HelpSubCommand extends SubCommandsFormat {
    public HelpSubCommand() {
        this.name = "help";
        this.description = "Information about the commands the plugin has.";
        this.usage = "/cmd help";
        this.minArguments = 0;
        this.executableByConsole = true;
        this.neededPermission = null;
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        for (SubCommandsFormat value : Main.subCommands.values()) {
            sender.sendMessage(value.usage + " - " + value.description);
        }
    }
}
