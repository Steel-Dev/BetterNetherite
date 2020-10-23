package com.github.steeldev.betternetherite.util.mobs;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.misc.BNItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemChance {
    public Material nItem;
    public BNItem item;
    public int maxAmount;
    public int chance;
    public float chanceF;
    public boolean damaged;
    BetterNetherite main = BetterNetherite.getInstance();

    public ItemChance(BNItem item, int chance) {
        this.item = item;
        this.chance = chance;
    }

    public ItemChance(BNItem item, int chance, boolean damaged) {
        this.item = item;
        this.chance = chance;
        this.damaged = damaged;
    }

    public ItemChance(BNItem item, int maxAmount, int chance) {
        this.item = item;
        this.maxAmount = maxAmount;
        this.chance = chance;
    }

    public ItemChance(BNItem item, int maxAmount, int chance, boolean damaged) {
        this.item = item;
        this.maxAmount = maxAmount;
        this.chance = chance;
        this.damaged = damaged;
    }

    public ItemChance(BNItem item, float chance) {
        this.item = item;
        this.chanceF = chance;
    }

    public ItemChance(BNItem item, float chance, boolean damaged) {
        this.item = item;
        this.chanceF = chance;
        this.damaged = damaged;
    }

    public ItemChance(BNItem item, int maxAmount, float chance) {
        this.item = item;
        this.maxAmount = maxAmount;
        this.chanceF = chance;
    }

    public ItemChance(BNItem item, int maxAmount, float chance, boolean damaged) {
        this.item = item;
        this.maxAmount = maxAmount;
        this.chanceF = chance;
        this.damaged = damaged;
    }


    public ItemChance(Material item, int chance) {
        this.nItem = item;
        this.chance = chance;
    }

    public ItemChance(Material item, int chance, boolean damaged) {
        this.nItem = item;
        this.chance = chance;
        this.damaged = damaged;
    }

    public ItemChance(Material item, int maxAmount, int chance) {
        this.nItem = item;
        this.maxAmount = maxAmount;
        this.chance = chance;
    }

    public ItemChance(Material item, int maxAmount, int chance, boolean damaged) {
        this.nItem = item;
        this.maxAmount = maxAmount;
        this.chance = chance;
        this.damaged = damaged;
    }

    public ItemChance(Material item, float chance) {
        this.nItem = item;
        this.chanceF = chance;
    }

    public ItemChance(Material item, float chance, boolean damaged) {
        this.nItem = item;
        this.chanceF = chance;
        this.damaged = damaged;
    }

    public ItemChance(Material item, int maxAmount, float chance) {
        this.nItem = item;
        this.maxAmount = maxAmount;
        this.chanceF = chance;
    }

    public ItemChance(Material item, int maxAmount, float chance, boolean damaged) {
        this.nItem = item;
        this.maxAmount = maxAmount;
        this.chanceF = chance;
        this.damaged = damaged;
    }

    public ItemStack getItem(boolean damaged) {
        if (nItem != null) {
            ItemStack customItem = new ItemStack(nItem);

            ItemMeta customItemMeta = (customItem.getItemMeta() == null) ? Bukkit.getItemFactory().getItemMeta(nItem) : customItem.getItemMeta();

            if (customItemMeta != null) {
                if (damaged) {
                    if (customItemMeta instanceof Damageable)
                        ((Damageable) customItemMeta).setDamage(main.rand.nextInt(nItem.getMaxDurability() - 20));
                }

                customItem.setItemMeta(customItemMeta);
            }

            return customItem;
        } else if (item != null) {
            return item.getItem(damaged);
        }
        return null;
    }
}
