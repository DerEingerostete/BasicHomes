package de.dereingerostete.homes.util.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InventoryBorder {
    public static final ItemStack DEFAULT_BORDER = loadDefaultBorder();

    @NotNull
    public static Inventory createWithBorder(@NotNull InventoryInfo info) {
        return createWithBorder(info, DEFAULT_BORDER);
    }

    @NotNull
    public static Inventory createWithBorder(@NotNull InventoryInfo info, @NotNull ItemStack borderItem) {
        int rows = info.getRows();
        int columns = info.getColumns();
        String title = info.getTitle();

        return createWithBorder(rows, columns, title, borderItem);
    }

    @NotNull
    public static Inventory createWithBorder(int rows, int columns, @NotNull String title,
                                             @NotNull  ItemStack borderItem) {
        Inventory inventory = Bukkit.createInventory(null, rows * columns, title);
        for (int i = 0; i < columns; i++) {
            if (i == 0 || i == columns - 1) {
                for (int j = 0; j < rows; j++)
                    inventory.setItem(j + i * rows, borderItem);
                continue;
            }

            inventory.setItem(i * rows, borderItem);
            inventory.setItem(i * rows + rows - 1, borderItem);
        }

        return inventory;
    }

    public static int getBorderItemsAmount(@NotNull InventoryInfo info) {
        int rows = info.getRows();
        int columns = info.getColumns();
        return getBorderItemsAmount(rows, columns);
    }

    public static int getBorderItemsAmount(int rows, int columns) {
        int amount = 0;
        for (int i = 0; i < rows; i++) {
            if (i == 0 || i == rows - 1)
                amount += columns;
            else amount += 2;
        }

        return amount;
    }

    @NotNull
    private static ItemStack loadDefaultBorder() {
        Material material = Material.GRAY_STAINED_GLASS_PANE;
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = Objects.requireNonNull(itemStack.getItemMeta());
        itemMeta.setDisplayName("§7");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
