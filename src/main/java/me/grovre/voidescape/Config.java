package me.grovre.voidescape;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static final Location safeLocation = loadSafeLocation();
    private static final FileConfiguration config = VoidEscape.plugin.getConfig();

    private static Location loadSafeLocation() {
        assert config != null;
        String safeWorldName = config.getString("safeWorld");
        if(safeWorldName == null || safeWorldName.length() == 0) {
            System.out.println("Config setup improperly. safeWorld has no value.");
            VoidEscape.emergencyUnload();
            return null;
        }
        World safeWorld = Bukkit.getWorld(safeWorldName);
        if(safeWorld == null) {
            System.out.println("Config setup improperly. safeWorld is an invalid world name.");
            VoidEscape.emergencyUnload();
        }
        int safeX = config.getInt("safeX");
        int safeY = config.getInt("safeY");
        int safeZ = config.getInt("safeZ");

        return new Location(safeWorld, safeX, safeY, safeZ);
    }
}
