package com.github.steeldev.betternetherite.util.mobs;

public class DeathExplosionInfo {
    public boolean enabled;
    public int chance;
    public int size;
    public boolean createsFire;

    public DeathExplosionInfo(boolean enabled, int chance, int size, boolean createsFire) {
        this.enabled = enabled;
        this.chance = chance;
        this.size = size;
        this.createsFire = createsFire;
    }
}
