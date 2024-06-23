package com.johnson.enchantmentscrolls;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

public class EnchantingTableListener implements Listener {

    private final EnchantmentScrollsPlugin plugin;

    public EnchantingTableListener(EnchantmentScrollsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
        event.setCancelled(true);
        //event.getEnchanter().sendMessage(plugin.getMessage("table_enchant"));
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        event.setCancelled(true);
        //event.getEnchanter().sendMessage(plugin.getMessage("table_enchant"));
    }
}