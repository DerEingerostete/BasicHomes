package de.dereingerostete.homes.chat;

import de.dereingerostete.homes.BasicHomes;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {
    private static final Logger logger = BasicHomes.getInstance().getLogger();
    private static boolean debug;

    public static void setDebug(boolean debug) {
        Logging.debug = debug;
    }

    public static void debug(Throwable throwable) {
        if (debug) warning("[DEBUG]", throwable);
    }

    public static void debug(String message) {
        if (debug) info("[DEBUG] ".concat(message));
    }

    public static void severe(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    public static void severe(String message) {
        logger.severe(message);
    }

    public static void warning(String message, Throwable throwable) {
        logger.log(Level.WARNING, message, throwable);
    }

    public static void warning(String message) {
        logger.warning(message);
    }

    public static void info(String message) {
        logger.info(message);
    }

}
