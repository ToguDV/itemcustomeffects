package org.togudv.itemcustomeffects.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.togudv.itemcustomeffects.utils.NBTEditor;

public class EssenceSet implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length >= 1) {
                String effectArg = args[0];
                PotionEffectType potionType = PotionEffectType.getByName(args[0]);
                if(potionType == null) {
                    sender.sendMessage("Tipo de poción inválido.");
                    return false;
                }

                ItemStack newEssence = player.getInventory().getItemInMainHand();
                int index = 0;
                while(true) {
                    if ( NBTEditor.contains( newEssence, "itemCustomEffects", "essence", "hitEffects", index+"") ) {
                        String currentPotionName = NBTEditor.getString(newEssence, "itemCustomEffects", "essence", "hitEffects", index+"");
                        PotionEffectType potionCurrent = PotionEffectType.getByName(currentPotionName);
                        if(potionCurrent == potionType) {
                            sender.sendMessage("Poción repetida!");
                            return false;
                        }
                        index++;
                    }

                    else break;
                }

                newEssence = NBTEditor.set(newEssence, effectArg, "itemCustomEffects", "essence", "hitEffects", index+"");
                player.getInventory().setItemInMainHand(newEssence);
            }

            else {
                sender.sendMessage(ChatColor.RED +"Parametros faltantes");
            }

            return true;
        } else {
            sender.sendMessage("Este comando solo puede ser ejecutado por un jugador en el juego.");
        }

        return false;
    }
}
