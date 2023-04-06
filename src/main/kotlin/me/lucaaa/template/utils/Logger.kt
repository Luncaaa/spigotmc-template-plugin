package me.lucaaa.template.utils

import org.bukkit.Bukkit
import java.util.logging.Level

class Logger {
    companion object {
        /**
         * Logs a message to console with the plugin's prefix.
         *
         * @param level The level of the message.
         * @param message The message you want to log.
         */
        fun log(level: Level, message: String) {
            Bukkit.getLogger().log(level, "[TEMPLATE] $message")
        }
    }
}