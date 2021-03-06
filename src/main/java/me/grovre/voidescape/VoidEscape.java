package me.grovre.voidescape;

import me.grovre.voidescape.listeners.FallListener;
import me.grovre.voidescape.listeners.VoidListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class VoidEscape extends JavaPlugin {

    // the stuff
    public static VoidEscape plugin;
    public static Set<Player> playersBeingSaved;

    // config options
    public static Location safeLocation;
    public static boolean useBlindingEffect;
    public static int blindingEffectDuration;
    public static boolean teleportToRandomPos;
    public static int randomTeleportBounds;
    public static boolean closeCall;

    public static VoidEscape getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        saveDefaultConfig();

        // config assignments
        FileConfiguration config = getConfig();
        safeLocation = getSafeLocation(config);
        useBlindingEffect = config.getBoolean("useBlindingEffect");
        blindingEffectDuration = config.getInt("blindingEffectDuration");
        teleportToRandomPos = config.getBoolean("teleportToRandomPos");
        randomTeleportBounds = config.getInt("randomTeleportBoundsFromCenter");
        closeCall = config.getBoolean("closeCall");

        // It's a hash set!!!
        playersBeingSaved = new HashSet<>();
        if(playersBeingSaved instanceof HashSet) System.out.println("Yes! It's a HashSet!!");

        Bukkit.getPluginManager().registerEvents(new FallListener(), this);
        Bukkit.getPluginManager().registerEvents(new VoidListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Location getSafeLocation(FileConfiguration config) {
        String safeWorldName = config.getString("safeWorld");
        if(safeWorldName == null || safeWorldName.length() == 0) {
            System.out.println("Config setup improperly. safeWorld has no value.");
            Bukkit.getPluginManager().disablePlugin(VoidEscape.plugin);
            return null;
        }
        World safeWorld = Bukkit.getWorld(safeWorldName);
        if(safeWorld == null) {
            System.out.println("Config setup improperly. safeWorld is an invalid world name.");
            Bukkit.getPluginManager().disablePlugin(VoidEscape.plugin);
        }
        int safeX = config.getInt("safeX");
        int safeY = config.getInt("safeY");
        int safeZ = config.getInt("safeZ");

        return new Location(safeWorld, safeX, safeY, safeZ);
    }
}
