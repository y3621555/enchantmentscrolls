package com.johnson.enchantmentscrolls;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class EnchantmentScrollsPlugin extends JavaPlugin {

    private Map<Material, Set<Enchantment>> allowedItems;
    private FileConfiguration messagesConfig;
    private String prefix;

    @Override
    public void onEnable() {
        // 保存默認配置文件
        saveDefaultConfig();
        // 加載允許的物品和附魔列表
        loadAllowedItems();
        // 加載訊息配置
        loadMessagesConfig();
        // 註冊事件
        getServer().getPluginManager().registerEvents(new InventoryDragListener(this), this);
        getLogger().info("EnchantmentScrolls 已啟動");
    }

    @Override
    public void onDisable() {
        getLogger().info("EnchantmentScrolls 已關閉");
    }

    public double getEnchantmentSuccessRate(String enchantment) {
        FileConfiguration config = getConfig();
        return config.getDouble("enchantments." + enchantment, 0.5);
    }

    public boolean isAllowedEnchantment(Material material, Enchantment enchantment) {
        return allowedItems.containsKey(material) && allowedItems.get(material).contains(enchantment);
    }

    private void loadAllowedItems() {
        FileConfiguration config = getConfig();
        allowedItems = new HashMap<>();

        for (String itemName : config.getConfigurationSection("allowed-items").getKeys(false)) {
            Material material = Material.getMaterial(itemName);
            if (material != null) {
                List<String> enchantmentList = config.getStringList("allowed-items." + itemName);
                Set<Enchantment> enchantments = new HashSet<>();
                for (String enchantmentName : enchantmentList) {
                    Enchantment enchantment = Enchantment.getByKey(org.bukkit.NamespacedKey.minecraft(enchantmentName.toLowerCase()));
                    if (enchantment != null) {
                        enchantments.add(enchantment);
                    } else {
                        getLogger().warning("未知的附魔名稱: " + enchantmentName);
                    }
                }
                allowedItems.put(material, enchantments);
            } else {
                getLogger().warning("未知的物品名稱: " + itemName);
            }
        }
    }

    private void loadMessagesConfig() {
        File messageFile = new File(getDataFolder(), "message.yml");
        if (!messageFile.exists()) {
            saveResource("message.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messageFile);
        prefix = ChatColor.translateAlternateColorCodes('&', messagesConfig.getString("prefix", "&7[&aEnchantment&7] "));
    }

    public String getMessage(String key) {
        String message = messagesConfig.getString("messages." + key, key);
        return ChatColor.translateAlternateColorCodes('&', prefix + message);
    }

    public ItemStack createEnchantedScroll(Enchantment enchantment, int level) {
        ItemStack scroll = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) scroll.getItemMeta();
        meta.addStoredEnchant(enchantment, level, true);
        scroll.setItemMeta(meta);
        return scroll;
    }


}
