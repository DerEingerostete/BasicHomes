/*
 * Copyright (c) 2023 × DerEingerostete
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package de.dereingerostete.homes.util;

import de.dereingerostete.homes.BasicHomes;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class SchedulerUtils {

    @NotNull
    public static BukkitTask runLaterAsync(@NotNull Runnable runnable, long delay) {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        Plugin plugin = BasicHomes.getInstance();
        return scheduler.runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    public static void runAsync(@NotNull Runnable runnable) {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        Plugin plugin = BasicHomes.getInstance();
        scheduler.runTaskAsynchronously(plugin, runnable);
    }

    public static void runLater(@NotNull Runnable runnable, long delay) {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        Plugin plugin = BasicHomes.getInstance();
        scheduler.runTaskLater(plugin, runnable, delay);
    }

    public static void runTask(@NotNull Runnable runnable) {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        Plugin plugin = BasicHomes.getInstance();
        scheduler.runTask(plugin, runnable);
    }

}
