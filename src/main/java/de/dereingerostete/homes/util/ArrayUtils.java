package de.dereingerostete.homes.util;

import org.jetbrains.annotations.NotNull;

public class ArrayUtils {

    public static @NotNull String toString(Object @NotNull [] array, String connector) {
        int maxIndex = array.length - 1;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; ; i++) {
            builder.append(array[i]);
            if (i == maxIndex) break;
            builder.append(connector);
        }
        return builder.toString();
    }

    public static @NotNull String toLines(Object[] array) {
        return toString(array, "\n");
    }

}
