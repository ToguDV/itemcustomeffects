package org.togudv.itemcustomeffects.utils;

import org.bukkit.inventory.ItemStack;

public class EssenceUtils {
    public static boolean isEssence(ItemStack item) {
        if ( NBTEditor.contains( item, "itemCustomEffects", "essence") ) {
            return true;
        }

        return false;
    }
}
