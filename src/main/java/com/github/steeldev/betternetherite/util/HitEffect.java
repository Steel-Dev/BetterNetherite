package com.github.steeldev.betternetherite.util;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HitEffect {
    public PotionEffectType Effect;
    public int Chance;
    public int Amplifier;
    public int Duration;

    public HitEffect(PotionEffectType effect, int chance, int amplifier, int duration){
        this.Effect = effect;
        this.Chance = chance;
        this.Amplifier = amplifier;
        this.Duration = duration;
    }

    public PotionEffect getPotionEffect(){
        return new PotionEffect(Effect, Duration, Amplifier);
    }
}
