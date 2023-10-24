package org.togudv.itemcustomeffects.models;

import org.bukkit.inventory.ItemStack;

public class PlayerItems {
    private ItemStack previousItem;


    public ItemStack getPreviousItem() {
        return previousItem;
    }

    public void setPreviousItem(ItemStack previousItem) {
        this.previousItem = previousItem;
    }

}
