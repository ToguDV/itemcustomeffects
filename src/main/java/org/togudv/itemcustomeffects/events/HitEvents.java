package org.togudv.itemcustomeffects.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.togudv.itemcustomeffects.models.PlayerItems;
import org.togudv.itemcustomeffects.utils.NBTEditor;

public class HitEvents implements Listener {
    private PlayerItems playerItems;

    public HitEvents(PlayerItems playerItems) {
        this.playerItems = playerItems;
    }

    @EventHandler
    public void OnPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();
        Player player = (Player) damager;
        ItemStack weapon = player.getInventory().getItemInMainHand();
        boolean bucle = true;
        int index = 0;
        if (NBTEditor.contains(weapon, "itemCustomEffects", "item", "hitEffects", 0 + "")) {
            do {
                if (NBTEditor.contains(weapon, "itemCustomEffects", "item", "hitEffects", index + "")) {
                    String essencePotionName = NBTEditor.getString(weapon, "itemCustomEffects", "item", "hitEffects", index + "");
                    if (victim instanceof Mob) {
                        Mob mob = (Mob) victim;
                        player.sendMessage("Potion name" + essencePotionName);
                        PotionEffectType veneno = PotionEffectType.getByName(essencePotionName);
                        //PotionEffectType veneno = PotionEffectType.getByName("poison");
                        mob.addPotionEffect(new PotionEffect(veneno, 300, 2));
                    }
                    index++;
                } else {
                    bucle = false;
                }
            } while (bucle);
        }
    }

}
