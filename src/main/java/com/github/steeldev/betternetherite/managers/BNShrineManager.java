package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.listeners.baselisteners.ShrineBase;
import com.github.steeldev.betternetherite.misc.BNShrine;
import com.github.steeldev.betternetherite.util.shrines.*;
import org.bukkit.*;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class BNShrineManager {
    static BetterNetherite main = BetterNetherite.getInstance();
    static Map<String, BNShrine> bnShrineMap;

    public static void registerNewShrine(BNShrine shrine) {
        if (bnShrineMap == null) bnShrineMap = new HashMap<>();

        if (bnShrineMap.containsKey(shrine.key)) return;

        bnShrineMap.put(shrine.key, shrine);

        main.getServer().getPluginManager().registerEvents(new ShrineBase(shrine.key), main);

        if (BetterConfig.DEBUG)
            main.getLogger().info(String.format("&aCustom shrine &ebetternetherite:%s&a has been &2registered.", shrine.key));
    }

    public static BNShrine getBNShrine(String key) {
        if (!bnShrineMap.containsKey(key)) return null;

        return bnShrineMap.get(key);
    }

    public static void registerShrines() {
        if (BetterConfig.CRIMSON_NETHERITE_SHRINE_ENABLED) {
            registerNewShrine(new BNShrine("crimson_shrine",
                    "<#aa1313>Crimson <#560a57>Shrine",
                    new ShrineCore(Material.CRIMSON_STEM,
                            Material.CRIMSON_FENCE),
                    new ShrineCharge(Sound.BLOCK_REDSTONE_TORCH_BURNOUT,
                            Material.SOUL_LANTERN,
                            Particle.FLAME),
                    new ShrineEffect(ShrineEffectType.REINFORCE_ITEM,
                            Sound.BLOCK_ANVIL_USE,
                            Sound.BLOCK_ANVIL_BREAK,
                            Sound.BLOCK_BEACON_DEACTIVATE,
                            Effect.ZOMBIE_DESTROY_DOOR,
                            Particle.EXPLOSION_NORMAL,
                            "<#600b0b>Hells Reinforcement",
                            "<#11349c>REINFORCED",
                            "<#11349c>Reinforced",
                            Particle.ENCHANTMENT_TABLE),
                    Arrays.asList(World.Environment.NORMAL, World.Environment.NETHER),
                    40,
                    true));
        }
        if (BetterConfig.WARPED_NETHERITE_SHRINE_ENABLED) {
            registerNewShrine(new BNShrine("warped_shrine",
                    "<#119c95>Warped <#560a57>Shrine",
                    new ShrineCore(Material.WARPED_STEM,
                            Material.WARPED_FENCE),
                    new ShrineCharge(Sound.BLOCK_REDSTONE_TORCH_BURNOUT,
                            Material.SOUL_LANTERN,
                            Particle.FLAME),
                    new ShrineEffect(ShrineEffectType.HEAL_ITEM,
                            Sound.BLOCK_ANVIL_USE,
                            Sound.BLOCK_ANVIL_BREAK,
                            Sound.BLOCK_BEACON_DEACTIVATE,
                            Effect.ZOMBIE_DESTROY_DOOR,
                            Particle.EXPLOSION_NORMAL,
                            "<#600b0b>Hells Mend",
                            "<#11349c>Mended",
                            Particle.ENCHANTMENT_TABLE),
                    Arrays.asList(World.Environment.NORMAL, World.Environment.NETHER),
                    40,
                    true));
        }
        if (BetterConfig.PRISMARINE_NETHERITE_SHRINE_ENABLED) {
            registerNewShrine(new BNShrine("prismarine_shrine",
                    "<#1297a1>Prismarine <#560a57>Shrine",
                    new ShrineCore(Material.PRISMARINE,
                            Material.PRISMARINE_WALL),
                    new ShrineCharge(Sound.BLOCK_REDSTONE_TORCH_BURNOUT,
                            Material.SOUL_TORCH,
                            Particle.FLAME),
                    new ShrineEffect(ShrineEffectType.APPLY_POTION_EFFECT,
                            Sound.BLOCK_BEACON_ACTIVATE,
                            Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE,
                            Sound.BLOCK_BEACON_DEACTIVATE,
                            Effect.ZOMBIE_DESTROY_DOOR,
                            Particle.EXPLOSION_NORMAL,
                            "<#1297a1>Heavens Grace",
                            "<#11349c>Graced",
                            Particle.ENCHANTMENT_TABLE,
                            Arrays.asList(new BNPotionEffect(PotionEffectType.REGENERATION, 30, 1, 3000),
                                    new BNPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 2, 3000),
                                    new BNPotionEffect(PotionEffectType.FAST_DIGGING, 16, 2, 3000),
                                    new BNPotionEffect(PotionEffectType.HEALTH_BOOST, 23, 3, 3000),
                                    new BNPotionEffect(PotionEffectType.FIRE_RESISTANCE, 21, 2, 3000))),
                    Collections.singletonList(World.Environment.NORMAL),
                    80,
                    false));
        }
    }

    public static List<String> getValidShrineList() {
        return new ArrayList<>(bnShrineMap.keySet());
    }
}
