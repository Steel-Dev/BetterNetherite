package com.github.steeldev.betternetherite.misc;

import com.github.steeldev.betternetherite.listeners.baselisteners.ShrineBase;
import com.github.steeldev.betternetherite.util.shrines.ShrineCharge;
import com.github.steeldev.betternetherite.util.shrines.ShrineCore;
import com.github.steeldev.betternetherite.util.shrines.ShrineEffect;

import java.util.ArrayList;
import java.util.List;

public class BNShrine {

    public String key;
    public String display;
    public ShrineCore core;
    public ShrineCharge charge;
    public ShrineEffect effect;
    public List<String> validUseWorlds;
    public int explodeChance;
    public boolean requiresValidItems;

    public ShrineBase eventListener;

    public BNShrine(String key,
                    String display,
                    ShrineCore core,
                    ShrineCharge charge) {
        this.key = key;
        this.display = display;
        this.core = core;
        this.charge = charge;
    }

    public BNShrine withCustomEffect(ShrineEffect effect) {
        this.effect = effect;
        return this;
    }

    public BNShrine withValidWorld(String world) {
        if (this.validUseWorlds == null) this.validUseWorlds = new ArrayList<>();
        this.validUseWorlds.add(world);
        return this;
    }

    public BNShrine withValidWorlds(List<String> worlds) {
        this.validUseWorlds = worlds;
        return this;
    }

    public BNShrine withExplodeChance(int chance) {
        this.explodeChance = chance;
        return this;
    }

    public BNShrine withItemRequirement(boolean requiresItem) {
        this.requiresValidItems = requiresItem;
        return this;
    }
}
