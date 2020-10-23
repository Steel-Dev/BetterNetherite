package com.github.steeldev.betternetherite.util.shrines;

import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.util.List;

public class ShrineEffect {
    public ShrineEffectType shrineEffectType;
    public Sound useSound;
    public Sound breakSound;
    public Sound noChargesSound;
    public Effect breakEffect;
    public Particle breakParticle;
    public String effectDisplay;
    public String effectResultDisplay;
    public String effectLoreDisplay;
    public Particle shrineUsedParticle;
    public List<BNPotionEffect> potionEffects;

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
        this.shrineEffectType = shrineEffectType;
        this.breakParticle = breakParticle;
        this.useSound = useSound;
        this.breakSound = breakSound;
        this.noChargesSound = noChargesSound;
        this.breakEffect = breakEffect;
        this.effectDisplay = effectDisplay;
        this.effectLoreDisplay = effectLoreDisplay;
        this.effectResultDisplay = effectResultDisplay;
        this.shrineUsedParticle = shrineUsedParticle;
    }

    public ShrineEffect(ShrineEffectType shrineEffectType,
                        Sound useSound,
                        Sound breakSound,
                        Sound noChargesSound,
                        Effect breakEffect,
                        Particle breakParticle,
                        String effectDisplay,
                        String effectResultDisplay,
                        Particle shrineUsedParticle) {
        this.shrineEffectType = shrineEffectType;
        this.breakParticle = breakParticle;
        this.useSound = useSound;
        this.breakSound = breakSound;
        this.noChargesSound = noChargesSound;
        this.breakEffect = breakEffect;
        this.effectDisplay = effectDisplay;
        this.effectResultDisplay = effectResultDisplay;
        this.shrineUsedParticle = shrineUsedParticle;
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
                        List<BNPotionEffect> potionEffects) {
        this.shrineEffectType = shrineEffectType;
        this.breakParticle = breakParticle;
        this.useSound = useSound;
        this.breakSound = breakSound;
        this.noChargesSound = noChargesSound;
        this.breakEffect = breakEffect;
        this.effectDisplay = effectDisplay;
        this.effectResultDisplay = effectResultDisplay;
        this.shrineUsedParticle = shrineUsedParticle;
        this.potionEffects = potionEffects;
    }
}
