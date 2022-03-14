package me.grovre.voidescape;

import org.bukkit.plugin.java.JavaPlugin;

public final class VoidEscape extends JavaPlugin {

    public static VoidEscape plugin;

    public static VoidEscape getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
