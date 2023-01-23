/*
 * Copyright (c) 2023 × DerEingerostete
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package de.dereingerostete.homes.command.util;

import de.dereingerostete.homes.chat.Chat;
import de.dereingerostete.homes.util.Lang;
import lombok.Getter;

public class CommandMessages {
    private static final String onlyPlayerMessage;
    private static final @Getter String noPermissionsMessage;

    static {
        onlyPlayerMessage = Lang.getMessage("onlyPlayerMessage", true);
        noPermissionsMessage = Lang.getMessage("noPermissionMessage", true);
    }

    public static void sendOnlyPlayerMessage() {
        Chat.toConsole(onlyPlayerMessage);
    }

}
