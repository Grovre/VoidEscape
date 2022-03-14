package me.grovre.voidescape;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class VoidEscape extends JavaPlugin {

    public static VoidEscape plugin;
    public static Location safeLocation;
    public static Set<Player> playersBeingSaved;

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
        playersBeingSaved = new HashSet<>();
        if(playersBeingSaved instanceof HashSet) System.out.println("Yes! It's a HashSet!!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
