package de.dereingerostete.homes.listener;

import de.dereingerostete.homes.entity.HomesCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ConnectListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onJoin(@NotNull PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        HomesCache.addAsynchronous(uuid);
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(@NotNull PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        HomesCache.removeUUID(uuid);
    }

}
