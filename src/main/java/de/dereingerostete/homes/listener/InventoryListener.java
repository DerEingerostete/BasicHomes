package de.dereingerostete.homes.listener;

import de.dereingerostete.homes.chat.Chat;
import de.dereingerostete.homes.entity.Homes;
import de.dereingerostete.homes.entity.HomesCache;
import de.dereingerostete.homes.util.inventory.InventoryBorder;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent event) {
        InventoryView view = event.getView();
        String title = view.getTitle();
        if (!title.equals(Homes.GUI_TITLE)) return;
        event.setCancelled(true);

        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null || itemStack.equals(InventoryBorder.DEFAULT_BORDER)) return;

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;
        String displayName = Chat.stripColorCodes(itemMeta.getDisplayName());

        HumanEntity entity = event.getWhoClicked();
        UUID uuid = entity.getUniqueId();

        Homes homes = HomesCache.getHomes(uuid);
        Location location = homes.getHome(displayName);
        if (location != null) entity.teleport(location);
    }

}
