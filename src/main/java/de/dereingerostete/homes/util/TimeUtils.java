package de.dereingerostete.homes.util;

import org.jetbrains.annotations.NotNull;

public class TimeUtils {

    @NotNull
    public static String format(long time) {
        long seconds = Long.divideUnsigned(time, 1000);
        if (seconds > 60 * 60 * 24)
            return Lang.getMessage("timeLayout.days", false,
                    "D", seconds / 86400,
                    "H", seconds / 60 / 60 % 24,
                    "M", seconds / 60 % 60,
                    "S", seconds % 60);
        else if (seconds > 60 * 60)
            return Lang.getMessage("timeLayout.hours", false,
                    "H", seconds / 60 / 60,
                    "M", seconds / 60 % 60,
                    "S", seconds % 60);
        else if (seconds > 60)
            return Lang.getMessage("timeLayout.minutes", false,
                    "M", seconds / 60,
                    "S", seconds % 60);
        else return Lang.getMessage("timeLayout.seconds", false,
                    "S", seconds);
    }

}
