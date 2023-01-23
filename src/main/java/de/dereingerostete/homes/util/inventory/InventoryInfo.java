package de.dereingerostete.homes.util.inventory;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class InventoryInfo {
    private final int rows;
    private final int columns;
    private final @NotNull String title;

}
