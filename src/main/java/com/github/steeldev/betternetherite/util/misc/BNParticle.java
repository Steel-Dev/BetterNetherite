package com.github.steeldev.betternetherite.util.misc;

import org.bukkit.Location;
import org.bukkit.Particle;

public class BNParticle {
    public Particle particle;
    public int amount;

    public BNParticle(Particle particle,
                      int amount) {
        this.particle = particle;
        this.amount = amount;
    }

    public void spawnParticle(Location location) {
        location.getWorld().spawnParticle(particle, location, amount);
    }
}
