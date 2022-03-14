package me.grovre.voidescape.listeners;

import me.grovre.voidescape.VoidEscape;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;

public class VoidListener implements Listener {

    @EventHandler
    public void OnPlayerDamageByVoidEvent(EntityDamageEvent e) {
        if(e.getEntityType() != EntityType.PLAYER) return;
        if(e.getCause() != EntityDamageEvent.DamageCause.VOID) return;

        Player player = (Player) e.getEntity();
        double yVelocity = player.getVelocity().getY();

        if(VoidEscape.closeCall) {
            if(player.getHealth() - e.getFinalDamage() > 0) {
                return;
            }
        }

        e.setCancelled(true);
        saveFromVoid(player, yVelocity, findTeleportLocation());
    }

    public void saveFromVoid(Player player, double yVelocity, Location safeLocation) {
        applyBlindEffect(player);
        player.teleport(safeLocation);
        player.setVelocity(new Vector(0, yVelocity, 0));
        VoidEscape.playersBeingSaved.add(player);
        Bukkit.getScheduler().runTaskLater(
                VoidEscape.getPlugin(),
                () -> VoidEscape.playersBeingSaved.remove(player),
                300
        );
        player.setFallDistance(4);
    }

    public void applyBlindEffect(Player player) {
        if(!VoidEscape.useBlindingEffect) return;
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,
                VoidEscape.blindingEffectDuration,
                1,
                false,
                false
        ));
    }

    public Location findTeleportLocation() {
        if(!VoidEscape.teleportToRandomPos) {
            return VoidEscape.safeLocation;
        }

        int distanceFromWorldCenter = VoidEscape.randomTeleportBounds;
        Random r = new Random();
        int xToTeleportTo = r.nextInt(distanceFromWorldCenter * 2) - distanceFromWorldCenter;
        int zToTeleportTo = r.nextInt(distanceFromWorldCenter * 2) - distanceFromWorldCenter;
        Location randomLocationWithinBorders = new Location(
                VoidEscape.safeLocation.getWorld(),
                xToTeleportTo,
                500,
                zToTeleportTo
        );
        System.out.println("New safe location");

        return randomLocationWithinBorders;
    }
}
