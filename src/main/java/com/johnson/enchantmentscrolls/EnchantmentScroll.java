package com.johnson.enchantmentscrolls;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.List;
import java.util.Map;

public class EnchantmentScroll {

    private final Enchantment enchantment;
    private final int level;

    public EnchantmentScroll(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }

    public static EnchantmentScroll fromItemStack(ItemStack item) {
        if (item == null || item.getType() != Material.ENCHANTED_BOOK) {
            return null;
        }
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        Map<Enchantment, Integer> storedEnchants = meta.getStoredEnchants();
        if (storedEnchants.isEmpty()) {
            return null;
        }
        Enchantment enchantment = storedEnchants.keySet().iterator().next();
        int level = storedEnchants.get(enchantment);
        return new EnchantmentScroll(enchantment, level);
    }

    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        meta.addStoredEnchant(enchantment, level, true);
        item.setItemMeta(meta);
        return item;
    }

}
