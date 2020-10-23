package com.github.steeldev.betternetherite.util.shrines;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class ShrineCharge {
    public Sound usedSound;
    public Material chargeMaterial;
    public Particle usedParticle;

    public ShrineCharge(Sound usedSound,
                        Material chargeMaterial,
                        Particle usedParticle) {
        this.usedSound = usedSound;
        this.chargeMaterial = chargeMaterial;
        this.usedParticle = usedParticle;
    }
}
