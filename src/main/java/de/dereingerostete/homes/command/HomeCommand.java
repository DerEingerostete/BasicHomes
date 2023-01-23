package de.dereingerostete.homes.command;

import com.google.common.collect.Lists;
import de.dereingerostete.homes.chat.Chat;
import de.dereingerostete.homes.command.util.CommandMessages;
import de.dereingerostete.homes.command.util.SimpleCommand;
import de.dereingerostete.homes.entity.Homes;
import de.dereingerostete.homes.entity.HomesCache;
import de.dereingerostete.homes.util.Lang;
import de.dereingerostete.homes.util.SchedulerUtils;
import de.dereingerostete.homes.util.inventory.InventoryBorder;
import de.dereingerostete.homes.util.inventory.InventoryInfo;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class HomeCommand extends SimpleCommand {

    public HomeCommand() {
        super("home", true);
        setUsage('/' + getName());
        setDescription(Lang.getMessage("homeDescription", false));
        setPermission("basichomes.home");
        setPermissionMessage(CommandMessages.getNoPermissionsMessage());
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args, int arguments) {
        CommandMessages.sendOnlyPlayerMessage();
    }

    @Override
    public void execute(@NotNull Player player, @NotNull String[] args, int arguments) {
        UUID uuid = player.getUniqueId();
        Homes homes = HomesCache.getHomes(uuid);

        if (homes.hadError()) {
            Homes.sendErrorMessage(player);
            return;
        }

        if (args.length == 0) SchedulerUtils.runTask(() -> showGUI(player, homes));
        else handleSpecifiedHome(player, homes, args[0]);
    }

    private void handleSpecifiedHome(@NotNull Player player,  @NotNull Homes homes, @NotNull String key) {
        Location location = homes.getHome(key);
        if (location != null) {
            Chat.toPlayer(player, "homesTeleporting", true);
            SchedulerUtils.runTask(() -> player.teleport(location));
        } else Chat.toPlayer(player, "homesKeyNotFound", true);
    }

    private void showGUI(@NotNull Player player, @NotNull Homes homes) {
        InventoryInfo info = new InventoryInfo(9, 3, Homes.GUI_TITLE);
        Inventory inventory = InventoryBorder.createWithBorder(info);

        Set<String> keySet = homes.getKeys();
        if (keySet.isEmpty()) {
            ItemStack itemStack = new ItemStack(Material.BARRIER);
            ItemMeta itemMeta = Objects.requireNonNull(itemStack.getItemMeta());

            String message = Lang.getMessage("noHomesSet", false);
            itemMeta.setDisplayName("§6" + message);
            itemStack.setItemMeta(itemMeta);

            inventory.setItem(13, itemStack);
            player.openInventory(inventory);
            return;
        }

        int i = 11;
        boolean single = keySet.size() == 1;
        for (String key : keySet) {
            ItemStack itemStack = new ItemStack(Material.OAK_DOOR);
            ItemMeta itemMeta = Objects.requireNonNull(itemStack.getItemMeta());
            itemMeta.setDisplayName("§6" + key);

            String lore = Lang.getMessage("homesItemLore", false);
            itemMeta.setLore(Lists.newArrayList(lore));
            itemStack.setItemMeta(itemMeta);

            if (!single) inventory.setItem(i, itemStack);
            else inventory.setItem(13, itemStack);
            i += 2;
        }

        player.openInventory(inventory);
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias,
                                    @NotNull String[] args) throws IllegalArgumentException {
        if (!(sender instanceof Player player) || args.length != 0) return Lists.newArrayList();
        UUID uuid = player.getUniqueId();
        Homes homes = HomesCache.getHomes(uuid);
        Set<String> keys = homes.getKeys();
        return Lists.newArrayList(keys);
    }

}
