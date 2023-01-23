/*
 * Copyright (c) 2023 × DerEingerostete
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package de.dereingerostete.homes.entity;

import de.dereingerostete.homes.chat.Chat;
import de.dereingerostete.homes.chat.Logging;
import de.dereingerostete.homes.sql.SQLDatabase;
import de.dereingerostete.homes.util.Config;
import de.dereingerostete.homes.util.JSONUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Homes {
    public static final String GUI_TITLE = "§8Homes";
    private static SQLDatabase database;
    private static int maxHomes;

    private final HashMap<String, Location> locations;
    private final UUID uuid;

    private JSONObject object;
    private boolean errorOccurred;

    public Homes(@NotNull UUID uuid) {
        this.uuid = uuid;
        locations = new HashMap<>();
        errorOccurred = false;

        try {
            PreparedStatement statement = database.prepare("select json from Homes where uuid=?;");
            statement.setString(1, uuid.toString());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String json = resultSet.getString("json");
                object = new JSONObject(json);

                Set<String> keySet = object.keySet();
                keySet.forEach(key -> {
                    JSONObject locationObject = object.optJSONObject(key);
                    if (locationObject == null) return;

                    Location location = JSONUtils.getLocation(locationObject);
                    locations.put(key, location);
                });
            } else createUser();
            resultSet.close();
            statement.close();
        } catch (SQLException exception) {
            Logging.warning("Failed to load homes of " + uuid, exception);
            errorOccurred = true;
        }

        if (!errorOccurred) {
            int amount = locations.size();
            if (amount <= maxHomes) return;
            getKeys().forEach(this::removeHome);

            Player player = Bukkit.getPlayer(uuid);
            if (player != null) Chat.toPlayer(player,
                    "§cYou own more homes than the maximum possible. " +
                    "All of your homes have been reset!");
        }
    }

    public boolean setHome(@NotNull String key, @NotNull Location location) {
        JSONObject locationObject = new JSONObject();
        JSONUtils.setLocation(locationObject, location);

        if (object == null) {
            errorOccurred = true;
            return false;
        }

        object.remove(key);
        object.put(key, locationObject);

        try {
            updateJson();
        } catch (SQLException exception) {
            Logging.warning("Failed to set home (" + key + ") for " + uuid);
            return false;
        }

        locations.put(key, location);
        return true;
    }

    public boolean removeHome(@NotNull String key) {
        object.remove(key);

        try {
            updateJson();
        } catch (SQLException exception) {
            Logging.warning("Failed to remove home (" + key + ") for " + uuid);
            return false;
        }

        locations.remove(key);
        return true;
    }

    private void updateJson() throws SQLException  {
        PreparedStatement statement = database.prepare("update Homes set json=? where uuid=?;");
        statement.setString(1, object.toString());
        statement.setString(2, uuid.toString());
        statement.executeUpdate();
        statement.close();
    }

    @NotNull
    public Set<String> getKeys() {
        return locations.keySet();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isKeySet(String key) {
        boolean contains = locations.containsKey(key);
        if (contains) return true;


        for (String home : getKeys()) {
            if (home.equalsIgnoreCase(key))
                return true;
        }

        return false;
    }

    public String getRealKey(String key) {
        for (String home : getKeys()) {
            if (home.equalsIgnoreCase(key))
                return home;
        }
        return null;
    }

    public Location getHome(String key) {
        Location location =  locations.get(key);
        if (location != null) return location;

        for (String home : getKeys()) {
            if (home.equalsIgnoreCase(key))
                return locations.get(home);
        }

        return null;
    }

    public boolean hasMaxHomes() {
        return locations.size() >= maxHomes;
    }

    public boolean hadError() {
        return errorOccurred;
    }

    private void createUser() throws SQLException {
        PreparedStatement statement = database.prepare("insert into Homes (uuid, json) values (?, ?);");
        statement.setString(1, uuid.toString());
        statement.setString(2, "{}");
        statement.executeUpdate();
        statement.close();
        object = new JSONObject();
    }

    public static void setDatabase(@NotNull SQLDatabase sqlDatabase, @NotNull Config config) throws SQLException {
        database = sqlDatabase;
        database.createTable("Homes", "`uuid` VARCHAR(64), `json` TEXT");
        maxHomes = config.getInt("maxHomes", 3);
    }

    public static void sendErrorMessage(@NotNull Player player) {
        Chat.toPlayer(player, "§cAn unexpected error occurred. " +
                "Your saved homes could not be loaded. " +
                "Please try to re-enter the server or contact the team!");
    }

}
