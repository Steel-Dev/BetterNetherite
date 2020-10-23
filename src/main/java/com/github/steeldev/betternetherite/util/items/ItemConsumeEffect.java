package com.github.steeldev.betternetherite.util.items;

import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;

import java.util.List;

public class ItemConsumeEffect {
    public String effectDisplay;
    public List<BNPotionEffect> potionEffects;

    public ItemConsumeEffect(String effectDisplay,
                             List<BNPotionEffect> potionEffects) {
        this.effectDisplay = effectDisplay;
        this.potionEffects = potionEffects;
    }
}
