package de.dereingerostete.homes.command;

import de.dereingerostete.homes.chat.Chat;
import de.dereingerostete.homes.command.util.CommandMessages;
import de.dereingerostete.homes.command.util.SimpleCommand;
import de.dereingerostete.homes.entity.Homes;
import de.dereingerostete.homes.entity.HomesCache;
import de.dereingerostete.homes.util.Lang;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SetHomeCommand extends SimpleCommand {

    public SetHomeCommand() {
        super("sethome", true);
        setUsage('/' + getName());
        setDescription(Lang.getMessage("setHomeDescription", false));
        setPermission("basichomes.sethome");
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
            Chat.toPlayer(player, "setHomeUsage", true);
            return;
        }

        String key = args[0];
        if (key.length() > 20) key = key.substring(0, 20);

        boolean keyAlreadySet = homes.isKeySet(key);
        if (homes.hasMaxHomes() && !keyAlreadySet) {
            Chat.toPlayer(player, "userHasMaxHomes", true);
            return;
        }

        Location location = player.getLocation();
        Block block = location.getBlock();
        Block groundBlock = block.getRelative(BlockFace.DOWN);

        if (groundBlock.isEmpty()) {
            Chat.toPlayer(player, "setHomeMustBeGround", true);
            return;
        }

        if (keyAlreadySet)
            key = homes.getRealKey(key);

        boolean success = homes.setHome(key, location);
        if (success) Chat.toPlayer(player, "setHomeSuccess", true);
        else Chat.toPlayer(player, "unexpectedError", true);
    }

}
