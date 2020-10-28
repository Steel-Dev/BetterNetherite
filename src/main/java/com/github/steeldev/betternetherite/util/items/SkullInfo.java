package com.github.steeldev.betternetherite.util.items;

import org.bukkit.OfflinePlayer;

public class SkullInfo {
    public OfflinePlayer owningPlayer;
    public String base64;

    public SkullInfo() {

    }

    public SkullInfo(OfflinePlayer owningPlayer) {
        this.owningPlayer = owningPlayer;
    }

    public SkullInfo(String base64) {
        this.base64 = base64;
    }
}
