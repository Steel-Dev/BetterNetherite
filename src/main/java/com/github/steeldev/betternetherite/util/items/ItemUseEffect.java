package com.github.steeldev.betternetherite.util.items;

import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;

import java.util.List;

public class ItemUseEffect {
    public ItemUseEffectType type;
    public String mobID;
    public List<BNPotionEffect> potionEffects;
    public boolean consumeItemOnUse;

    public ItemUseEffect(ItemUseEffectType type,
                         List<BNPotionEffect> potionEffects,
                         boolean consumeItemOnUse) {
        this.type = type;
        this.potionEffects = potionEffects;
        this.consumeItemOnUse = consumeItemOnUse;
    }

    public ItemUseEffect(ItemUseEffectType type,
                         String mobID) {
        this.type = type;
        this.mobID = mobID;
    }
}
