package me.grovre.voidescape;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    private final FileConfiguration config = VoidEscape.plugin.getConfig();

    public FileConfiguration getConfig() {
        return config;
    }

    public Location getSafeLocation() {
        String safeWorldName = config.getString("safeWorld");
        if(safeWorldName == null || safeWorldName.length() == 0) {
            System.out.println("ConfigUtils setup improperly. safeWorld has no value.");
            VoidEscape.emergencyUnload();
            return null;
        }
        World safeWorld = Bukkit.getWorld(safeWorldName);
        if(safeWorld == null) {
            System.out.println("ConfigUtils setup improperly. safeWorld is an invalid world name.");
            VoidEscape.emergencyUnload();
        }
        int safeX = config.getInt("safeX");
        int safeY = config.getInt("safeY");
        int safeZ = config.getInt("safeZ");

        return new Location(safeWorld, safeX, safeY, safeZ);
    }
}
