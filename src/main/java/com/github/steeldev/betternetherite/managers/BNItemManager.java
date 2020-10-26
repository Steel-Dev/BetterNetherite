package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.listeners.baselisteners.CustomItemBase;
import com.github.steeldev.betternetherite.misc.BNItem;
import com.github.steeldev.betternetherite.util.items.*;
import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class BNItemManager {
    static BetterNetherite main = BetterNetherite.getInstance();
    static Map<String, BNItem> bnItemMap;

    public static void registerNewBNItem(BNItem item) {
        if (bnItemMap == null) bnItemMap = new HashMap<>();

        if (bnItemMap.containsKey(item.key)) return;

        bnItemMap.put(item.key, item);

        main.getServer().getPluginManager().registerEvents(new CustomItemBase(item.key), main);

        if (BetterConfig.DEBUG)
            main.getLogger().info(String.format("&aCustom item &ebetternetherite:%s&a has been &2registered.", item.key));
    }

    public static BNItem getBNItem(String key) {
        if (!bnItemMap.containsKey(key)) return null;

        return bnItemMap.get(key);
    }

    public static void registerCustomItems() {
        if (BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED || BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED) {
            registerNewBNItem(new BNItem("marauder_bone", Material.BONE)
                    .withDisplayName("<#3c1a4c>Marauder Bone")
                    .withLore("&7A Netherite Marauder's bone."));

            registerNewBNItem(new BNItem("marauder_sword", Material.NETHERITE_SWORD)
                    .withDisplayName("<#3c1a4c>Marauder Sword")
                    .setReinforced(true)
                    .withLore("&7A Netherite Marauder's sword.")
                    .withAttribute(new ItemAttributeInfo("generic.attack_damage", EquipmentSlot.HAND, Attribute.GENERIC_ATTACK_DAMAGE, 9))
                    .withAttribute(new ItemAttributeInfo("generic.attack_speed", EquipmentSlot.HAND, Attribute.GENERIC_ATTACK_SPEED, 1.7)));

            registerNewBNItem(new BNItem("marauder_chestplate", Material.NETHERITE_CHESTPLATE)
                    .withDisplayName("<#3c1a4c>Marauder Chestplate")
                    .setReinforced(true)
                    .withLore("&7A Netherite Marauder's chestplate.")
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.CHEST, Attribute.GENERIC_ARMOR, 9))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.CHEST, Attribute.GENERIC_ARMOR_TOUGHNESS, 3.5))
                    .withAttribute(new ItemAttributeInfo("generic.knockback_resistance", EquipmentSlot.CHEST, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 1.2)));

            if (BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED) {
                registerNewBNItem(new BNItem("netherite_marauder_spawn_egg", Material.ENDERMAN_SPAWN_EGG)
                        .withDisplayName("<#3c1a4c>Spawn Netherite Marauder")
                        .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "netherite_marauder")));
            }
        }
        if (BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED) {
            registerNewBNItem(new BNItem("marauder_axe", Material.NETHERITE_AXE)
                    .withDisplayName("<#3c1a4c>Marauder Axe")
                    .setReinforced(true)
                    .withLore("&7A Netherite Marauder's axe.")
                    .withAttribute(new ItemAttributeInfo("generic.attack_damage", EquipmentSlot.HAND, Attribute.GENERIC_ATTACK_DAMAGE, 11))
                    .withAttribute(new ItemAttributeInfo("generic.attack_speed", EquipmentSlot.HAND, Attribute.GENERIC_ATTACK_SPEED, 1.8)));

            registerNewBNItem(new BNItem("netherite_marauder_brute_spawn_egg", Material.ENDERMITE_SPAWN_EGG)
                    .withDisplayName("<#3c1a4c>Spawn Netherite Marauder Brute")
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "netherite_marauder_brute")));
        }
        if (BetterConfig.CUSTOM_MOB_HELLHOUND_ENABLED || BetterConfig.CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED) {
            registerNewBNItem(new BNItem("hound_meat", Material.PORKCHOP)
                    .withDisplayName("<#915103>Raw Hound Meat"));

            registerNewBNItem(new BNItem("cooked_hound_meat", Material.COOKED_PORKCHOP)
                    .withDisplayName("<#915103>Cooked Hound Meat")
                    .withConsumeEffect(new ItemConsumeEffect("&bHounds Grace",
                            Arrays.asList(new BNPotionEffect(PotionEffectType.ABSORPTION, 12, 2, 2000),
                                    new BNPotionEffect(PotionEffectType.REGENERATION, 25, 2, 2000)))));

            registerNewBNItem(new BNItem("rotten_hound_meat", Material.ROTTEN_FLESH)
                    .withDisplayName("<#2d2501>Rotted Hound Meat")
                    .withConsumeEffect(new ItemConsumeEffect("&cFood Poisoning",
                            Arrays.asList(new BNPotionEffect(PotionEffectType.POISON, 50, 1, 2000),
                                    new BNPotionEffect(PotionEffectType.CONFUSION, 50, 1, 2000)))));

            if (BetterConfig.CUSTOM_MOB_HELLHOUND_ENABLED) {
                registerNewBNItem(new BNItem("hellhound_spawn_egg", Material.GHAST_SPAWN_EGG)
                        .withDisplayName("<#571664>Spawn Hellhound")
                        .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "hellhound")));
            }

            if (BetterConfig.CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED) {
                registerNewBNItem(new BNItem("alpha_hellhound_spawn_egg", Material.GHAST_SPAWN_EGG)
                        .withDisplayName("<#571664>Spawn Alpha Hellhound")
                        .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "alpha_hellhound")));
            }
        }
        if (BetterConfig.CUSTOM_MOB_LOST_SOUL_ENABLED) {
            registerNewBNItem(new BNItem("soul", Material.GHAST_TEAR)
                    .withDisplayName("<#1152a6>Soul")
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.EFFECT_HOLDER,
                            Collections.singletonList(new BNPotionEffect(PotionEffectType.REGENERATION, 90, 2, 2000)), true)));

            registerNewBNItem(new BNItem("lost_soul_spawn_egg", Material.SHEEP_SPAWN_EGG)
                    .withDisplayName("<#1152a6>Spawn Lost Soul")
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "lost_soul")));
        }
        if (BetterConfig.CUSTOM_MOB_ZOMBIFIED_DEMON_ENABLED) {
            registerNewBNItem(new BNItem("rotten_demon_flesh", Material.ROTTEN_FLESH)
                    .withDisplayName("<#2d2501>Rotten Demon Flesh")
                    .withConsumeEffect(new ItemConsumeEffect("&cFood Poisoning",
                            Arrays.asList(new BNPotionEffect(PotionEffectType.POISON, 50, 1, 2000),
                                    new BNPotionEffect(PotionEffectType.CONFUSION, 50, 1, 2000)))));

            registerNewBNItem(new BNItem("zombified_demon_spawn_egg", Material.ZOMBIE_HORSE_SPAWN_EGG)
                    .withDisplayName("<#2d2501>Spawn Zombified Demon")
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "zombified_demon")));
        }
        if (BetterConfig.CUSTOM_MOB_TANK_ENABLED) {
            registerNewBNItem(new BNItem("tank_sword", Material.STONE_SWORD)
                    .withDisplayName("<#f1e46a>Tenonus")
                    .withEnchant(new ItemEnchantInfo(Enchantment.DAMAGE_ALL, 1)));

            registerNewBNItem(new BNItem("tank_helmet", Material.LEATHER_HELMET)
                    .withDisplayName("<#f1e46a>Tanks Helmet")
                    .withColor(Color.RED)
                    .withLore("&7The helmet of a Tank.")
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR, 1.5))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR_TOUGHNESS, 1))
                    .withAttribute(new ItemAttributeInfo("generic.knockback_resistance", EquipmentSlot.HEAD, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 0.2)));

            registerNewBNItem(new BNItem("tank_chestplate", Material.LEATHER_CHESTPLATE)
                    .withDisplayName("<#f1e46a>Tanks Chestplate")
                    .withColor(Color.GRAY)
                    .withLore("&7The chestplate of a Tank.")
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR, 3.5))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR_TOUGHNESS, 1.2))
                    .withAttribute(new ItemAttributeInfo("generic.knockback_resistance", EquipmentSlot.HEAD, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 0.3)));

            registerNewBNItem(new BNItem("tank_leggings", Material.LEATHER_LEGGINGS)
                    .withDisplayName("<#f1e46a>Tanks Leggings")
                    .withColor(Color.GRAY)
                    .withLore("&7The leggings of a Tank.")
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR, 2.5))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR_TOUGHNESS, 0.6))
                    .withAttribute(new ItemAttributeInfo("generic.knockback_resistance", EquipmentSlot.HEAD, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 0.2)));

            registerNewBNItem(new BNItem("tank_boots", Material.LEATHER_BOOTS)
                    .withDisplayName("<#f1e46a>Tanks Boots")
                    .withColor(Color.BLACK)
                    .withLore("&7The boots of a Tank.")
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR, 1.2))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR_TOUGHNESS, 0.2))
                    .withAttribute(new ItemAttributeInfo("generic.knockback_resistance", EquipmentSlot.HEAD, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 0.1)));

            registerNewBNItem(new BNItem("demon_tank_spawn_egg", Material.HUSK_SPAWN_EGG)
                    .withDisplayName("<#f1e46a>Spawn Demon Tank")
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "demon_tank")));

            registerNewBNItem(new BNItem("demon_tank_totem", Material.TOTEM_OF_UNDYING)
                    .withDisplayName("&6Tank Totem")
                    .withLore("&7A special rare totem that can be used as a revival token")
                    .withLore("&7or for limited special effects.")
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.EFFECT_HOLDER, Arrays.asList(new BNPotionEffect(PotionEffectType.ABSORPTION, 12, 2, 3000),
                            new BNPotionEffect(PotionEffectType.REGENERATION, 25, 2, 3000),
                            new BNPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 25, 2, 3000)), true)));
        }
    }

    public static List<String> getValidItemList() {
        return new ArrayList<>(bnItemMap.keySet());
    }
}
