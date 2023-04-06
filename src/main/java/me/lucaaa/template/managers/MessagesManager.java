package me.lucaaa.template.managers;

import me.lucaaa.template.Main;
import me.lucaaa.template.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

/**
 * This class creates loads the messages from the language file set in config.yml
 */
public class MessagesManager {
    private final HashMap<String, String> messages = new HashMap<>();

    /**
     * @param langConfig The config file the user wants to get the messages from.
     */
    public MessagesManager(YamlConfiguration langConfig) {
        // Each key that is not a config section is added to the map along with its corresponding message
        for (String key : langConfig.getKeys(true)) {
            if (!langConfig.isConfigurationSection(key)) this.messages.put(key, Objects.requireNonNull(langConfig.get(key)).toString());
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
    public String getMessage(String key, HashMap<String, String> placeholders, boolean addPrefix) {
        if (!this.messages.containsKey(key)) {
            Logger.log(Level.SEVERE, "The key $key was not found in your language file. Try to delete the file and generate it again to solve this issue.");
            return "Message not found.";
        }

        String message = this.messages.get(key);

        if (placeholders != null) message = this.replacePlaceholders(message, placeholders);

        if (addPrefix) message = Main.mainConfig.get("prefix") + message;

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Loops through the placeholder map and replaces the keys with the values in the provided string.
     *
     * @param message The message in which you want to replace the placeholders.
     * @param placeholders A map with placeholders. The map's keys will be replaced with the values in the message.
     * @return The message with the placeholders replaced.
     */
    private String replacePlaceholders(String message, HashMap<String, String> placeholders) {
        var newMessage = message;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            newMessage = message.replace(entry.getKey(), entry.getValue());
        }
        return newMessage;
    }
}