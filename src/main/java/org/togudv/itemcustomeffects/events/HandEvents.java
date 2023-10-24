package org.togudv.itemcustomeffects.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.togudv.itemcustomeffects.models.PlayerItems;

public class HandEvents implements Listener {

    private PlayerItems playerItems;

    public HandEvents(PlayerItems playerItems) {
        this.playerItems = playerItems;
    }

    @EventHandler
    public void onPlayerChangeMainHandSlot(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        updatePlayerItemInfo(player);
    }

    public void updatePlayerItemInfo(Player player) {

        ItemStack currentItem = player.getInventory().getItemInMainHand();
        if (playerItems.getPreviousItem() != null && !currentItem.equals(playerItems.getPreviousItem())) {
            player.sendMessage("Has cambiado el ítem en tu mano.");
        }

        playerItems.setPreviousItem(currentItem);
    }
}
