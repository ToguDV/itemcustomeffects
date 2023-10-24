package org.togudv.itemcustomeffects.events;

import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeWrapper;
import org.togudv.itemcustomeffects.models.PlayerItems;

public class PVPEvents implements Listener {
    private PlayerItems player;

    public PVPEvents(PlayerItems player) {
        this.player = player;
    }

    @EventHandler
    public void OnPlayerHit(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            event.getDamager().sendMessage("Te gusta el pilin, eres un jugador y le pegaste a " + event.getEntity().getName() + "! :D");
            PotionEffectType veneno = PotionEffectType.getByName("POISON");
            Mob mob = (Mob) event.getEntity();
            mob.addPotionEffect(new PotionEffect(veneno, 300, 2));
        }
    }

}
