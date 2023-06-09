package me.lucaaa.template

import me.lucaaa.template.commands.MainCommand
import me.lucaaa.template.commands.subCommands.SubCommandsInterface
import me.lucaaa.template.managers.ConfigManager
import me.lucaaa.template.managers.MessagesManager
import me.lucaaa.template.utils.Logger
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Level

class Main : JavaPlugin() {
    companion object {
        // An instance of the plugin.
        private lateinit var plugin: Plugin

        // Subcommands for the HelpSubCommand class.
        val subCommands: HashMap<String, SubCommandsInterface> = MainCommand.subCommands

        // Config & lang file.
        lateinit var mainConfig: YamlConfiguration
        lateinit var langConfig: YamlConfiguration

        // Managers.
        lateinit var messagesManager: MessagesManager

        // Reload the config files.
        fun reloadConfigs() {
            // Creates the config and lang files.
            this.createConfigs()
            this.mainConfig =  ConfigManager(this.plugin, "config.yml").getConfig()
            
            // Loads the lang file the user wants.
            this.langConfig = ConfigManager(this.plugin, "langs" + File.separator + this.mainConfig.get("language")).getConfig()
            
            // Managers
            this.messagesManager = MessagesManager(this.langConfig)
        }

        // If the config files do not exist, create them.
        private fun createConfigs() {
            if (!File(plugin.dataFolder.absolutePath + File.separator + "config.yml").exists()) plugin.saveResource("config.yml", false)
            if (!File(plugin.dataFolder.absolutePath + File.separator + "langs" + File.separator + "en.yml").exists()) plugin.saveResource("langs" + File.separator + "en.yml", false)
            if (!File(plugin.dataFolder.absolutePath + File.separator + "langs" + File.separator + "es.yml").exists()) plugin.saveResource("langs" + File.separator + "es.yml", false)
        }
    }

    override fun onEnable() {
        plugin = this

        // Set up files and managers.
        reloadConfigs()

        // Registers the main command and adds tab completions.
        this.getCommand("cmd")?.setExecutor(MainCommand())
        this.getCommand("cmd")?.tabCompleter = MainCommand()

        Logger.log(Level.INFO, "The plugin has been enabled.")
    }
}
