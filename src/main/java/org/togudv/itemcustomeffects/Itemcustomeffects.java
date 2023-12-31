package org.togudv.itemcustomeffects;


import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.togudv.itemcustomeffects.commands.EssenceSet;
import org.togudv.itemcustomeffects.commands.GivePoisonSword;
import org.togudv.itemcustomeffects.events.EssenceApplicationListener;
import org.togudv.itemcustomeffects.events.HandEvents;
import org.togudv.itemcustomeffects.events.HitEvents;
import org.togudv.itemcustomeffects.models.PlayerItems;


public final class Itemcustomeffects extends JavaPlugin implements Listener {

    private PlayerItems playerItems = new PlayerItems();
    private HandEvents handEvents = new HandEvents(playerItems);
    private HitEvents hitEvents = new HitEvents(playerItems);
    private EssenceApplicationListener essenceApplicationListener = new EssenceApplicationListener();
    @Override
    public void onEnable() {
        this.getCommand("poisonSword").setExecutor(new GivePoisonSword());
        this.getCommand("itemcustomeffectsset").setExecutor(new EssenceSet());
        getServer().getPluginManager().registerEvents(handEvents, this);
        getServer().getPluginManager().registerEvents(hitEvents, this);
        getServer().getPluginManager().registerEvents(essenceApplicationListener, this);

    }


}

