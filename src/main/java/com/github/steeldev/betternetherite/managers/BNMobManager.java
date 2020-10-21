package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.listeners.baselisteners.CustomMobBase;
import com.github.steeldev.betternetherite.misc.BNMob;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BNMobManager {
    static BetterNetherite main = BetterNetherite.getInstance();
    public static NamespacedKey MobsKey = new NamespacedKey(main, "better_netherite_mob");
    static Map<String, BNMob> bnMobMap;

    public static void registerNewBNMob(BNMob mob){
        if(bnMobMap == null) bnMobMap = new HashMap<>();

        if(bnMobMap.containsKey(mob.Key)) return;

        bnMobMap.put(mob.Key, mob);

        main.getServer().getPluginManager().registerEvents(new CustomMobBase(mob.Key), main);

        if(BetterConfig.DEBUG)
            main.getLogger().info(main.colorize("&aCustom mob &ebetternetherite:"+mob.Key+"&a has been &2registered."));
    }

    public static BNMob getBNMob(String key){
        if(!bnMobMap.containsKey(key)) return null;

        return bnMobMap.get(key);
    }

    public static List<String> getValidMobList(){
        return new ArrayList<>(bnMobMap.keySet());
    }
}
