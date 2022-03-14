package me.grovre.voidescape.listeners;

import me.grovre.voidescape.VoidEscape;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FallListener implements Listener {

    @EventHandler
    public void OnPlayerFallDamage(EntityDamageEvent e) {
        if(e.getEntityType() != EntityType.PLAYER) return;
        if(e.getCause() != EntityDamageEvent.DamageCause.FALL) return;

        if(VoidEscape.playersBeingSaved.remove((Player) e.getEntity())) {
            e.setCancelled(true);
        }
    }

}
