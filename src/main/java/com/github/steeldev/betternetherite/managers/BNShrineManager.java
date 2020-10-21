package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.listeners.baselisteners.ShrineBase;
import com.github.steeldev.betternetherite.misc.BNShrine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BNShrineManager {
    static BetterNetherite main = BetterNetherite.getInstance();
    static Map<String, BNShrine> bnShrineMap;

    public static void registerNewShrine(BNShrine shrine){
        if(bnShrineMap == null) bnShrineMap = new HashMap<>();

        if(bnShrineMap.containsKey(shrine.Key)) return;

        bnShrineMap.put(shrine.Key, shrine);

        main.getServer().getPluginManager().registerEvents(new ShrineBase(shrine.Key), main);

        if(BetterConfig.DEBUG)
            main.getLogger().info(main.colorize("&aCustom shrine &ebetternetherite:"+shrine.Key+"&a has been &2registered."));
    }

    public static BNShrine getBNShrine(String key){
        if(!bnShrineMap.containsKey(key)) return null;

        return bnShrineMap.get(key);
    }

    public static List<String> getValidShrineList(){
        return new ArrayList<>(bnShrineMap.keySet());
    }
}
