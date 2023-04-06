package me.lucaaa.template;

import me.lucaaa.template.commands.MainCommand;
import me.lucaaa.template.commands.subCommands.SubCommandsFormat;
import me.lucaaa.template.managers.ConfigManager;
import me.lucaaa.template.managers.MessagesManager;
import me.lucaaa.template.utils.Logger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    static Plugin plugin;

    // Subcommands for the HelpSubCommand class.
    public static HashMap<String, SubCommandsFormat> subCommands = MainCommand.subCommands;

    // Config & lang file.
    public static YamlConfiguration mainConfig;
    public static YamlConfiguration langConfig;

    // Managers.
    public static MessagesManager messagesManager;

    @Override
    public void onEnable() {
        plugin = this;

        // Saves the default config file and adds it to the mainConfig variable.
        this.saveDefaultConfig();
        mainConfig = (YamlConfiguration) this.getConfig();

        // Creates the lang files and loads the lang file the user wants.
        createConfigs();
        langConfig = new ConfigManager(this, "langs" + File.separator + mainConfig.get("language")).getConfig();

        // Managers
        messagesManager = new MessagesManager(langConfig);

        // Registers the main command and adds tab completions.
        Objects.requireNonNull(this.getCommand("cmd")).setExecutor(new MainCommand());
        Objects.requireNonNull(this.getCommand("cmd")).setTabCompleter(new MainCommand());

        Logger.log(Level.INFO, "The plugin has been enabled.");
    }

    // Reload the config files.
    public static void reloadConfigs() {
        createConfigs();
        mainConfig =  new ConfigManager(plugin, "config.yml").getConfig();
        langConfig = new ConfigManager(plugin, "langs" + File.separator + mainConfig.get("language")).getConfig();
        messagesManager = new MessagesManager(langConfig);
    }

    // If the config files do not exist, create them.
    private static void createConfigs() {
        if (!new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "config.yml").exists()) plugin.saveResource("config.yml", false);
        if (!new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "langs" + File.separator + "en.yml").exists()) plugin.saveResource("langs" + File.separator + "en.yml", false);
        if (!new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "langs" + File.separator + "es.yml").exists()) plugin.saveResource("langs" + File.separator + "es.yml", false);
    }
}