package me.lucaaa.template.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Loads or creates a config file from a path.
 */
public class ConfigManager {
    private final File file;
    private final YamlConfiguration config;

    /**
     *
     * @param plugin An instance of the plugin (main class).
     * @param path The path to the config file. Do not include the plugin's folder.
     */
    public ConfigManager(Plugin plugin, String path) {
        this.file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator +  path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * Saves the config to a file.
     *
     * @throws IOException Any error occurred while saving the config to a file.
     */
    public void save() throws IOException {
        this.config.save(this.file);
    }

    /**
     * Gets the config's file.
     *
     * @return The config's file.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Gets the config file.
     *
     * @return The config file.
     */
    public YamlConfiguration getConfig() {
        return this.config;
    }
}