package com.johnson.enchantmentscrolls;

import org.bukkit.Material;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.Random;

public class InventoryDragListener implements Listener {


    private final EnchantmentScrollsPlugin plugin;
    private final Random random;

    public InventoryDragListener(EnchantmentScrollsPlugin plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCursor() == null || event.getCurrentItem() == null) {
            return;
        }

        ItemStack cursorItem = event.getCursor();
        ItemStack currentItem = event.getCurrentItem();

        if (cursorItem.getType() == Material.ENCHANTED_BOOK && currentItem.getType() != Material.AIR) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) cursorItem.getItemMeta();
            Map<Enchantment, Integer> storedEnchants = meta.getStoredEnchants();
            if (storedEnchants.isEmpty()) {
                return;
            }

            Enchantment enchantment = storedEnchants.keySet().iterator().next();
            int enchantmentLevel = storedEnchants.get(enchantment);

            Player player = (Player) event.getWhoClicked();
            if (!plugin.isAllowedEnchantment(currentItem.getType(), enchantment)) {
                player.sendMessage(plugin.getMessage("not_allowed_enchantment"));
                return;
            }

            Location location = player.getLocation();

            if (random.nextDouble() <= plugin.getEnchantmentSuccessRate(enchantment.getKey().getKey())) {
                currentItem.addUnsafeEnchantment(enchantment, enchantmentLevel);
                player.sendMessage(plugin.getMessage("success"));
                player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location, 30, 0.5, 1, 0.5, 0.1);
                player.playSound(location, Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
            } else {
                player.getInventory().removeItem(currentItem);
                player.getInventory().addItem(new ItemStack(Material.GUNPOWDER));
                player.sendMessage(plugin.getMessage("failure"));
                player.getWorld().spawnParticle(Particle.SMOKE_LARGE, location, 30, 0.5, 1, 0.5, 0.1);
                player.playSound(location, Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            }

            cursorItem.setAmount(cursorItem.getAmount() - 1);
            event.setCancelled(true);
        }
    }
}
