package org.togudv.itemcustomeffects.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.togudv.itemcustomeffects.models.PlayerItems;

public class HitEvents implements Listener {
    private PlayerItems playerItems;

    public HitEvents(PlayerItems playerItems) {
        this.playerItems = playerItems;
    }

    @EventHandler
    public void OnPlayerHit(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            event.getDamager().sendMessage("Te gusta el pilin, eres un jugador y le pegaste a " + event.getEntity().getName() + "! :D");
            Entity damager = event.getDamager();
            Entity victim = event.getEntity();
            //PotionEffectType veneno = PotionEffectType.getByName("POISON");
            if(victim instanceof Mob ) {
                Mob mob = (Mob) victim;
                //mob.addPotionEffect(new PotionEffect(veneno, 300, 2));
            }
        }
    }
}
