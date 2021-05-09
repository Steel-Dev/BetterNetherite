package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.monstrorvm.managers.ItemManager;
import com.github.steeldev.monstrorvm.managers.MobManager;
import com.github.steeldev.monstrorvm.util.misc.MVParticle;
import com.github.steeldev.monstrorvm.util.misc.MVPotionEffect;
import com.github.steeldev.monstrorvm.util.misc.MVSound;
import com.github.steeldev.monstrorvm.util.mobs.*;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collections;

public class BNMobManager {
    static BetterNetherite main = BetterNetherite.getInstance();

    public static void registerCustomMobs() {
        if (main.config.CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED) {
            MobManager.registerNewMob(new MVMob("netherite_marauder",
                    EntityType.WITHER_SKELETON,
                    "<#571664>Netherite <#3c1a4c>Marauder",
                    main.config.CUSTOM_MOB_NETHERITE_MARAUDER_SPAWNCHANCE)
                    .withEntityToReplace(EntityType.WITHER_SKELETON)
                    .withMount(new MountInfo(EntityType.SKELETON_HORSE, 40, Arrays.asList(Material.IRON_HORSE_ARMOR, Material.GOLDEN_HORSE_ARMOR), 20))
                    .withCustomDeathEXP(Arrays.asList(5, 10, 15, 20))
                    .withCustomMaxHP(Arrays.asList(10.0, 15.0, 20.0, 25.0))
                    .withCustomMoveSpeed(0.2f)
                    .withValidSpawnWorlds(main.config.CUSTOM_MOB_NETHERITE_MARAUDER_SPAWN_WORLDS)
                    .withHitEffect(new MVPotionEffect(PotionEffectType.BLINDNESS, 30, 1, 60))
                    .withDropToRemove(Material.BONE)
                    .withDropToRemove(Material.COAL)
                    .withDrop(new ItemChance(Material.NETHERITE_SCRAP, 2, 20))
                    .withDrop(new ItemChance(Material.NETHERITE_INGOT, 3, 5))
                    .withDrop(new ItemChance(ItemManager.getItem("marauder_bone"), 2, 60))
                    .withMainHandItem(new ItemChance(ItemManager.getItem("marauder_sword"), 0.1f, true))
                    .withChestplate(new ItemChance(ItemManager.getItem("marauder_chestplate"), 0.05f, true))
                    .withTargetEffect(new MobTargetEffect(50, new MVParticle(Particle.VILLAGER_ANGRY, 6),
                            new MVSound(Sound.ENTITY_WOLF_GROWL, SoundCategory.HOSTILE, 1, 0.4f),
                            Collections.singletonList(new MVPotionEffect(PotionEffectType.SPEED, 15, 2, 20)),
                            Collections.singletonList(new MVPotionEffect(PotionEffectType.BLINDNESS, 15, 2, 60)))), main);
        }
        if (main.config.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED) {
            MobManager.registerNewMob(new MVMob("netherite_marauder_brute",
                    EntityType.WITHER_SKELETON,
                    "<#571664>Netherite <#3c1a4c>Marauder Brute",
                    main.config.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_SPAWNCHANCE)
                    .withEntityToReplace(EntityType.WITHER_SKELETON)
                    .withMount(new MountInfo(EntityType.ZOMBIE_HORSE, 40, Arrays.asList(Material.DIAMOND_HORSE_ARMOR, Material.IRON_HORSE_ARMOR), 20))
                    .withCustomDeathEXP(Arrays.asList(10, 15, 20, 25))
                    .withCustomMaxHP(Arrays.asList(15.0, 20.0, 25.0, 30.0))
                    .withCustomMoveSpeed(0.2f)
                    .withValidSpawnWorlds(main.config.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_SPAWN_WORLDS)
                    .withHitEffect(new MVPotionEffect(PotionEffectType.BLINDNESS, 30, 1, 60)).withDropToRemove(Material.BONE)
                    .withDropToRemove(Material.COAL)
                    .withDrop(new ItemChance(Material.NETHERITE_SCRAP, 2, 20))
                    .withDrop(new ItemChance(Material.NETHERITE_INGOT, 3, 5))
                    .withDrop(new ItemChance(ItemManager.getItem("marauder_bone"), 2, 60))
                    .withMainHandItem(new ItemChance(ItemManager.getItem("marauder_axe"), 0.1f, true))
                    .withChestplate(new ItemChance(ItemManager.getItem("marauder_chestplate"), 0.05f, true))
                    .withTargetEffect(new MobTargetEffect(56, new MVParticle(Particle.VILLAGER_ANGRY, 6),
                            new MVSound(Sound.ENTITY_WOLF_GROWL, SoundCategory.HOSTILE, 1, 0.3f),
                            Collections.singletonList(new MVPotionEffect(PotionEffectType.SPEED, 20, 2, 20)),
                            Collections.singletonList(new MVPotionEffect(PotionEffectType.BLINDNESS, 20, 2, 60)))), main);
        }
        if (main.config.CUSTOM_MOB_HELLHOUND_ENABLED) {
            MobManager.registerNewMob(new MVMob("hellhound",
                    EntityType.WOLF,
                    "<#571664>Hellhound",
                    main.config.CUSTOM_MOB_HELLHOUND_SPAWNCHANCE)
                    .withAnger(true)
                    .withEntityToReplace(EntityType.ZOMBIFIED_PIGLIN)
                    .withCustomDeathEXP(Arrays.asList(2, 5, 7, 10))
                    .withCustomMaxHP(Arrays.asList(2.0, 5.0, 7.0, 10.0))
                    .withCustomMoveSpeed(0.4f)
                    .withValidSpawnWorlds(main.config.CUSTOM_MOB_ALPHA_HELLHOUND_SPAWN_WORLDS)
                    .withHitEffect(new MVPotionEffect(PotionEffectType.WITHER, 30, 1, 60))
                    .withDrop(new ItemChance(ItemManager.getItem("rotten_hound_meat"), 3, 30))
                    .withDrop(new ItemChance(ItemManager.getItem("hound_meat"), 3, 40))
                    .withBurningEffect(new BurningInfo(true, Integer.MAX_VALUE))
                    .withSpawnEffect(new MVPotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 1, Integer.MAX_VALUE))
                    .withTargetEffect(new MobTargetEffect(50, new MVParticle(Particle.FLAME, 6),
                            new MVSound(Sound.ENTITY_WOLF_GROWL, SoundCategory.HOSTILE, 1, 0.6f),
                            Collections.singletonList(new MVPotionEffect(PotionEffectType.SPEED, 10, 2, 20)), null))
                    .setBaby(new BabyInfo(true, 30))
                    .withPossibleTarget(EntityType.PLAYER)
                    .withPossibleTarget(EntityType.SHEEP)
                    .withPossibleTarget(EntityType.COW), main);
        }
        if (main.config.CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED) {
            MobManager.registerNewMob(new MVMob("alpha_hellhound",
                    EntityType.WOLF,
                    "<#571664>Alpha Hellhound",
                    main.config.CUSTOM_MOB_ALPHA_HELLHOUND_SPAWNCHANCE)
                    .withAnger(true)
                    .withEntityToReplace(EntityType.ZOMBIFIED_PIGLIN)
                    .withCustomDeathEXP(Arrays.asList(4, 6, 9, 15))
                    .withCustomMaxHP(Arrays.asList(5.0, 8.0, 10.0, 15.0))
                    .withCustomMoveSpeed(0.5f)
                    .withValidSpawnWorlds(main.config.CUSTOM_MOB_ALPHA_HELLHOUND_SPAWN_WORLDS)
                    .withHitEffect(new MVPotionEffect(PotionEffectType.WITHER, 30, 1, 60))
                    .withDrop(new ItemChance(ItemManager.getItem("rotten_hound_meat"), 4, 30))
                    .withDrop(new ItemChance(ItemManager.getItem("hound_meat"), 4, 40))
                    .withBurningEffect(new BurningInfo(true, Integer.MAX_VALUE))
                    .withSpawnEffect(new MVPotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 1, Integer.MAX_VALUE))
                    .withTargetEffect(new MobTargetEffect(50, new MVParticle(Particle.FLAME, 6),
                            new MVSound(Sound.ENTITY_WOLF_GROWL, SoundCategory.HOSTILE, 1, 0.6f),
                            Collections.singletonList(new MVPotionEffect(PotionEffectType.SPEED, 15, 2, 20)), null))
                    .withPossibleTarget(EntityType.PLAYER)
                    .withPossibleTarget(EntityType.SHEEP)
                    .withPossibleTarget(EntityType.COW)
                    .withPack(new PackInfo(3, 20, "monstrorvm:hellhound")), main);
        }
        if (main.config.CUSTOM_MOB_LOST_SOUL_ENABLED) {
            MobManager.registerNewMob(new MVMob("lost_soul",
                    EntityType.VEX,
                    "<#1152a6>Lost Soul",
                    main.config.CUSTOM_MOB_LOST_SOUL_SPAWNCHANCE)
                    .withEntityToReplace(EntityType.ZOMBIFIED_PIGLIN)
                    .withCustomDeathEXP(Arrays.asList(2, 5, 7, 8))
                    .withCustomMaxHP(Arrays.asList(2.0, 5.0, 7.0, 9.0))
                    .withCustomMoveSpeed(0.5f)
                    .withValidSpawnWorlds(main.config.CUSTOM_MOB_LOST_SOUL_SPAWN_WORLDS)
                    .withHitEffect(new MVPotionEffect(PotionEffectType.WITHER, 30, 1, 60))
                    .withHitEffect(new MVPotionEffect(PotionEffectType.POISON, 70, 1, 60))
                    .withHitEffect(new MVPotionEffect(PotionEffectType.BLINDNESS, 50, 1, 60))
                    .withDeathExplosion(true, 90, 2, true)
                    .withDrop(new ItemChance(ItemManager.getItem("soul"), 2, 5))
                    .withTargetEffect(new MobTargetEffect(50, new MVParticle(Particle.SOUL_FIRE_FLAME, 6),
                            new MVSound(Sound.ENTITY_VEX_CHARGE, SoundCategory.HOSTILE, 1, 0.6f),
                            Collections.singletonList(new MVPotionEffect(PotionEffectType.SPEED, 10, 2, 20)), null)), main);
        }
        if (main.config.CUSTOM_MOB_ZOMBIFIED_DEMON_ENABLED) {
            MobManager.registerNewMob(new MVMob("zombified_demon",
                    EntityType.ZOMBIE,
                    "<#0f853c>Zombified Demon",
                    main.config.CUSTOM_MOB_ZOMBIFIED_DEMON_SPAWNCHANCE)
                    .withEntityToReplace(EntityType.ZOMBIFIED_PIGLIN)
                    .withEntityToReplace(EntityType.ZOMBIE)
                    .withCustomDeathEXP(Arrays.asList(4, 6, 10, 20))
                    .withCustomMaxHP(Arrays.asList(2.0, 5.0, 8.0, 10.0))
                    .withCustomMoveSpeed(0.4f)
                    .withValidSpawnWorlds(main.config.CUSTOM_MOB_ZOMBIFIED_DEMON_SPAWN_WORLDS)
                    .withHitEffect(new MVPotionEffect(PotionEffectType.HUNGER, 30, 1, 60))
                    .withHitEffect(new MVPotionEffect(PotionEffectType.POISON, 70, 1, 60))
                    .withDrop(new ItemChance(ItemManager.getItem("rotten_demon_flesh"), 2, 5))
                    .withHelmet(new ItemChance(ItemManager.getItem("demon_head"), 0.05f, false))
                    .withChestplate(new ItemChance(ItemManager.getItem("demon_chestplate"), 0.03f, true))
                    .withLeggings(new ItemChance(ItemManager.getItem("demon_leggings"), 0.03f, true))
                    .withBoots(new ItemChance(ItemManager.getItem("demon_boots"), 0.03f, true))
                    .withBurningEffect(new BurningInfo(true, Integer.MAX_VALUE))
                    .withSpawnEffect(new MVPotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 1, Integer.MAX_VALUE))
                    .withTargetEffect(new MobTargetEffect(50, new MVParticle(Particle.CRIMSON_SPORE, 6),
                            new MVSound(Sound.ENTITY_ZOMBIE_AMBIENT, SoundCategory.HOSTILE, 1, 0.6f),
                            Collections.singletonList(new MVPotionEffect(PotionEffectType.SPEED, 15, 2, 20)), null))
                    .setBaby(new BabyInfo(false, 0)), main);
        }
        if (main.config.CUSTOM_MOB_TANK_ENABLED) {
            MobManager.registerNewMob(new MVMob("demon_tank",
                    EntityType.HUSK,
                    "<#f1e46a>Demon Tank",
                    main.config.CUSTOM_MOB_TANK_SPAWNCHANCE)
                    .withEntityToReplace(EntityType.HUSK)
                    .withEntityToReplace(EntityType.PIGLIN_BRUTE)
                    .withEntityToReplace(EntityType.ENDERMAN)
                    .withCustomDeathEXP(Arrays.asList(5, 8, 10, 20))
                    .withCustomMaxHP(Arrays.asList(10.0, 12.0, 17.0, 25.0))
                    .withCustomMoveSpeed(0.2f)
                    .withValidSpawnWorlds(main.config.CUSTOM_MOB_TANK_SPAWN_WORLDS)
                    .withHitEffect(new MVPotionEffect(PotionEffectType.POISON, 80, 1, 90))
                    .withMainHandItem(new ItemChance(ItemManager.getItem("tank_sword"), 0.1f, true))
                    .withHelmet(new ItemChance(ItemManager.getItem("tank_head"), 0.1f, false))
                    .withChestplate(new ItemChance(ItemManager.getItem("tank_chestplate"), 0.1f, true))
                    .withLeggings(new ItemChance(ItemManager.getItem("tank_leggings"), 0.1f, true))
                    .withBoots(new ItemChance(ItemManager.getItem("tank_boots"), 0.1f, true))
                    .withDrop(new ItemChance(ItemManager.getItem("demon_tank_totem"), 10))
                    .setBaby(new BabyInfo(false, 0))
                    .withTargetEffect(new MobTargetEffect(30, new MVParticle(Particle.SOUL, 6),
                            new MVSound(Sound.PARTICLE_SOUL_ESCAPE, SoundCategory.HOSTILE, 1, 0.3f),
                            Collections.singletonList(new MVPotionEffect(PotionEffectType.SPEED, 20, 1, 7)),
                            Collections.singletonList(new MVPotionEffect(PotionEffectType.BLINDNESS, 40, 1, 60)))), main);
        }
    }
}
