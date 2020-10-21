package com.github.steeldev.betternetherite.misc;

import com.github.steeldev.betternetherite.util.shrines.ShrineCharge;
import com.github.steeldev.betternetherite.util.shrines.ShrineCore;
import com.github.steeldev.betternetherite.util.shrines.ShrineEffect;
import org.bukkit.World;

import java.util.List;

public class BNShrine {

    public String Key;
    public String Display;
    public ShrineCore Core;
    public ShrineCharge Charge;
    public ShrineEffect Effect;
    public List<World.Environment> ValidUseWorlds;
    public int ExplodeChance;
    public boolean RequiresValidItems;

    public BNShrine(String key,
                    String display,
                    ShrineCore core,
                    ShrineCharge charge,
                    ShrineEffect effect,
                    List<World.Environment> validUseWorlds,
                    int explodeChance,
                    boolean requiresValidItems){
        this.Key = key;
        this.Display = display;
        this.Core = core;
        this.Charge = charge;
        this.Effect = effect;
        this.ValidUseWorlds = validUseWorlds;
        this.ExplodeChance = explodeChance;
        this.RequiresValidItems = requiresValidItems;
    }
}
