package me.grovre.voidescape.listeners;

import me.grovre.voidescape.VoidEscape;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.function.*;

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

        // Extra distance to look cool and also to cancel out the
        // fall damage removal
        player.setFallDistance(VoidEscape.bufferHeight);
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
        World world = VoidEscape.safeLocation.getWorld();
        if (world == null)
            throw new RuntimeException("Attempted to find a random safe location but the world is null.");
        Random r = new Random();
        IntSupplier randomPosSupplier = () -> getRandomPos(r, VoidEscape.safeLocation.getWorld(), distanceFromWorldCenter);
        Block highestYBlock;
        int xToTeleportTo;
        int zToTeleportTo;

        int i = 0;
        do {
            xToTeleportTo = randomPosSupplier.getAsInt();
            zToTeleportTo = randomPosSupplier.getAsInt();

            highestYBlock = VoidEscape.safeLocation
                    .getWorld()
                    .getHighestBlockAt(xToTeleportTo, zToTeleportTo);

            if (highestYBlock == null)
                continue;

            if (highestYBlock.isEmpty())
                continue;

            Material blockType = highestYBlock.getType();
            if (blockType == Material.LAVA)
                continue;

            if (!VoidEscape.allowTpIntoWater && blockType == Material.WATER)
                continue;

        } while (i++ < 500_000);

        if (i >= 500_000)
            throw new RuntimeException(
                    "Safe teleport position failsafe activated. " +
                    "Couldn't find a safe teleport location 500,000 times.");

        Location randomLocationWithinBorders = new Location(
                VoidEscape.safeLocation.getWorld(),
                xToTeleportTo,
                highestYBlock.getY() + VoidEscape.bufferHeight * 125, // 4 * 125 = 500 blocks higher than highest block
                zToTeleportTo
        );

        return randomLocationWithinBorders;
    }

    private int getRandomPos(Random random, World world, int distanceFromWorldCenter) {
        return random.nextInt(distanceFromWorldCenter * 2) - distanceFromWorldCenter;
    }
}
