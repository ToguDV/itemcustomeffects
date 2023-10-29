package org.togudv.itemcustomeffects.events;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.togudv.itemcustomeffects.utils.EssenceUtils;
import org.togudv.itemcustomeffects.utils.NBTEditor;

public class EssenceApplicationListener implements Listener {
    @EventHandler
    public void onClickEvent(InventoryClickEvent event) {
        HumanEntity player = event.getWhoClicked();
        player.sendMessage("Inventario click");
        if (event.getCurrentItem() == null || event.getCursor() == null)
            return;
        player.sendMessage("tiene algo en el cursor y en el click");
        ItemStack clickedItem = event.getCurrentItem();
        ItemStack cursor = event.getCursor();

        if(!EssenceUtils.isEssence(cursor) || EssenceUtils.isEssence(clickedItem)) {
            player.sendMessage("No es esencia o la esencia esta en el click");
            return;
        }
        ItemStack result = clickedItem;
        int i = 0;
        int j = 0;

        if (NBTEditor.contains(result, "itemCustomEffects", "item", "hitEffects", 0 + "")) {
            player.sendMessage("entramos al bucle");
            while(true) {
                if (NBTEditor.contains(cursor, "itemCustomEffects", "essence", "hitEffects", i + "")) {
                    String essencePotionName = NBTEditor.getString(cursor, "itemCustomEffects", "essence", "hitEffects", i+"");
                    PotionEffectType potionEssence = PotionEffectType.getByName(essencePotionName);
                    while(true) {
                        if(NBTEditor.contains(clickedItem, "itemCustomEffects", "item", "hitEffects", j + "")) {
                            String itemPotionName = NBTEditor.getString(result, "itemCustomEffects", "item", "hitEffects", j+"");
                            PotionEffectType potionItem = PotionEffectType.getByName(itemPotionName);
                            if(potionEssence == potionItem) {

                                break;
                            }

                            result = NBTEditor.set(result, essencePotionName, "itemCustomEffects", "item", "hitEffects", (j+1)+"");
                            player.sendMessage("creado result a item con efectos previos");
                        }

                        else {
                            break;
                        }
                        j++;
                    }

                }

                else {
                    break;
                }
                i++;
            }
        }

        else {
            //FALTA RECORRER TODOS LOS EFECTOS DEL ESENCE
            int index = 0;
            String essencePotionName = NBTEditor.getString(cursor, "itemCustomEffects", "essence", "hitEffects", index+"");
            result = NBTEditor.set(result, essencePotionName, "itemCustomEffects", "item", "hitEffects", index+"");
            player.sendMessage("creado result a item sin efectos previos: essencePotionName="+essencePotionName+", index="+index);
        }

        if (result == null || cursor == null || clickedItem == null) {
            player.sendMessage("result null o cursor");
            return;
        }

        event.setCancelled(true);
        event.setCurrentItem(result);

        int cursorModifier = event.getCursor().getAmount();
        if (cursor.getAmount() - cursorModifier == 0) {
            event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
            player.sendMessage("Aplicado a no stack");
        } else {
            cursor.setAmount(cursor.getAmount() + cursorModifier);
            player.sendMessage("Aplicado a stack");
            event.getWhoClicked().setItemOnCursor(cursor);
        }


    }
}
