package com.github.steeldev.betternetherite.util.shrines;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class ShrineCharge {
    public Sound UsedSound;
    public Material ChargeMaterial;
    public Particle UsedParticle;

    public ShrineCharge(Sound usedSound,
                        Material chargeMaterial,
                        Particle usedParticle){
        this.UsedSound = usedSound;
        this.ChargeMaterial = chargeMaterial;
        this.UsedParticle = usedParticle;
    }
}
