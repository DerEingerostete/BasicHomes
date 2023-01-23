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
