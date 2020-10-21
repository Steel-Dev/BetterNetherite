package com.github.steeldev.betternetherite.util.shrines;

import org.bukkit.Material;

public class ShrineCore {
    public Material CoreBlock;
    public Material CoreSupport;

    public ShrineCore(Material coreBlock,
                      Material coreSupport){
        this.CoreBlock = coreBlock;
        this.CoreSupport = coreSupport;
    }
}
