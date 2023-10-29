package org.togudv.itemcustomeffects.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.togudv.itemcustomeffects.utils.NBTEditor;

public class GivePoisonSword implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack apple = new ItemStack(Material.APPLE);
            apple = NBTEditor.set(apple, "BananaPuncher714", "customeffects", "item", "owner", "", "postempty", "hola:3" );
            sender.sendMessage("Vales puro pito, el custom meta es: ");
            player.getInventory().addItem(apple);

            return true;
        } else {
            sender.sendMessage("Este comando solo puede ser ejecutado por un jugador en el juego.");
        }

        return false;
    }
}
