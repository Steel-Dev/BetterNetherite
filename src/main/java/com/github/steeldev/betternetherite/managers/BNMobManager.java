package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.listeners.baselisteners.CustomMobBase;
import com.github.steeldev.betternetherite.misc.BNMob;
import com.github.steeldev.betternetherite.util.misc.BNParticle;
import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;
import com.github.steeldev.betternetherite.util.misc.BNSound;
import com.github.steeldev.betternetherite.util.mobs.*;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static com.github.steeldev.betternetherite.util.Util.colorize;

public class BNMobManager {
    static BetterNetherite main = BetterNetherite.getInstance();
    public static NamespacedKey MobsKey = new NamespacedKey(main, "better_netherite_mob");
    static Map<String, BNMob> bnMobMap;


    static Map<UUID, LivingEntity> spawnedBNMobs;

    public static void init() {
        spawnedBNMobs = new HashMap<>();
    }

    public static void registerNewBNMob(BNMob mob) {
        if (bnMobMap == null) bnMobMap = new HashMap<>();

        if (bnMobMap.containsKey(mob.key)) return;

        bnMobMap.put(mob.key, mob);

        main.getServer().getPluginManager().registerEvents(new CustomMobBase(mob.key), main);

        if (BetterConfig.DEBUG)
            main.getLogger().info(String.format("&aCustom mob &ebetternetherite:%s&a has been &2registered.", mob.key));
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
                    BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_SPAWNCHANCE)
                    .withEntityToReplace(EntityType.WITHER_SKELETON)
                    .withMount(new MountInfo(EntityType.SKELETON_HORSE, 40, Arrays.asList(Material.IRON_HORSE_ARMOR, Material.GOLDEN_HORSE_ARMOR), 20))
                    .withCustomDeathEXP(20)
                    .withCustomMaxHP(20)
                    .withCustomMoveSpeed(0.2f)
                    .withValidSpawnWorld(World.Environment.NETHER)
                    .withHitEffect(new BNPotionEffect(PotionEffectType.BLINDNESS, 30, 1, 60))
                    .withDropToRemove(Material.BONE)
                    .withDropToRemove(Material.COAL)
                    .withDrop(new ItemChance(Material.NETHERITE_SCRAP, 2, 20))
                    .withDrop(new ItemChance(Material.NETHERITE_INGOT, 3, 5))
                    .withDrop(new ItemChance(BNItemManager.getBNItem("marauder_bone"), 2, 60))
                    .withMainHandItem(new ItemChance(BNItemManager.getBNItem("marauder_sword"), 0.1f, true))
                    .withChestplate(new ItemChance(BNItemManager.getBNItem("marauder_chestplate"), 0.05f, true))
                    .withTargetEffect(new MobTargetEffect(50, new BNParticle(Particle.VILLAGER_ANGRY, 6),
                            new BNSound(Sound.ENTITY_WOLF_GROWL, SoundCategory.HOSTILE, 1, 0.4f),
                            Arrays.asList(new BNPotionEffect(PotionEffectType.SPEED, 15, 2, 20)),
                            Arrays.asList(new BNPotionEffect(PotionEffectType.BLINDNESS, 15, 2, 60)))));
        }
        if (BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED) {
            registerNewBNMob(new BNMob("netherite_marauder_brute",
                    EntityType.WITHER_SKELETON,
                    "<#571664>Netherite <#3c1a4c>Marauder Brute",
                    BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_SPAWNCHANCE)
                    .withEntityToReplace(EntityType.WITHER_SKELETON)
                    .withMount(new MountInfo(EntityType.ZOMBIE_HORSE, 40, Arrays.asList(Material.DIAMOND_HORSE_ARMOR, Material.IRON_HORSE_ARMOR), 20))
                    .withCustomDeathEXP(20)
                    .withCustomMaxHP(25)
                    .withCustomMoveSpeed(0.2f)
                    .withValidSpawnWorld(World.Environment.NETHER)
                    .withHitEffect(new BNPotionEffect(PotionEffectType.BLINDNESS, 30, 1, 60)).withDropToRemove(Material.BONE)
                    .withDropToRemove(Material.COAL)
                    .withDrop(new ItemChance(Material.NETHERITE_SCRAP, 2, 20))
                    .withDrop(new ItemChance(Material.NETHERITE_INGOT, 3, 5))
                    .withDrop(new ItemChance(BNItemManager.getBNItem("marauder_bone"), 2, 60))
                    .withMainHandItem(new ItemChance(BNItemManager.getBNItem("marauder_axe"), 0.1f, true))
                    .withChestplate(new ItemChance(BNItemManager.getBNItem("marauder_chestplate"), 0.05f, true))
                    .withTargetEffect(new MobTargetEffect(56, new BNParticle(Particle.VILLAGER_ANGRY, 6),
                            new BNSound(Sound.ENTITY_WOLF_GROWL, SoundCategory.HOSTILE, 1, 0.3f),
                            Arrays.asList(new BNPotionEffect(PotionEffectType.SPEED, 20, 2, 20)),
                            Arrays.asList(new BNPotionEffect(PotionEffectType.BLINDNESS, 20, 2, 60)))));
        }
        if (BetterConfig.CUSTOM_MOB_HELLHOUND_ENABLED) {
            registerNewBNMob(new BNMob("hellhound",
                    EntityType.WOLF,
                    "<#571664>Hellhound",
                    BetterConfig.CUSTOM_MOB_HELLHOUND_SPAWNCHANCE)
                    .withAnger(true)
                    .withEntityToReplace(EntityType.ZOMBIFIED_PIGLIN)
                    .withCustomDeathEXP(10)
                    .withCustomMaxHP(10)
                    .withCustomMoveSpeed(0.4f)
                    .withValidSpawnWorld(World.Environment.NETHER)
                    .withHitEffect(new BNPotionEffect(PotionEffectType.WITHER, 30, 1, 60))
                    .withDrop(new ItemChance(BNItemManager.getBNItem("rotten_hound_meat"), 3, 30))
                    .withDrop(new ItemChance(BNItemManager.getBNItem("hound_meat"), 3, 40))
                    .withBurningEffect(new BurningInfo(true, Integer.MAX_VALUE))
                    .withSpawnEffect(new BNPotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 1, Integer.MAX_VALUE))
                    .withTargetEffect(new MobTargetEffect(50, new BNParticle(Particle.FLAME, 6),
                            new BNSound(Sound.ENTITY_WOLF_GROWL, SoundCategory.HOSTILE, 1, 0.6f),
                            Arrays.asList(new BNPotionEffect(PotionEffectType.SPEED, 10, 2, 20)), null))
                    .setBaby(new BabyInfo(true, 30))
                    .withPossibleTarget(EntityType.PLAYER)
                    .withPossibleTarget(EntityType.SHEEP)
                    .withPossibleTarget(EntityType.COW));
        }
        if (BetterConfig.CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED) {
            registerNewBNMob(new BNMob("alpha_hellhound",
                    EntityType.WOLF,
                    "<#571664>Alpha Hellhound",
                    BetterConfig.CUSTOM_MOB_ALPHA_HELLHOUND_SPAWNCHANCE)
                    .withAnger(true)
                    .withEntityToReplace(EntityType.ZOMBIFIED_PIGLIN)
                    .withCustomDeathEXP(20)
                    .withCustomMaxHP(15)
                    .withCustomMoveSpeed(0.5f)
                    .withValidSpawnWorld(World.Environment.NETHER)
                    .withHitEffect(new BNPotionEffect(PotionEffectType.WITHER, 30, 1, 60))
                    .withDrop(new ItemChance(BNItemManager.getBNItem("rotten_hound_meat"), 4, 30))
                    .withDrop(new ItemChance(BNItemManager.getBNItem("hound_meat"), 4, 40))
                    .withBurningEffect(new BurningInfo(true, Integer.MAX_VALUE))
                    .withSpawnEffect(new BNPotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 1, Integer.MAX_VALUE))
                    .withTargetEffect(new MobTargetEffect(50, new BNParticle(Particle.FLAME, 6),
                            new BNSound(Sound.ENTITY_WOLF_GROWL, SoundCategory.HOSTILE, 1, 0.6f),
                            Arrays.asList(new BNPotionEffect(PotionEffectType.SPEED, 15, 2, 20)), null))
                    .withPossibleTarget(EntityType.PLAYER)
                    .withPossibleTarget(EntityType.SHEEP)
                    .withPossibleTarget(EntityType.COW));
        }
        if (BetterConfig.CUSTOM_MOB_LOST_SOUL_ENABLED) {
            registerNewBNMob(new BNMob("lost_soul",
                    EntityType.VEX,
                    "<#1152a6>Lost Soul",
                    BetterConfig.CUSTOM_MOB_LOST_SOUL_SPAWNCHANCE)
                    .withEntityToReplace(EntityType.ZOMBIFIED_PIGLIN)
                    .withCustomDeathEXP(30)
                    .withCustomMaxHP(5)
                    .withCustomMoveSpeed(0.5f)
                    .withValidSpawnWorld(World.Environment.NETHER)
                    .withHitEffect(new BNPotionEffect(PotionEffectType.WITHER, 30, 1, 60))
                    .withHitEffect(new BNPotionEffect(PotionEffectType.POISON, 70, 1, 60))
                    .withHitEffect(new BNPotionEffect(PotionEffectType.BLINDNESS, 50, 1, 60))
                    .withDeathExplosion(true, 90, 2, true)
                    .withDrop(new ItemChance(BNItemManager.getBNItem("soul"), 2, 5))
                    .withBurningEffect(new BurningInfo(true, Integer.MAX_VALUE))
                    .withSpawnEffect(new BNPotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 1, Integer.MAX_VALUE))
                    .withTargetEffect(new MobTargetEffect(50, new BNParticle(Particle.SOUL_FIRE_FLAME, 6),
                            new BNSound(Sound.ENTITY_VEX_CHARGE, SoundCategory.HOSTILE, 1, 0.6f),
                            Arrays.asList(new BNPotionEffect(PotionEffectType.SPEED, 10, 2, 20)), null)));
        }
        if (BetterConfig.CUSTOM_MOB_ZOMBIFIED_DEMON_ENABLED) {
            registerNewBNMob(new BNMob("zombified_demon",
                    EntityType.ZOMBIE,
                    "<#0f853c>Zombified Demon",
                    BetterConfig.CUSTOM_MOB_ZOMBIFIED_DEMON_SPAWNCHANCE)
                    .withEntityToReplace(EntityType.ZOMBIFIED_PIGLIN)
                    .withEntityToReplace(EntityType.ZOMBIE)
                    .withCustomDeathEXP(30)
                    .withCustomMaxHP(8)
                    .withCustomMoveSpeed(0.4f)
                    .withValidSpawnWorld(World.Environment.NETHER)
                    .withValidSpawnWorld(World.Environment.NORMAL)
                    .withHitEffect(new BNPotionEffect(PotionEffectType.HUNGER, 30, 1, 60))
                    .withHitEffect(new BNPotionEffect(PotionEffectType.POISON, 70, 1, 60))
                    .withDrop(new ItemChance(BNItemManager.getBNItem("rotten_demon_flesh"), 2, 5))
                    .withHelmet(new ItemChance(Material.IRON_HELMET, 0.3f, true))
                    .withTargetEffect(new MobTargetEffect(50, new BNParticle(Particle.CRIMSON_SPORE, 6),
                            new BNSound(Sound.ENTITY_ZOMBIE_AMBIENT, SoundCategory.HOSTILE, 1, 0.6f),
                            Arrays.asList(new BNPotionEffect(PotionEffectType.SPEED, 15, 2, 20)), null))
                    .setBaby(new BabyInfo(false, 0)));
        }
        if (BetterConfig.CUSTOM_MOB_TANK_ENABLED) {
            registerNewBNMob(new BNMob("demon_tank",
                    EntityType.HUSK,
                    "<#f1e46a>Demon Tank",
                    BetterConfig.CUSTOM_MOB_TANK_SPAWNCHANCE)
                    .withEntityToReplace(EntityType.HUSK)
                    .withEntityToReplace(EntityType.PIGLIN_BRUTE)
                    .withEntityToReplace(EntityType.ENDERMAN)
                    .withCustomDeathEXP(40)
                    .withCustomMaxHP(25)
                    .withCustomMoveSpeed(0.2f)
                    .withValidSpawnWorld(World.Environment.NORMAL)
                    .withValidSpawnWorld(World.Environment.NETHER)
                    .withValidSpawnWorld(World.Environment.THE_END)
                    .withHitEffect(new BNPotionEffect(PotionEffectType.POISON, 80, 1, 90))
                    .withMainHandItem(new ItemChance(BNItemManager.getBNItem("tank_sword"), 0.1f, true))
                    .withHelmet(new ItemChance(BNItemManager.getBNItem("tank_helmet"), 0.1f, true))
                    .withChestplate(new ItemChance(BNItemManager.getBNItem("tank_chestplate"), 0.1f, true))
                    .withLeggings(new ItemChance(BNItemManager.getBNItem("tank_leggings"), 0.1f, true))
                    .withBoots(new ItemChance(BNItemManager.getBNItem("tank_boots"), 0.1f, true))
                    .withDrop(new ItemChance(BNItemManager.getBNItem("demon_tank_totem"), 10))
                    .setBaby(new BabyInfo(false, 0))
                    .withTargetEffect(new MobTargetEffect(30, new BNParticle(Particle.SOUL, 6),
                            new BNSound(Sound.PARTICLE_SOUL_ESCAPE, SoundCategory.HOSTILE, 1, 0.3f),
                            Arrays.asList(new BNPotionEffect(PotionEffectType.SPEED, 20, 1, 7)),
                            Arrays.asList(new BNPotionEffect(PotionEffectType.BLINDNESS, 40, 1, 60)))));
        }
    }

    public static List<String> getValidMobList() {
        return new ArrayList<>(bnMobMap.keySet());
    }

    public static void addMobToSpawned(LivingEntity entity) {
        if (spawnedBNMobs.containsKey(entity.getUniqueId())) return;
        spawnedBNMobs.put(entity.getUniqueId(), entity);
    }

    public static void removeMobFromSpawned(LivingEntity entity) {
        if (!spawnedBNMobs.containsKey(entity.getUniqueId())) return;
        spawnedBNMobs.remove(entity.getUniqueId());
    }

    public static Map<UUID, LivingEntity> getSpawnedMobs() {
        return spawnedBNMobs;
    }
}
