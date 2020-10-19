package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.listeners.custommobs.CustomMobBase;
import com.github.steeldev.betternetherite.misc.BNMob;

import java.util.HashMap;
import java.util.Map;

public class BNMobManager {
    static BetterNetherite main = BetterNetherite.getInstance();
    static Map<String, BNMob> bnMobMap;

    public static void registerNewBNMob(BNMob mob){
        if(bnMobMap == null) bnMobMap = new HashMap<>();

        if(bnMobMap.containsKey(mob.Key)) return;

        bnMobMap.put(mob.Key, mob);

        main.getServer().getPluginManager().registerEvents(new CustomMobBase(mob.Key), main);
    }

    public static BNMob getBNMob(String key){
        if(!bnMobMap.containsKey(key)) return null;

        return bnMobMap.get(key);
    }
}
