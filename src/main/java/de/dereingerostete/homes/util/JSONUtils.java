package de.dereingerostete.homes.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class JSONUtils {

    @NotNull
    public static <T> List<T> getList(JSONObject jsonObject,
                                      String key, Class<T> objectClass) {
        List<T> list = Lists.newArrayList();
        JSONArray array = jsonObject.optJSONArray(key);
        if (array == null) return list;

        array.forEach(object -> {
            T casted = objectClass.cast(object);
            list.add(casted);
        });

        return list;
    }

    public static void setLocation(@NotNull JSONObject object, @NotNull Location location) {
        Validate.notNull(object, "JSONObject cannot be null");
        Validate.notNull(location, "Location cannot be null");

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        float yaw = location.getYaw();
        float pitch = location.getPitch();

        World world = Objects.requireNonNull(location.getWorld());
        String name = world.getName();

        object.put("x", x);
        object.put("y", y);
        object.put("z", z);
        object.put("yaw", yaw);
        object.put("pitch", pitch);
        object.put("world", name);
    }

    @NotNull
    public static Location getLocation(@NotNull JSONObject object) {
        Validate.notNull(object, "JSONObject cannot be null");

        double x = object.optDouble("x", 0);
        double y = object.optDouble("y", 0);
        double z = object.optDouble("z", 0);

        boolean hasYawAndPitch = object.has("yaw")
                && object.has("pitch");

        float yaw = 0;
        float pitch = 0;
        if (hasYawAndPitch) {
            yaw = object.optFloat("yaw");
            pitch = object.optFloat("pitch");
        }

        String worldName = object.optString("world", null);
        Validate.notNull(worldName, "World cannot be null");

        World world = Bukkit.getWorld(worldName);
        if (hasYawAndPitch) return new Location(world, x, y, z, yaw, pitch);
        else return new Location(world, x, y, z);
    }

}
