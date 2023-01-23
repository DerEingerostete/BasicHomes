package de.dereingerostete.homes;

import de.dereingerostete.homes.chat.Logging;
import de.dereingerostete.homes.command.HomeCommand;
import de.dereingerostete.homes.command.RemoveHomeCommand;
import de.dereingerostete.homes.command.SetHomeCommand;
import de.dereingerostete.homes.command.util.SimpleCommand;
import de.dereingerostete.homes.entity.Homes;
import de.dereingerostete.homes.listener.InventoryListener;
import de.dereingerostete.homes.sql.SQLDatabase;
import de.dereingerostete.homes.util.Config;
import de.dereingerostete.homes.util.Lang;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class BasicHomes extends JavaPlugin {
    private static @Getter Plugin instance;
    private static @Getter Config defaultConfig;

    @Override
    public void onEnable() {
        instance = this;

        try {
            File configFile = new File(getDataFolder(), "config.json");
            defaultConfig = new Config(configFile);
            Lang.loadLanguage();
        } catch (IOException exception) {
            Logging.severe("Failed to load config");
            throw new RuntimeException(exception);
        }

        try {
            String fileName = defaultConfig.getString("databaseFile", "database.db");
            SQLDatabase database = new SQLDatabase(fileName);
            database.connect();
            Homes.setDatabase(database, defaultConfig);
        } catch (SQLException exception) {
            Logging.severe("Failed to load coins database");
            throw new RuntimeException(exception);
        }

        registerCommand(new HomeCommand());
        registerCommand(new SetHomeCommand());
        registerCommand(new RemoveHomeCommand());
        registerListeners();
        Logging.info("Enabled " + getName());
    }

    private void registerListeners() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new InventoryListener(), this);
    }

    private void registerCommand(@NotNull SimpleCommand command) {
        command.register("basichomes");
    }

    @Override
    public void onDisable() {
        Logging.info("Disabled " + getName());
    }


}