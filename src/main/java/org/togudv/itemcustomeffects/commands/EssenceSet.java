package org.togudv.itemcustomeffects.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.togudv.itemcustomeffects.utils.TriggerTypes;
import org.togudv.itemcustomeffects.utils.NBTEditor;

public class EssenceSet implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack currentItem = player.getInventory().getItemInMainHand();
            if(currentItem.getAmount() == 0 || currentItem == null) {
                sender.sendMessage("Iventario vacío");
                return true;
            }
            if(args.length >= 5) {
                if(!isValidArgs(args, sender)) return true;
                String triggerType = args[0];
                triggerType = triggerType.toLowerCase();
                String potionName = args[1];
                String effectLevel = args[2];
                String effectDuration = args[3];
                String effectProbs = args[4];
                String result = potionName+","+effectLevel+","+effectDuration+","+effectProbs+"f";
                PotionEffectType potionType = PotionEffectType.getByName(potionName);
                ItemStack newEssence = player.getInventory().getItemInMainHand();
                int index = 0;
                while(true) {
                    if ( NBTEditor.contains( newEssence, "itemCustomEffects", "essence", triggerType, index+"") ) {
                        String currentPotionName = NBTEditor.getString(newEssence, "itemCustomEffects", "essence", triggerType, index+"");
                        PotionEffectType potionCurrent = PotionEffectType.getByName(currentPotionName);

                        if(potionCurrent == potionType) {
                            sender.sendMessage("Poción repetida!");
                            return false;
                        }
                        index++;
                    }

                    else break;
                }

                newEssence = NBTEditor.set(newEssence, result, "itemCustomEffects", "essence", triggerType, index+"");
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

    public boolean isValidArgs(String[] args, CommandSender sender) {
        if(!TriggerTypes.isValidEffectTypeName(args[0])) {
            sender.sendMessage("Tipo de efecto no válido: " +args[0]);
            return false;
        }

        PotionEffectType potionType = PotionEffectType.getByName(args[1]);
        if(potionType == null) {
            sender.sendMessage("Tipo de poción inválido: " +args[1]);
            return false;
        }
        int level = 0;
        try {
            level = Integer.parseInt(args[2]);
        }

        catch (NumberFormatException e) {
            sender.sendMessage("El nivel especificado no es válido: " +args[2]);
            return  false;
        }
        if(level > 100 || level < 1) {
            sender.sendMessage("El nivel como máximo para este efecto debe ser 100: " +args[2]);
            return  false;
        }

        int duration = 0;
        try {
            duration = Integer.parseInt(args[3]);
        }

        catch (NumberFormatException e) {
            sender.sendMessage("la duración especificada no es válida: " +args[3]);
            return  false;
        }

        if(duration > 144000 || duration < 1) {
            sender.sendMessage("La duración como máximo para este efecto debe ser 7200 segundos y mínimo 1 segundo: " +args[3]);
            return  false;
        }

        float probs = 0;
        try {
            probs = Float.parseFloat(args[4]);
        }

        catch (NumberFormatException e) {
            sender.sendMessage("la probabilidad especificada no es válida: " +args[4]);
            return  false;
        }

        if(probs > 100f  || probs <= 0f) {
            sender.sendMessage("la probabilidad especificada debe estar entre 0.1 y 100: " +args[4]);
            return false;
        }



        return true;
    }
}
