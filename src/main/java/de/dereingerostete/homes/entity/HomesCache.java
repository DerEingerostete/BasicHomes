package de.dereingerostete.homes.entity;

import de.dereingerostete.homes.util.SchedulerUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomesCache {
    private static final Map<UUID, Homes> homesMap;

    static {
        homesMap = new HashMap<>();
    }

    @NotNull
    public static Homes getHomes(@NotNull UUID uuid) {
        Homes homes = homesMap.get(uuid);
        if (homes != null) return homes;

        homes = new Homes(uuid);
        homesMap.put(uuid, homes);

        return homes;
    }

    public static void addAsynchronous(@NotNull UUID uuid) {
        SchedulerUtils.runAsync(() -> {
            if (homesMap.containsKey(uuid)) return;
            homesMap.put(uuid, new Homes(uuid));
        });
    }

    public static void removeUUID(@NotNull UUID uuid) {
        homesMap.remove(uuid);
    }

}
