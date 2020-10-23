package com.github.steeldev.betternetherite.misc;

import com.github.steeldev.betternetherite.util.shrines.ShrineCharge;
import com.github.steeldev.betternetherite.util.shrines.ShrineCore;
import com.github.steeldev.betternetherite.util.shrines.ShrineEffect;
import org.bukkit.World;

import java.util.List;

public class BNShrine {

    public String key;
    public String display;
    public ShrineCore core;
    public ShrineCharge charge;
    public ShrineEffect effect;
    public List<World.Environment> validUseWorlds;
    public int explodeChance;
    public boolean requiresValidItems;

    public BNShrine(String key,
                    String display,
                    ShrineCore core,
                    ShrineCharge charge,
                    ShrineEffect effect,
                    List<World.Environment> validUseWorlds,
                    int explodeChance,
                    boolean requiresValidItems) {
        this.key = key;
        this.display = display;
        this.core = core;
        this.charge = charge;
        this.effect = effect;
        this.validUseWorlds = validUseWorlds;
        this.explodeChance = explodeChance;
        this.requiresValidItems = requiresValidItems;
    }
}
