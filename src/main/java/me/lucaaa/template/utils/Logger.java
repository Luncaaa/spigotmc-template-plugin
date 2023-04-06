package me.lucaaa.template.utils;

import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Logger {
    /**
     * Logs a message to console with the plugin's prefix.
     *
     * @param level The level of the message.
     * @param message The message you want to log.
     */
    public static void log(Level level, String message) {
        Bukkit.getLogger().log(level, "[NAME] " + message);
    }
}
