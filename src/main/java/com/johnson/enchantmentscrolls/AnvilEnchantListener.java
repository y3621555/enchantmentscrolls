package com.johnson.enchantmentscrolls;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class AnvilEnchantListener implements Listener {

    private final EnchantmentScrollsPlugin plugin;

    public AnvilEnchantListener(EnchantmentScrollsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory anvilInventory = event.getInventory();
        ItemStack resultItem = event.getResult();

        // 檢查結果物品是否有附魔
        if (resultItem != null && resultItem.getEnchantments().size() > 0) {
            event.setResult(null);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() instanceof AnvilInventory) {
            ItemStack resultItem = event.getCurrentItem();

            // 禁止物品在鐵砧中附魔
            if (resultItem != null && resultItem.getEnchantments().size() > 0) {
                event.setCancelled(true);
                //event.getWhoClicked().sendMessage(plugin.getMessage("anvil_enchant"));
            }
        }
    }
}