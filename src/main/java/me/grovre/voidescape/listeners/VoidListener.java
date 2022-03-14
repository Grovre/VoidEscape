package me.grovre.voidescape.listeners;

import me.grovre.voidescape.VoidEscape;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class VoidListener implements Listener {

    @EventHandler
    public void OnPlayerDamageByVoidEvent(EntityDamageEvent e) {
        if(e.getEntityType() != EntityType.PLAYER) return;
        if(e.getCause() != EntityDamageEvent.DamageCause.VOID) return;

        e.setCancelled(true);

        Player player = (Player) e.getEntity();
        double yVelocity = player.getVelocity().getY();
        if(yVelocity <= 0) return;

        saveFromVoid(player, yVelocity, VoidEscape.safeLocation);
    }

    public void saveFromVoid(Player player, double yVelocity, Location safeLocation) {
        player.teleport(safeLocation);
        player.setVelocity(new Vector(0, yVelocity, 0));
        VoidEscape.playersBeingSaved.add(player);
    }
}
