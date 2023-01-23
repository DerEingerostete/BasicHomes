package de.dereingerostete.homes.chat;

import de.dereingerostete.homes.util.Lang;
import lombok.Getter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @author DerEingerostete
 */
public class Chat {
    private static @Getter String prefix;

    public static void setPrefix(@NotNull String prefix) {
        Chat.prefix = prefix;
    }

    public static void toPlayer(@NotNull CommandSender sender, @NotNull String message) {
        sender.sendMessage(prefix + message);
    }

    public static void toPlayer(@NotNull CommandSender sender, @NotNull String key,
                                boolean withPrefix, @NotNull Object... objects) {
        String message = Lang.getMessage(key, withPrefix, objects);
        sender.sendMessage(message);
    }

    public static void toConsole(@NotNull String message) {
        toConsole(message, true);
    }

    public static void toConsole(@NotNull String message, boolean usePrefix) {
        CommandSender sender = Bukkit.getConsoleSender();
        if (!usePrefix) sender.sendMessage(message);
        else toPlayer(sender, message);
    }

    public static void toActionBar(@NotNull Player player, @NotNull String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    public static void broadcast(@NotNull String message) {
        Consumer<Player> consumer = player -> player.sendMessage(prefix + message);
        Bukkit.getOnlinePlayers().forEach(consumer);
    }

    public static String stripColorCodes(@NotNull String string) {
        String[] colorCodes = {"§1", "§2", "§3",
                "§4", "§5", "§6",
                "§7", "§8", "§9",
                "§a", "§b", "§c",
                "§d", "§e", "§f",
                "§k", "§m", "§n",
                "§o", "§r"};
        String replaced = string;
        for (String colorCode : colorCodes)
            replaced = replaced.replaceAll(colorCode, "");
        return replaced;
    }

}
