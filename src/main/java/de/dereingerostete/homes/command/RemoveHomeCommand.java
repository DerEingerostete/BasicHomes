/*
 * Copyright (c) 2023 × DerEingerostete
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package de.dereingerostete.homes.command;

import de.dereingerostete.homes.chat.Chat;
import de.dereingerostete.homes.command.util.CommandMessages;
import de.dereingerostete.homes.command.util.SimpleCommand;
import de.dereingerostete.homes.entity.Homes;
import de.dereingerostete.homes.entity.HomesCache;
import de.dereingerostete.homes.util.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class RemoveHomeCommand extends SimpleCommand {

    public RemoveHomeCommand() {
        super("removehome", true);
        setUsage('/' + getName());
        setDescription(Lang.getMessage("removeHomeDescription", false));
        setPermission("basichomes.removehome");
        addAliases("delhome", "deletehome");
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

        if (args.length == 0) {
            Chat.toPlayer(player, "removeHomeUsage", true);
            return;
        }

        String key = args[0];
        if (!homes.isKeySet(key)) {
            Chat.toPlayer(player, "homesKeyNotFound", true);
            return;
        }

        boolean success = homes.removeHome(key);
        if (success) Chat.toPlayer(player, "removeHomeSuccess", true);
        else Chat.toPlayer(player, "unexpectedError", true);
    }
}
