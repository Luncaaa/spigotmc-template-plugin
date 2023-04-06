package me.lucaaa.template.managers

import me.lucaaa.template.Main
import me.lucaaa.template.utils.Logger
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import java.util.logging.Level

/**
 * This class creates loads the messages from the language file set in config.yml
 *
 * @constructor Loads the messages and saves them in a hashmap.
 *
 * @param langConfig The config file the user wants to get the messages from.
 */
class MessagesManager(langConfig: YamlConfiguration) {
    private val messages: HashMap<String, String> = HashMap()

    init {
        // Each key that is not a config section is added to the map along with its corresponding message
        for (key: String in langConfig.getKeys(true)) {
            if (!langConfig.isConfigurationSection(key)) this.messages[key] = langConfig.get(key).toString()
        }
    }

    /**
     * Gets a message from the language config the user has set in config.yml
     *
     * @param key The route of the message you want. For example: commands.no-permission.
     * @param placeholders A map with placeholders. The map's keys will be replaced with the values in the message.
     * @param addPrefix If the prefix from config.yml should be added at the beginning of the message.
     * @return The message you want from the language file with the placeholders replaced and colors parsed.
     */
    fun getMessage(key: String, placeholders: HashMap<String, String>?, addPrefix: Boolean = false): String {
        if (!this.messages.containsKey(key)) {
            Logger.log(Level.SEVERE, "The key $key was not found in your language file. Try to delete the file and generate it again to solve this issue.")
            return "Message not found."
        }

        var message: String = this.messages[key].toString()

        if (placeholders != null) message = this.replacePlaceholders(message, placeholders)

        if (addPrefix) message = Main.mainConfig.get("prefix").toString() + message

        return ChatColor.translateAlternateColorCodes('&', message)
    }

    /**
     * Loops through the placeholder map and replaces the keys with the values in the provided string.
     *
     * @param message The message in which you want to replace the placeholders.
     * @param placeholders A map with placeholders. The map's keys will be replaced with the values in the message.
     * @return The message with the placeholders replaced.
     */
    private fun replacePlaceholders(message: String, placeholders: HashMap<String, String>): String {
        var newMessage = message
        for ((key, value) in placeholders) {
            newMessage = newMessage.replace(key, value)
        }
        return newMessage
    }
}
