package com.github.steeldev.betternetherite.util.mobs;

import com.github.steeldev.betternetherite.util.misc.BNParticle;
import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;
import com.github.steeldev.betternetherite.util.misc.BNSound;

import java.util.List;

public class MobTargetEffect {
    public int chance;
    public BNParticle targetParticle;
    public BNSound targetSound;
    public List<BNPotionEffect> selfEffects;
    public List<BNPotionEffect> targetEffects;

    public MobTargetEffect(int chance,
                           BNParticle targetParticle,
                           BNSound targetSound,
                           List<BNPotionEffect> selfEffects,
                           List<BNPotionEffect> targetEffects) {
        this.chance = chance;
        this.targetParticle = targetParticle;
        this.targetSound = targetSound;
        this.selfEffects = selfEffects;
        this.targetEffects = targetEffects;
    }
}
