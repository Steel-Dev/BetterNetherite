package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.listeners.baselisteners.CustomMobBase;
import com.github.steeldev.betternetherite.misc.BNMob;
import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;
import com.github.steeldev.betternetherite.util.mobs.ItemChance;
import com.github.steeldev.betternetherite.util.mobs.MountInfo;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class BNMobManager {
    static BetterNetherite main = BetterNetherite.getInstance();
    public static NamespacedKey MobsKey = new NamespacedKey(main, "better_netherite_mob");
    static Map<String, BNMob> bnMobMap;

    public static void registerNewBNMob(BNMob mob) {
        if (bnMobMap == null) bnMobMap = new HashMap<>();

        if (bnMobMap.containsKey(mob.key)) return;

        bnMobMap.put(mob.key, mob);

        main.getServer().getPluginManager().registerEvents(new CustomMobBase(mob.key), main);

        if (BetterConfig.DEBUG)
            main.getLogger().info(main.colorize(String.format("&aCustom mob &ebetternetherite:%s&a has been &2registered.", mob.key)));
    }

    public static BNMob getBNMob(String key) {
        if (!bnMobMap.containsKey(key)) return null;

        return bnMobMap.get(key);
    }

    public static void registerCustomMobs() {
        if (BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED) {
            registerNewBNMob(new BNMob("netherite_marauder",
                    EntityType.WITHER_SKELETON,
                    "<#571664>Netherite <#3c1a4c>Marauder",
                    10).
                    withEntityToReplace(EntityType.WITHER_SKELETON).
                    withMount(new MountInfo(EntityType.SKELETON_HORSE, 40, Arrays.asList(Material.IRON_HORSE_ARMOR, Material.GOLDEN_HORSE_ARMOR), 20)).
                    withCustomDeathEXP(20).
                    withCustomMaxHP(80).
                    withCustomMoveSpeed(0.2f).
                    withValidSpawnWorld(World.Environment.NETHER).
                    withHitEffect(new BNPotionEffect(PotionEffectType.BLINDNESS, 30, 1, 60)).
                    withDropToRemove(Material.BONE).
                    withDropToRemove(Material.COAL).
                    withDrop(new ItemChance(Material.NETHERITE_SCRAP, 2, 20)).
                    withDrop(new ItemChance(Material.NETHERITE_INGOT, 3, 5)).
                    withDrop(new ItemChance(BNItemManager.getBNItem("marauder_bone"), 2, 60)).
                    withMainHandItem(new ItemChance(BNItemManager.getBNItem("marauder_sword"), 0.1f, true)).
                    withChestplate(new ItemChance(BNItemManager.getBNItem("marauder_chestplate"), 0.05f, true)));
        }
        if (BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED) {
            registerNewBNMob(new BNMob("netherite_marauder_brute",
                    EntityType.WITHER_SKELETON,
                    "<#571664>Netherite <#3c1a4c>Marauder Brute",
                    10).
                    withEntityToReplace(EntityType.WITHER_SKELETON).
                    withMount(new MountInfo(EntityType.ZOMBIE_HORSE, 40, Arrays.asList(Material.DIAMOND_HORSE_ARMOR, Material.IRON_HORSE_ARMOR), 20)).
                    withCustomDeathEXP(20).
                    withCustomMaxHP(80).
                    withCustomMoveSpeed(0.2f).
                    withValidSpawnWorld(World.Environment.NETHER).
                    withHitEffect(new BNPotionEffect(PotionEffectType.BLINDNESS, 30, 1, 60)).
                    withDropToRemove(Material.BONE).
                    withDropToRemove(Material.COAL).
                    withDrop(new ItemChance(Material.NETHERITE_SCRAP, 2, 20)).
                    withDrop(new ItemChance(Material.NETHERITE_INGOT, 3, 5)).
                    withDrop(new ItemChance(BNItemManager.getBNItem("marauder_bone"), 2, 60)).
                    withMainHandItem(new ItemChance(BNItemManager.getBNItem("marauder_axe"), 0.1f, true)).
                    withChestplate(new ItemChance(BNItemManager.getBNItem("marauder_chestplate"), 0.05f, true)));
        }
        if (BetterConfig.CUSTOM_MOB_HELLHOUND_ENABLED) {
            registerNewBNMob(new BNMob("hellhound",
                    EntityType.WOLF,
                    "<#571664>Hellhound",
                    12).
                    withAnger(true).
                    withEntityToReplace(EntityType.ZOMBIFIED_PIGLIN).
                    withCustomDeathEXP(10).
                    withCustomMaxHP(10).
                    withCustomMoveSpeed(0.4f).
                    withValidSpawnWorld(World.Environment.NETHER).
                    withHitEffect(new BNPotionEffect(PotionEffectType.WITHER, 30, 1, 60)).
                    withDrop(new ItemChance(BNItemManager.getBNItem("rotten_hound_meat"), 3, 30)).
                    withDrop(new ItemChance(BNItemManager.getBNItem("hound_meat"), 3, 40)));
        }
        if (BetterConfig.CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED) {
            registerNewBNMob(new BNMob("alpha_hellhound",
                    EntityType.WOLF,
                    "<#571664>Alpha Hellhound",
                    12).
                    withAnger(true).
                    withEntityToReplace(EntityType.ZOMBIFIED_PIGLIN).
                    withCustomDeathEXP(20).
                    withCustomMaxHP(20).
                    withCustomMoveSpeed(0.5f).
                    withValidSpawnWorld(World.Environment.NETHER).
                    withHitEffect(new BNPotionEffect(PotionEffectType.WITHER, 30, 1, 60)).
                    withDrop(new ItemChance(BNItemManager.getBNItem("rotten_hound_meat"), 4, 30)).
                    withDrop(new ItemChance(BNItemManager.getBNItem("hound_meat"), 4, 40)));
        }
        if (BetterConfig.CUSTOM_MOB_LOST_SOUL_ENABLED) {
            registerNewBNMob(new BNMob("lost_soul",
                    EntityType.VEX,
                    "<#1152a6>Lost Soul",
                    5).
                    withEntityToReplace(EntityType.ZOMBIFIED_PIGLIN).
                    withCustomDeathEXP(30).
                    withCustomMaxHP(10).
                    withCustomMoveSpeed(0.5f).
                    withValidSpawnWorld(World.Environment.NETHER).
                    withHitEffect(new BNPotionEffect(PotionEffectType.WITHER, 30, 1, 60)).
                    withHitEffect(new BNPotionEffect(PotionEffectType.POISON, 70, 1, 60)).
                    withHitEffect(new BNPotionEffect(PotionEffectType.BLINDNESS, 50, 1, 60)).
                    withDeathExplosion(true, 90, 2, true).
                    withDrop(new ItemChance(BNItemManager.getBNItem("soul"), 2, 5)));
        }
        if (BetterConfig.CUSTOM_MOB_ZOMBIFIED_DEMON_ENABLED) {
            registerNewBNMob(new BNMob("zombified_demon",
                    EntityType.ZOMBIE,
                    "<#0f853c>Zombified Demon",
                    5).
                    withEntityToReplace(EntityType.ZOMBIFIED_PIGLIN).
                    withEntityToReplace(EntityType.ZOMBIE).
                    withCustomDeathEXP(30).
                    withCustomMaxHP(15).
                    withCustomMoveSpeed(0.4f).
                    withValidSpawnWorld(World.Environment.NETHER).
                    withValidSpawnWorld(World.Environment.NORMAL).
                    withHitEffect(new BNPotionEffect(PotionEffectType.HUNGER, 30, 1, 60)).
                    withHitEffect(new BNPotionEffect(PotionEffectType.POISON, 70, 1, 60)).
                    withDrop(new ItemChance(BNItemManager.getBNItem("rotten_demon_flesh"), 2, 5)).
                    withHelmet(new ItemChance(Material.IRON_HELMET, 0.3f, true)));
        }
    }

    public static List<String> getValidMobList() {
        return new ArrayList<>(bnMobMap.keySet());
    }
}
