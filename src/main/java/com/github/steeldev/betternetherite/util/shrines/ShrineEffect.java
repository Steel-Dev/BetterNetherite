package com.github.steeldev.betternetherite.util.shrines;

import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.util.List;

public class ShrineEffect {
    public ShrineEffectType ShrineEffectType;
    public Sound UseSound;
    public Sound BreakSound;
    public Sound NoChargesSound;
    public Effect BreakEffect;
    public Particle BreakParticle;
    public String EffectDisplay;
    public String EffectResultDisplay;
    public String EffectLoreDisplay;
    public Particle ShrineUsedParticle;
    public List<BNPotionEffect> PotionEffects;

    public ShrineEffect(ShrineEffectType shrineEffectType,
                        Sound useSound,
                        Sound breakSound,
                        Sound noChargesSound,
                        Effect breakEffect,
                        Particle breakParticle,
                        String effectDisplay,
                        String effectLoreDisplay,
                        String effectResultDisplay,
                        Particle shrineUsedParticle) {
        this.ShrineEffectType = shrineEffectType;
        this.BreakParticle = breakParticle;
        this.UseSound = useSound;
        this.BreakSound = breakSound;
        this.NoChargesSound = noChargesSound;
        this.BreakEffect = breakEffect;
        this.EffectDisplay = effectDisplay;
        this.EffectLoreDisplay = effectLoreDisplay;
        this.EffectResultDisplay = effectResultDisplay;
        this.ShrineUsedParticle = shrineUsedParticle;
    }

    public ShrineEffect(ShrineEffectType shrineEffectType,
                        Sound useSound,
                        Sound breakSound,
                        Sound noChargesSound,
                        Effect breakEffect,
                        Particle breakParticle,
                        String effectDisplay,
                        String effectResultDisplay,
                        Particle shrineUsedParticle){
        this.ShrineEffectType = shrineEffectType;
        this.BreakParticle = breakParticle;
        this.UseSound = useSound;
        this.BreakSound = breakSound;
        this.NoChargesSound = noChargesSound;
        this.BreakEffect = breakEffect;
        this.EffectDisplay = effectDisplay;
        this.EffectResultDisplay = effectResultDisplay;
        this.ShrineUsedParticle = shrineUsedParticle;
    }

    public ShrineEffect(ShrineEffectType shrineEffectType,
                        Sound useSound,
                        Sound breakSound,
                        Sound noChargesSound,
                        Effect breakEffect,
                        Particle breakParticle,
                        String effectDisplay,
                        String effectResultDisplay,
                        Particle shrineUsedParticle,
                        List<BNPotionEffect> potionEffects){
        this.ShrineEffectType = shrineEffectType;
        this.BreakParticle = breakParticle;
        this.UseSound = useSound;
        this.BreakSound = breakSound;
        this.NoChargesSound = noChargesSound;
        this.BreakEffect = breakEffect;
        this.EffectDisplay = effectDisplay;
        this.EffectResultDisplay = effectResultDisplay;
        this.ShrineUsedParticle = shrineUsedParticle;
        this.PotionEffects = potionEffects;
    }
}
