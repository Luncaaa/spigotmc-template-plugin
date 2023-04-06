package me.lucaaa.template.managers

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

/**
 * Loads or creates a config file from a path.
 *
 * @constructor Gets and loads the config file.
 *
 * @param plugin An instance of the plugin (main class).
 * @param path The path to the config file. Do not include the plugin's folder.
 */
class ConfigManager(plugin: Plugin, path: String) {
    private val file: File
    private val config: YamlConfiguration

    init {
        this.file = File(plugin.dataFolder.absolutePath + File.separator + path)
        this.config = YamlConfiguration.loadConfiguration(this.file)
    }

    /**
     * Saves the config to a file.
     */
    fun save() {
        this.config.save(this.file)
    }

    /**
     * Gets the config's file.
     *
     * @return The config's file.
     */
    fun getFile(): File {
        return this.file
    }

    /**
     * Gets the config file.
     *
     * @return The config file.
     */
    fun getConfig(): YamlConfiguration {
        return this.config
    }
}