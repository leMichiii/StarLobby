package de.michaelmawick.starlobby;

import de.michaelmawick.starlobby.commands.StarLobbyCommand;
import de.michaelmawick.starlobby.listener.ConnectionListener;
import de.michaelmawick.starlobby.listener.ProtectionListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class StarLobby extends JavaPlugin {

    public static StarLobby instance;
    private final FileConfiguration config = getConfig();

    @Override
    public void onLoad() {
        saveDefaultConfig();
        String msg = replacePlaceholders(config.getString("plugin-load-message"));
        getServer().getConsoleSender().sendMessage(msg);
    }


    @Override
    public void onEnable() {
        instance = this;
        String msg = replacePlaceholders(config.getString("plugin-enable-message"));
        getServer().getConsoleSender().sendMessage(msg);

        getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
        getServer().getPluginManager().registerEvents(new ProtectionListener(), this);

        getCommand("starlobby").setExecutor(new StarLobbyCommand());
        getCommand("starlobby").setTabCompleter(new StarLobbyCommand());

        
    }

    @Override
    public void onDisable() {
        String msg = replacePlaceholders(config.getString("plugin-disable-message"));
        getServer().getConsoleSender().sendMessage(msg);
    }

    public static StarLobby getInstance() {
        return instance;
    }

    public String replacePlaceholders(String input) {
        return input.replace("%starlobby_prefix%", config.getString("prefix"));
    }

}
