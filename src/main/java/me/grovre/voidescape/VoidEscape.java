package me.grovre.voidescape;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class VoidEscape extends JavaPlugin {

    public static VoidEscape plugin;
    public static Location safeLocation;

    public static VoidEscape getPlugin() {
        return plugin;
    }

    public static void emergencyUnload() {
        System.err.println("Disabling VoidEscape...");
        Bukkit.getPluginManager().disablePlugin(plugin);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        ConfigUtils config = new ConfigUtils();
        safeLocation = config.getSafeLocation();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
