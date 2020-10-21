package com.github.steeldev.betternetherite.util.misc;

import org.bukkit.potion.PotionEffectType;

public class BNPotionEffect {
    public PotionEffectType Effect;
    public int Chance;
    public int Amplifier;
    public int Duration;

    public BNPotionEffect(PotionEffectType effect, int chance, int amplifier, int duration){
        this.Effect = effect;
        this.Chance = chance;
        this.Amplifier = amplifier;
        this.Duration = duration;
    }

    public org.bukkit.potion.PotionEffect getPotionEffect(){
        return new org.bukkit.potion.PotionEffect(Effect, Duration, Amplifier);
    }
}
