package me.quexer.homeserver.lobby.enums;

import me.quexer.homeserver.serverapi.quickyapi.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ContentType {

    EMERALD_BLOCK("§e100.000 §7Coins", new ItemBuilder(Material.EMERALD_BLOCK).setName("§e100.000 §7Coins").toItemStack(), 100000, Seltenheit.LEGENDARY),
    DIAMOND_BLOCK("§e50.000 §7Coins", new ItemBuilder(Material.DIAMOND_BLOCK).setName("§e50.000 §7Coins").toItemStack(), 50000, Seltenheit.ULTIMATE),
    REDTONE_ORE("§cTraitor Pass", new ItemBuilder(Material.REDSTONE_ORE).setName("§cTraitor Pass").toItemStack(), 50000, Seltenheit.ULTIMATE),
    GOLD_BLOCK("§e25.000 §7Coins", new ItemBuilder(Material.GOLD_BLOCK).setName("§e25.000 §7Coins").toItemStack(), 25000, Seltenheit.EPIC),
    LAPIS_ORE("§9Detective Pass", new ItemBuilder(Material.LAPIS_ORE).setName("§9Detective Pass").toItemStack(), 20000, Seltenheit.EPIC),
    GOLD_INGOT("§e10.000 §7Coins", new ItemBuilder(Material.GOLD_INGOT).setName("§e10.000 §7Coins").toItemStack(), 10000, Seltenheit.RARE),
    GOLD_NUGGET("§e5.000 §7Coins", new ItemBuilder(Material.GOLD_NUGGET).setName("§e5.000 §7Coins").toItemStack(), 5000, Seltenheit.COMMON);


    private String name;
    private ItemStack itemStack;
    private long price;
    private Seltenheit rarity;

    ContentType(String name, ItemStack itemStack, long price, Seltenheit rarity) {
        this.name = name;
        this.itemStack = itemStack;
        this.price = price;
        this.rarity = rarity;
    }

    public long getPrice() {
        return price;
    }

    public Seltenheit getRarity() {
        return rarity;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }


}
