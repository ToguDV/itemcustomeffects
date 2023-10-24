package org.togudv.itemcustomeffects;


import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.togudv.itemcustomeffects.commands.GivePoisonSword;
import org.togudv.itemcustomeffects.events.HandEvents;
import org.togudv.itemcustomeffects.events.PVPEvents;
import org.togudv.itemcustomeffects.models.PlayerItems;


public final class Itemcustomeffects extends JavaPlugin implements Listener {

    private PlayerItems playerItems = new PlayerItems();
    private HandEvents handEvents = new HandEvents(playerItems);
    private PVPEvents pvpEvents = new PVPEvents(playerItems);
    @Override
    public void onEnable() {
        this.getCommand("poisonSword").setExecutor(new GivePoisonSword());
        getServer().getPluginManager().registerEvents(handEvents, this);
        getServer().getPluginManager().registerEvents(pvpEvents, this);

    }


}

