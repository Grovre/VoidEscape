package me.grovre.voidescape;

import me.grovre.voidescape.listeners.FallListener;
import me.grovre.voidescape.listeners.VoidListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

    public static void emergencyUnload() {
        System.err.println("Disabling VoidEscape...");
        Bukkit.getPluginManager().disablePlugin(plugin);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // config assignments
        this.saveDefaultConfig();
        ConfigUtils config = new ConfigUtils();
        safeLocation = config.getSafeLocation();
        useBlindingEffect = config.useBlindingEffect();
        blindingEffectDuration = config.getBlindingEffectDuration();
        teleportToRandomPos = config.willTeleportToRandomPos();
        randomTeleportBounds = config.getRandomTeleportBounds();
        closeCall = config.getCloseCall();

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
}
