package com.github.steeldev.betternetherite.misc;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.managers.BNShrineManager;
import com.github.steeldev.betternetherite.util.items.*;
import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BNItem {
    public String key;
    public Material baseItem;
    public String displayName;
    public List<String> lore;
    public int customModelData;
    public List<ItemAttributeInfo> attributeInfo;
    public List<ItemEnchantInfo> enchantInfo;
    public ItemUseEffect useEffect;
    public List<BNPotionEffect> attackEffect;
    public ItemConsumeEffect consumeEffect;
    public List<ItemNBTCompound> nbtCompoundList;
    BetterNetherite main = BetterNetherite.getInstance();

    public BNItem(String key,
                  Material baseItem) {
        this.key = key;
        this.baseItem = baseItem;
    }

    public BNItem withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public BNItem withConsumeEffect(ItemConsumeEffect effect) {
        if (this.baseItem.isEdible())
            this.consumeEffect = effect;
        return this;
    }

    public BNItem withUseEffect(ItemUseEffect effect) {
        this.useEffect = effect;
        return this;
    }

    public BNItem withEnchant(ItemEnchantInfo enchantInfo) {
        if (this.enchantInfo == null) this.enchantInfo = new ArrayList<>();
        this.enchantInfo.add(enchantInfo);
        return this;
    }

    public BNItem withAttribute(ItemAttributeInfo attributeInfo) {
        if (this.attributeInfo == null) this.attributeInfo = new ArrayList<>();
        this.attributeInfo.add(attributeInfo);
        return this;
    }

    public BNItem withLore(String line) {
        if (this.lore == null) this.lore = new ArrayList<>();
        this.lore.add(line);
        return this;
    }

    public BNItem withCustomModelData(int customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    public BNItem withNBT(ItemNBTCompound compound) {
        if (this.nbtCompoundList == null) this.nbtCompoundList = new ArrayList<>();
        this.nbtCompoundList.add(compound);
        return this;
    }

    public BNItem setReinforced(boolean reinforced) {
        BNShrine crimsonShrine = BNShrineManager.getBNShrine("crimson_shrine");
        if (crimsonShrine != null && reinforced) {
            if (this.nbtCompoundList == null) this.nbtCompoundList = new ArrayList<>();
            this.nbtCompoundList.add(new ItemNBTCompound("netherite_reinforced", reinforced));

            if (this.lore == null) this.lore = new ArrayList<>();
            this.lore.add(crimsonShrine.effect.effectLoreDisplay);
        }
        return this;
    }

    public ItemStack getItem(boolean damaged) {
        ItemStack customItem = new ItemStack(baseItem);

        ItemMeta customItemMeta = (customItem.getItemMeta() == null) ? Bukkit.getItemFactory().getItemMeta(baseItem) : customItem.getItemMeta();

        if (customItemMeta != null) {
            if (displayName != null && !displayName.equals(""))
                customItemMeta.setDisplayName(main.colorize(displayName));
            if (lore != null && lore.size() > 0) {
                List<String> lore = new ArrayList<>();
                for (String line : this.lore) {
                    lore.add(main.colorize(line));
                }
                customItemMeta.setLore(lore);
            }
            if (customModelData != 0)
                customItemMeta.setCustomModelData(customModelData);

            if (attributeInfo != null && attributeInfo.size() > 0) {
                for (ItemAttributeInfo attributeInfo : attributeInfo) {
                    customItemMeta.addAttributeModifier(attributeInfo.attributeMod, new AttributeModifier(UUID.randomUUID(), attributeInfo.attributeModName, attributeInfo.attributeValue, AttributeModifier.Operation.ADD_NUMBER, attributeInfo.slot));
                }
            }

            if (damaged) {
                if (customItemMeta instanceof Damageable)
                    ((Damageable) customItemMeta).setDamage(main.rand.nextInt(baseItem.getMaxDurability() - 20));
            }

            customItem.setItemMeta(customItemMeta);
        }

        if (enchantInfo != null && enchantInfo.size() > 0) {
            for (ItemEnchantInfo enchantInfo : enchantInfo) {
                customItem.addUnsafeEnchantment(enchantInfo.enchant, enchantInfo.level);
            }
        }

        NBTItem customItemNBT = new NBTItem(customItem);
        customItemNBT.addCompound("BNItem");
        customItemNBT.setString("BNItem", key);
        if (nbtCompoundList != null && nbtCompoundList.size() > 0) {
            for (ItemNBTCompound compound : nbtCompoundList) {
                customItemNBT.addCompound(compound.compoundKey);
                switch (compound.compoundType) {
                    case BOOLEAN:
                        customItemNBT.setBoolean(compound.compoundKey, (Boolean) compound.compoundValue);
                        break;
                    case DOUBLE:
                        customItemNBT.setDouble(compound.compoundKey, (Double) compound.compoundValue);
                        break;
                    case FLOAT:
                        customItemNBT.setFloat(compound.compoundKey, (Float) compound.compoundValue);
                        break;
                    case STRING:
                        customItemNBT.setString(compound.compoundKey, (String) compound.compoundValue);
                        break;
                    case INTEGER:
                        customItemNBT.setInteger(compound.compoundKey, (Integer) compound.compoundValue);
                        break;
                }
            }
        }
        customItem = customItemNBT.getItem();

        return customItem;
    }
}
