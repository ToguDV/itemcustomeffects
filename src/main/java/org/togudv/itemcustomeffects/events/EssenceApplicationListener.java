package org.togudv.itemcustomeffects.events;

import org.bukkit.GameMode;
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
        if (event.getCurrentItem().getAmount() == 0 || event.getCursor().getAmount() == 0) {
            HumanEntity player = event.getWhoClicked();
            player.sendMessage("Inventario vacio");
            return;
        }

        HumanEntity player = event.getWhoClicked();
        player.sendMessage("Inventario click");

        ItemStack clickedItem = event.getCurrentItem();
        ItemStack cursor = event.getCursor();
        if (!event.getWhoClicked().getGameMode().equals(GameMode.SURVIVAL)) {
            player.sendMessage("Quitate el creativo, putito");
            return;
        }

        player.sendMessage("tiene algo en el cursor y en el click");
        player.sendMessage("CURSO:"+cursor.toString());
        player.sendMessage("ITEMCLICK:"+clickedItem.toString());
        if (!EssenceUtils.isEssence(cursor) || EssenceUtils.isEssence(clickedItem)) {
            player.sendMessage("No es esencia o la esencia esta en el click");
            return;
        }
        ItemStack result = clickedItem;
        int i = 0;
        int j = 0;
        boolean isSamePotion = false;
        boolean bucle = true;
        boolean bucle2 = true;
        /* if (NBTEditor.contains(result, "itemCustomEffects", "item", "hitEffects", 0 + "")) { */
        String triggerType = "";
        if(NBTEditor.contains(cursor, "itemCustomEffects", "essence", "hiteffects", 0 + "")) {
            triggerType = "hiteffects";
        }

        if(NBTEditor.contains(cursor, "itemCustomEffects", "essence", "hurteffects", 0 + "")) {
            triggerType = "hurteffects";
        }

        if(NBTEditor.contains(cursor, "itemCustomEffects", "essence", "holdeffects", 0 + "")) {
            triggerType = "holdeffects";
        }

        player.sendMessage("entramos al bucle");
        do {
            isSamePotion = false;
            if (NBTEditor.contains(cursor, "itemCustomEffects", "essence", triggerType, i + "")) {
                String essencePotionName = NBTEditor.getString(cursor, "itemCustomEffects", "essence", triggerType, i + "");
                PotionEffectType potionEssence = PotionEffectType.getByName(essencePotionName);
                bucle2 = true;
                j = 0;

                if (NBTEditor.contains(clickedItem, "itemCustomEffects", "item", triggerType, 0 + "")) {
                    do {
                        if (NBTEditor.contains(clickedItem, "itemCustomEffects", "item", triggerType, j + "")) {
                            String itemPotionName = NBTEditor.getString(result, "itemCustomEffects", "item", triggerType, j + "");
                            PotionEffectType potionItem = PotionEffectType.getByName(itemPotionName);
                            if (potionEssence == potionItem) {
                                isSamePotion = true;
                                bucle2 = false;
                            }
                            j++;


                        } else {
                            bucle2 = false;
                        }
                    } while(bucle2);

                    if (!isSamePotion) {
                        int count = countItemEffects(clickedItem,triggerType);
                        result = NBTEditor.set(result, essencePotionName, "itemCustomEffects", "item", triggerType, (count) + "");
                        player.sendMessage("creado result a item con efectos previos");
                    } else {
                        player.sendMessage("este item ya contiene este efecto, materiales desperdiciados!");
                    }
                }

                else {
                    result = NBTEditor.set(result, essencePotionName, "itemCustomEffects", "item", triggerType, 0 + "");
                    player.sendMessage("creado result a item sin efectos previos");
                }

            } else {
                bucle = false;
            }
            i++;


        } while (bucle);

        if (event.getCurrentItem().getAmount() == 0 || event.getCursor().getAmount() == 0) {
            player.sendMessage("result null o cursor");
            return;
        }

        event.setCancelled(true);
        event.setCurrentItem(result);

        int cursorModifier = result.getAmount();
        if (cursor.getAmount() - cursorModifier == 0) {
            event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
            player.sendMessage("Aplicado a no stack");
        } else {
            cursor.setAmount(cursor.getAmount() - 1);
            player.sendMessage("Aplicado a stack");
            event.getWhoClicked().setItemOnCursor(cursor);
        }


    }

    public int countItemEffects(ItemStack clickedItem, String type) {
        int result = 0;
        boolean bucle = true;
        if (NBTEditor.contains(clickedItem, "itemCustomEffects", "item", type, 0 + "")) {
            do {
                if (NBTEditor.contains(clickedItem, "itemCustomEffects", "item", type, result + "")) {
                    result++;
                } else {
                    bucle = false;
                }
            } while (bucle);
        }

        return result;

    }
}
