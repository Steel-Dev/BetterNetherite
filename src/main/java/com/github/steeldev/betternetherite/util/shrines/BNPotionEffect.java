package com.github.steeldev.betternetherite.util.shrines;

import org.bukkit.potion.PotionEffectType;

public class BNPotionEffect {
    public PotionEffectType effect;
    public int chance;
    public int amplifier;
    public int duration;

    public BNPotionEffect(PotionEffectType effect, int chance, int amplifier, int duration) {
        this.effect = effect;
        this.chance = chance;
        this.amplifier = amplifier;
        this.duration = duration;
    }

    public org.bukkit.potion.PotionEffect getPotionEffect() {
        return new org.bukkit.potion.PotionEffect(effect, duration, amplifier);
    }
}
