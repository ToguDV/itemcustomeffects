package org.togudv.itemcustomeffects.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.togudv.itemcustomeffects.models.PlayerItems;

public class GivePoisonSword implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack apple = new ItemStack(Material.APPLE);
            ItemMeta meta = apple.getItemMeta();
            meta.setCustomModelData(69);

            apple.setItemMeta(meta);
            int custom = apple.getItemMeta().getCustomModelData();
            sender.sendMessage("Vales puro pito, el custom meta es: "+custom);
            player.getInventory().addItem(apple);

            return true;
        } else {
            sender.sendMessage("Este comando solo puede ser ejecutado por un jugador en el juego.");
        }

        return false;
    }
}
