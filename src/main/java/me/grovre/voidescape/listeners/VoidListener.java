package me.grovre.voidescape.listeners;

import me.grovre.voidescape.VoidEscape;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class VoidListener implements Listener {

    @EventHandler
    public void OnPlayerDamageByVoidEvent(EntityDamageEvent e) {
        if(e.getEntityType() != EntityType.PLAYER) return;
        if(e.getCause() != EntityDamageEvent.DamageCause.VOID) return;

        e.setCancelled(true);

        Player player = (Player) e.getEntity();
        double yVelocity = player.getVelocity().getY();

        saveFromVoid(player, yVelocity, VoidEscape.safeLocation);
    }

    public void saveFromVoid(Player player, double yVelocity, Location safeLocation) {
        applyBlindEffect(player);
        player.teleport(safeLocation);
        player.setVelocity(new Vector(0, yVelocity, 0));
        VoidEscape.playersBeingSaved.add(player);
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
}
