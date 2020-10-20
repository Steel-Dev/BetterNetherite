package com.github.steeldev.betternetherite.util;

public class DeathExplosionInfo {
    public boolean Enabled;
    public int Chance;
    public int Size;
    public boolean CreatesFire;

    public DeathExplosionInfo(boolean enabled, int chance, int size, boolean createsFire){
        this.Enabled = enabled;
        this.Chance = chance;
        this.Size = size;
        this.CreatesFire = createsFire;
    }
}
