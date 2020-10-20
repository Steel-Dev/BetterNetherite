package com.github.steeldev.betternetherite.util;

import com.github.steeldev.betternetherite.BetterNetherite;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemChance {
    BetterNetherite main = BetterNetherite.getInstance();

    public Material Item;
    public int MaxAmount;
    public int Chance;
    public float ChanceF;
    public String ItemName;
    public List<String> ItemLore;
    public boolean Damaged;

    public ItemChance(Material item, int maxAmount, int chance){
        this.Item = item;
        this.MaxAmount = maxAmount;
        this.Chance = chance;
    }

    public ItemChance(Material item, int maxAmount, int chance, boolean damaged){
        this.Item = item;
        this.MaxAmount = maxAmount;
        this.Chance = chance;
        this.Damaged = damaged;
    }

    public ItemChance(Material item, int maxAmount, int chance, String itemName, List<String> itemLore){
        this.Item = item;
        this.MaxAmount = maxAmount;
        this.Chance = chance;
        this.ItemName = itemName;
        this.ItemLore = itemLore;
    }

    public ItemChance(Material item, int maxAmount, int chance, boolean damaged, String itemName, List<String> itemLore){
        this.Item = item;
        this.MaxAmount = maxAmount;
        this.Chance = chance;
        this.ItemName = itemName;
        this.ItemLore = itemLore;
        this.Damaged = damaged;
    }

    public ItemChance(Material item, int chance){
        this.Item = item;
        this.Chance = chance;
    }

    public ItemChance(Material item, float chance){
        this.Item = item;
        this.ChanceF = chance;
    }

    public ItemChance(Material item, float chance, String itemName, List<String> itemLore){
        this.Item = item;
        this.ChanceF = chance;
        this.ItemName = itemName;
        this.ItemLore = itemLore;
    }

    public ItemChance(Material item, float chance, boolean damaged, String itemName, List<String> itemLore){
        this.Item = item;
        this.ChanceF = chance;
        this.ItemName = itemName;
        this.ItemLore = itemLore;
        this.Damaged = damaged;
    }

    public ItemStack getItem(){
        ItemStack returnItem = new ItemStack(Item);

        int maxAm = MaxAmount;
        int finalAm = (maxAm > 1) ? main.rand.nextInt(maxAm) : 1;
        if(finalAm < 1) finalAm = 1;
        if(finalAm > 64) finalAm = 64;

        returnItem.setAmount(finalAm);

        ItemMeta returnItemMeta = (returnItem.getItemMeta() == null) ? Bukkit.getItemFactory().getItemMeta(Item) : returnItem.getItemMeta();
        if(returnItemMeta instanceof Damageable) {
            if (Damaged)
                ((Damageable) returnItemMeta).setDamage(main.rand.nextInt(returnItem.getType().getMaxDurability() - 10));
        }
        if(ItemName != null && !ItemName.equals(""))
            returnItemMeta.setDisplayName(main.colorize(ItemName));
        if(ItemLore != null && ItemLore.size() > 0){
            List<String> lore = new ArrayList<>();
            for(String line : ItemLore){
                lore.add(main.colorize(line));
            }
            returnItemMeta.setLore(lore);
        }
        returnItem.setItemMeta(returnItemMeta);

        return returnItem;
    }
}
