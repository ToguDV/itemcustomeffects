package org.togudv.itemcustomeffects.events;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.togudv.itemcustomeffects.utils.EssenceUtils;
import org.togudv.itemcustomeffects.utils.NBTEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EssenceApplicationListener implements Listener {
    @EventHandler
    public void onClickEvent(InventoryClickEvent event) {
        if(event.getCurrentItem() == null || event.getCursor() == null) {
            return;
        }
        if (event.getCurrentItem().getAmount() == 0 || event.getCursor().getAmount() == 0) {
            return;
        }

        HumanEntity player = event.getWhoClicked();

        ItemStack clickedItem = event.getCurrentItem();
        ItemStack cursor = event.getCursor();
        if (!event.getWhoClicked().getGameMode().equals(GameMode.SURVIVAL)) {
            return;
        }
        if (!EssenceUtils.isEssence(cursor) || EssenceUtils.isEssence(clickedItem)) {
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

        do {
            isSamePotion = false;
            if (NBTEditor.contains(cursor, "itemCustomEffects", "essence", triggerType, i + "")) {
                String essencePotionValues = NBTEditor.getString(cursor, "itemCustomEffects", "essence", triggerType, i + "");
                String essencePotionName = EssenceUtils.getPotionValues(essencePotionValues)[0];
                PotionEffectType potionEssence = PotionEffectType.getByName(essencePotionName);
                bucle2 = true;
                j = 0;

                if (NBTEditor.contains(clickedItem, "itemCustomEffects", "item", triggerType, 0 + "")) {
                    do {
                        if (NBTEditor.contains(clickedItem, "itemCustomEffects", "item", triggerType, j + "")) {
                            String itemPotionValues = NBTEditor.getString(result, "itemCustomEffects", "item", triggerType, j + "");
                            String itemPotionName = EssenceUtils.getPotionValues(itemPotionValues)[0];
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
                        result = NBTEditor.set(result, essencePotionValues, "itemCustomEffects", "item", triggerType, (count) + "");
                        result = addItemLore(result, cursor);
                    } else {
                        player.sendMessage(ChatColor.RED+"Este item ya contiene este efecto o uno incompatible, materiales desperdiciados!");
                    }
                }

                else {
                    result = NBTEditor.set(result, essencePotionValues, "itemCustomEffects", "item", triggerType, 0 + "");
                    result = addItemLore(result, cursor);
                }

            } else {
                bucle = false;
            }
            i++;


        } while (bucle);

        if (event.getCurrentItem().getAmount() == 0 || event.getCursor().getAmount() == 0) {
            return;
        }

        event.setCancelled(true);
        event.setCurrentItem(result);

        int cursorModifier = result.getAmount();
        if (cursor.getAmount() - cursorModifier == 0) {
            event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
        } else {
            cursor.setAmount(cursor.getAmount() - 1);
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

    public ItemStack addItemLore(ItemStack item, ItemStack cursor) {
        ItemMeta meta = item.getItemMeta();
        if(meta == null) return item;
        List<String> lore;
        if(meta.hasLore()) {
            lore = meta.getLore();
        }
        else {
            lore = new ArrayList<>();
        }
        lore.add(cursor.getItemMeta().getDisplayName());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
