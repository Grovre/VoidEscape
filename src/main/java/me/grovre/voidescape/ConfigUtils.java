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

    public boolean useBlindingEffect() {
        Boolean useBlindingEffect = config.getBoolean("useBlindingEffect");
        if(useBlindingEffect == null) {
            System.out.println("Config setup improperly. Does useBlindingEffect have a true/false value?");
            VoidEscape.emergencyUnload();
        }

        return useBlindingEffect;
    }

    public int getBlindingEffectDuration() {
        Integer blindingEffectDuration = config.getInt("blindingEffectDuration");
        if(blindingEffectDuration == null) {
            System.out.println("Config setup improperly. Does blindingEffectDuration contain an integer with no decimals?");
            VoidEscape.emergencyUnload();
        }

        return blindingEffectDuration;
    }

    public boolean willTeleportToRandomPos() {
        Boolean teleportToRandomPos = config.getBoolean("teleportToRandomPos");
        if(teleportToRandomPos == null) {
            System.out.println("Config setup improperly. Does teleportToRandomPos have a true/false value?");
            VoidEscape.emergencyUnload();
        }

        return teleportToRandomPos;
    }

    public int getRandomTeleportBounds() {
        Integer randomTeleportBounds = config.getInt("randomTeleportBoundsFromCenter");
        if(randomTeleportBounds == null) {
            System.out.println("Config setup improperly. Does randomTeleportBoundsFromCenter have a value?");
            VoidEscape.emergencyUnload();
        }

        return randomTeleportBounds;
    }

    public boolean getCloseCall() {
        Boolean closeCall = config.getBoolean("closeCall");
        if(closeCall == null) {
            System.out.println("Config setup improperly. Does teleportToRandomPos have a true/false value?");
            VoidEscape.emergencyUnload();
        }

        return closeCall;
    }
}
