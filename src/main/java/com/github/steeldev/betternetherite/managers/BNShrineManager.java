package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.listeners.baselisteners.ShrineBase;
import com.github.steeldev.betternetherite.misc.BNShrine;
import com.github.steeldev.betternetherite.util.shrines.*;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static com.github.steeldev.betternetherite.util.Util.main;

public class BNShrineManager {
    static Map<String, BNShrine> bnShrineMap;

    public static void registerNewShrine(BNShrine shrine) {
        if (bnShrineMap == null) bnShrineMap = new HashMap<>();

        if (bnShrineMap.containsKey(shrine.key)) bnShrineMap.replace(shrine.key, shrine);
        else bnShrineMap.put(shrine.key, shrine);

        if(shrine.eventListener == null) {
            ShrineBase listener = new ShrineBase(shrine);
            main.getServer().getPluginManager().registerEvents(listener, main);
            shrine.eventListener = listener;
        }else{
            shrine.eventListener.updateShrine(shrine);
        }

        if (main.config.DEBUG)
            main.getLogger().info(String.format("&aCustom shrine &ebetternetherite:%s&a has been &2registered.", shrine.key));
    }

    public static BNShrine getBNShrine(String key) {
        if (!bnShrineMap.containsKey(key)) return null;

        return bnShrineMap.get(key);
    }

    public static void registerShrines() {
        if (main.config.CRIMSON_NETHERITE_SHRINE_ENABLED) {
            registerNewShrine(new BNShrine("crimson_shrine",
                    "<#aa1313>Crimson <#560a57>Shrine",
                    new ShrineCore(Material.CRIMSON_STEM,
                            Material.CRIMSON_FENCE),
                    new ShrineCharge(Sound.BLOCK_REDSTONE_TORCH_BURNOUT,
                            Material.SOUL_LANTERN,
                            Particle.FLAME))
                    .withCustomEffect(new ShrineEffect(ShrineEffectType.REINFORCE_ITEM,
                            Sound.BLOCK_ANVIL_USE,
                            Sound.BLOCK_ANVIL_BREAK,
                            Sound.BLOCK_BEACON_DEACTIVATE,
                            Effect.ZOMBIE_DESTROY_DOOR,
                            Particle.EXPLOSION_NORMAL,
                            "<#600b0b>Hells Reinforcement",
                            "<#11349c>REINFORCED",
                            "<#11349c>Reinforced",
                            Particle.ENCHANTMENT_TABLE))
                    .withValidWorlds(main.config.CRIMSON_NETHERITE_SHRINE_USABLE_IN)
                    .withExplodeChance(40)
                    .withItemRequirement(true));
        }
        if (main.config.WARPED_NETHERITE_SHRINE_ENABLED) {
            registerNewShrine(new BNShrine("warped_shrine",
                    "<#119c95>Warped <#560a57>Shrine",
                    new ShrineCore(Material.WARPED_STEM,
                            Material.WARPED_FENCE),
                    new ShrineCharge(Sound.BLOCK_REDSTONE_TORCH_BURNOUT,
                            Material.SOUL_LANTERN,
                            Particle.FLAME))
                    .withCustomEffect(new ShrineEffect(ShrineEffectType.HEAL_ITEM,
                            Sound.BLOCK_ANVIL_USE,
                            Sound.BLOCK_ANVIL_BREAK,
                            Sound.BLOCK_BEACON_DEACTIVATE,
                            Effect.ZOMBIE_DESTROY_DOOR,
                            Particle.EXPLOSION_NORMAL,
                            "<#600b0b>Hells Mend",
                            "<#11349c>Mended",
                            Particle.ENCHANTMENT_TABLE))
                    .withValidWorlds(main.config.WARPED_NETHERITE_SHRINE_USABLE_IN)
                    .withExplodeChance(40)
                    .withItemRequirement(true));
        }
        if (main.config.PRISMARINE_NETHERITE_SHRINE_ENABLED) {
            registerNewShrine(new BNShrine("prismarine_shrine",
                    "<#1297a1>Prismarine <#560a57>Shrine",
                    new ShrineCore(Material.PRISMARINE,
                            Material.PRISMARINE_WALL),
                    new ShrineCharge(Sound.BLOCK_REDSTONE_TORCH_BURNOUT,
                            Material.SOUL_TORCH,
                            Particle.FLAME))
                    .withCustomEffect(new ShrineEffect(ShrineEffectType.APPLY_POTION_EFFECT,
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
                                    new BNPotionEffect(PotionEffectType.FIRE_RESISTANCE, 21, 2, 3000))))
                    .withValidWorlds(main.config.PRISMARINE_NETHERITE_SHRINE_USABLE_IN)
                    .withExplodeChance(80)
                    .withItemRequirement(false));
        }
    }

    public static List<String> getValidShrineList() {
        return new ArrayList<>(bnShrineMap.keySet());
    }
}
