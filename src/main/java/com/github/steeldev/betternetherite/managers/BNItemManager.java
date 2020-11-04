package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.monstrorvm.managers.ItemManager;
import com.github.steeldev.monstrorvm.util.items.*;
import com.github.steeldev.monstrorvm.util.misc.MVPotionEffect;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collections;

public class BNItemManager {
    static BetterNetherite main = BetterNetherite.getInstance();

    public static void registerCustomItems() {
        if (BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED || BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED) {
            ItemManager.registerNewItem(new MVItem("marauder_bone", Material.BONE)
                    .withDisplayName("<#3c1a4c>Marauder Bone")
                    .withCustomModelData(1)
                    .withLore("&7A Netherite Marauder's bone."), main);

            ItemManager.registerNewItem(new MVItem("marauder_sword", Material.NETHERITE_SWORD)
                    .withDisplayName("<#3c1a4c>Marauder Sword")
                    .withNBT(new ItemNBTCompound("netherite_reinforced", true))
                    .withLore(BNShrineManager.getBNShrine("crimson_shrine").effect.effectLoreDisplay)
                    .withCustomModelData(1)
                    .withLore("&7A Netherite Marauder's sword.")
                    .withAttribute(new ItemAttributeInfo("generic.attack_damage", EquipmentSlot.HAND, Attribute.GENERIC_ATTACK_DAMAGE, 9))
                    .withAttribute(new ItemAttributeInfo("generic.attack_speed", EquipmentSlot.HAND, Attribute.GENERIC_ATTACK_SPEED, 1.7)), main);

            ItemManager.registerNewItem(new MVItem("marauder_chestplate", Material.NETHERITE_CHESTPLATE)
                    .withDisplayName("<#3c1a4c>Marauder Chestplate")
                    .withNBT(new ItemNBTCompound("netherite_reinforced", true))
                    .withLore(BNShrineManager.getBNShrine("crimson_shrine").effect.effectLoreDisplay)
                    .withLore("&7A Netherite Marauder's chestplate.")
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.CHEST, Attribute.GENERIC_ARMOR, 9))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.CHEST, Attribute.GENERIC_ARMOR_TOUGHNESS, 3.5))
                    .withAttribute(new ItemAttributeInfo("generic.knockback_resistance", EquipmentSlot.CHEST, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 1.2)), main);

            if (BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED) {
                ItemManager.registerNewItem(new MVItem("netherite_marauder_spawn_egg", Material.GHAST_SPAWN_EGG)
                        .withDisplayName("<#3c1a4c>Spawn Netherite Marauder")
                        .withCustomModelData(1)
                        .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "netherite_marauder")), main);
            }
        }
        if (BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED) {
            ItemManager.registerNewItem(new MVItem("marauder_axe", Material.NETHERITE_AXE)
                    .withDisplayName("<#3c1a4c>Marauder Axe")
                    .withNBT(new ItemNBTCompound("netherite_reinforced", true))
                    .withLore(BNShrineManager.getBNShrine("crimson_shrine").effect.effectLoreDisplay)
                    .withCustomModelData(1)
                    .withLore("&7A Netherite Marauder's axe.")
                    .withAttribute(new ItemAttributeInfo("generic.attack_damage", EquipmentSlot.HAND, Attribute.GENERIC_ATTACK_DAMAGE, 11))
                    .withAttribute(new ItemAttributeInfo("generic.attack_speed", EquipmentSlot.HAND, Attribute.GENERIC_ATTACK_SPEED, 1.8)), main);

            ItemManager.registerNewItem(new MVItem("netherite_marauder_brute_spawn_egg", Material.GHAST_SPAWN_EGG)
                    .withDisplayName("<#3c1a4c>Spawn Netherite Marauder Brute")
                    .withCustomModelData(2)
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "netherite_marauder_brute")), main);
        }
        if (BetterConfig.CUSTOM_MOB_HELLHOUND_ENABLED || BetterConfig.CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED) {
            ItemManager.registerNewItem(new MVItem("hound_meat", Material.PORKCHOP)
                    .withCustomModelData(1)
                    .withDisplayName("<#915103>Raw Hound Meat"), main);

            ItemManager.registerNewItem(new MVItem("cooked_hound_meat", Material.COOKED_PORKCHOP)
                    .withDisplayName("<#915103>Cooked Hound Meat")
                    .withCustomModelData(1)
                    .withConsumeEffect(new ItemConsumeEffect("&bHounds Grace",
                            Arrays.asList(new MVPotionEffect(PotionEffectType.ABSORPTION, 12, 2, 2000),
                                    new MVPotionEffect(PotionEffectType.REGENERATION, 25, 2, 2000)), 7)), main);

            ItemManager.registerNewItem(new MVItem("rotten_hound_meat", Material.ROTTEN_FLESH)
                    .withDisplayName("<#2d2501>Rotted Hound Meat")
                    .withCustomModelData(1)
                    .withConsumeEffect(new ItemConsumeEffect("&cFood Poisoning",
                            Arrays.asList(new MVPotionEffect(PotionEffectType.POISON, 50, 1, 2000),
                                    new MVPotionEffect(PotionEffectType.CONFUSION, 50, 1, 2000)), 2)), main);

            if (BetterConfig.CUSTOM_MOB_HELLHOUND_ENABLED) {
                ItemManager.registerNewItem(new MVItem("hellhound_spawn_egg", Material.GHAST_SPAWN_EGG)
                        .withDisplayName("<#571664>Spawn Hellhound")
                        .withCustomModelData(3)
                        .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "hellhound")), main);
            }

            if (BetterConfig.CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED) {
                ItemManager.registerNewItem(new MVItem("alpha_hellhound_spawn_egg", Material.GHAST_SPAWN_EGG)
                        .withDisplayName("<#571664>Spawn Alpha Hellhound")
                        .withCustomModelData(4)
                        .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "alpha_hellhound")), main);
            }
        }
        if (BetterConfig.CUSTOM_MOB_LOST_SOUL_ENABLED) {
            ItemManager.registerNewItem(new MVItem("soul", Material.GHAST_TEAR)
                    .withDisplayName("<#1152a6>Soul")
                    .withCustomModelData(1)
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.EFFECT_HOLDER,
                            Collections.singletonList(new MVPotionEffect(PotionEffectType.REGENERATION, 90, 2, 2000)), true)), main);

            ItemManager.registerNewItem(new MVItem("lost_soul_spawn_egg", Material.GHAST_SPAWN_EGG)
                    .withDisplayName("<#1152a6>Spawn Lost Soul")
                    .withCustomModelData(5)
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "lost_soul")), main);
        }
        if (BetterConfig.CUSTOM_MOB_ZOMBIFIED_DEMON_ENABLED) {
            ItemManager.registerNewItem(new MVItem("rotten_demon_flesh", Material.ROTTEN_FLESH)
                    .withDisplayName("<#2d2501>Rotten Demon Flesh")
                    .withCustomModelData(2)
                    .withConsumeEffect(new ItemConsumeEffect("&cFood Poisoning",
                            Arrays.asList(new MVPotionEffect(PotionEffectType.POISON, 50, 1, 2000),
                                    new MVPotionEffect(PotionEffectType.CONFUSION, 50, 1, 2000)), 1)), main);

            ItemManager.registerNewItem(new MVItem("zombified_demon_spawn_egg", Material.GHAST_SPAWN_EGG)
                    .withDisplayName("<#2d2501>Spawn Zombified Demon")
                    .withCustomModelData(6)
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "zombified_demon")), main);

            ItemManager.registerNewItem(new MVItem("demon_head", Material.PLAYER_HEAD)
                    .withDisplayName("<#2d2501>Demon Head")
                    .withSkullOwnerByBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTg4ODExM2E5ZWE2OGM4ZDg4N2U3ZDNlZTU0YmFjM2VhNDIxMGJmYmFiNzIwZDhlODRiZTU3MDVjZmFkYzViIn19fQ=="), main);

            ItemManager.registerNewItem(new MVItem("demon_chestplate", Material.LEATHER_CHESTPLATE)
                    .withDisplayName("<#2d2501>Demons Chestplate")
                    .withColor(Color.GRAY)
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR, 1))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR_TOUGHNESS, 1)), main);

            ItemManager.registerNewItem(new MVItem("demon_leggings", Material.LEATHER_LEGGINGS)
                    .withDisplayName("<#2d2501>Demons Leggings")
                    .withColor(Color.GRAY)
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR, 1.2))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR_TOUGHNESS, 1.1)), main);

            ItemManager.registerNewItem(new MVItem("demon_boots", Material.LEATHER_BOOTS)
                    .withDisplayName("<#2d2501>Demons Boots")
                    .withColor(Color.BLACK)
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR, 1.3))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR_TOUGHNESS, 0.9)), main);
        }
        if (BetterConfig.CUSTOM_MOB_TANK_ENABLED) {
            ItemManager.registerNewItem(new MVItem("tank_sword", Material.STONE_SWORD)
                    .withDisplayName("<#f1e46a>Tenonus")
                    .withCustomModelData(1)
                    .withEnchant(new ItemEnchantInfo(Enchantment.DAMAGE_ALL, 1)), main);

            ItemManager.registerNewItem(new MVItem("tank_head", Material.PLAYER_HEAD)
                    .withDisplayName("<#f1e46a>Tanks Head")
                    .withSkullOwnerByBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDI5NzViNjdjMTlmOWJhMjM0NGY4ZWVlOTU2YzUwMTVhZDYzZDllODhhZDQ4ODJhZTc5MzY5Mzc0ZmIzOTc1In19fQ==")
                    .withColor(Color.RED), main);

            ItemManager.registerNewItem(new MVItem("tank_chestplate", Material.LEATHER_CHESTPLATE)
                    .withDisplayName("<#f1e46a>Tanks Chestplate")
                    .withColor(Color.GRAY)
                    .withLore("&7The chestplate of a Tank.")
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR, 3.5))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR_TOUGHNESS, 1.2))
                    .withAttribute(new ItemAttributeInfo("generic.knockback_resistance", EquipmentSlot.HEAD, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 0.3)), main);

            ItemManager.registerNewItem(new MVItem("tank_leggings", Material.LEATHER_LEGGINGS)
                    .withDisplayName("<#f1e46a>Tanks Leggings")
                    .withColor(Color.GRAY)
                    .withLore("&7The leggings of a Tank.")
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR, 2.5))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR_TOUGHNESS, 0.6))
                    .withAttribute(new ItemAttributeInfo("generic.knockback_resistance", EquipmentSlot.HEAD, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 0.2)), main);

            ItemManager.registerNewItem(new MVItem("tank_boots", Material.LEATHER_BOOTS)
                    .withDisplayName("<#f1e46a>Tanks Boots")
                    .withColor(Color.BLACK)
                    .withLore("&7The boots of a Tank.")
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR, 1.2))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.HEAD, Attribute.GENERIC_ARMOR_TOUGHNESS, 0.2))
                    .withAttribute(new ItemAttributeInfo("generic.knockback_resistance", EquipmentSlot.HEAD, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 0.1)), main);

            ItemManager.registerNewItem(new MVItem("demon_tank_spawn_egg", Material.GHAST_SPAWN_EGG)
                    .withDisplayName("<#f1e46a>Spawn Demon Tank")
                    .withCustomModelData(7)
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.SPAWN_CUSTOM_MOB, "demon_tank")), main);

            ItemManager.registerNewItem(new MVItem("demon_tank_totem", Material.TOTEM_OF_UNDYING)
                    .withDisplayName("&6Tank Totem")
                    .withCustomModelData(1)
                    .withLore("&7A special rare totem that can be used as a revival token")
                    .withLore("&7or for limited special effects.")
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.EFFECT_HOLDER, Arrays.asList(new MVPotionEffect(PotionEffectType.ABSORPTION, 12, 2, 3000),
                            new MVPotionEffect(PotionEffectType.REGENERATION, 25, 2, 3000),
                            new MVPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 25, 2, 3000)), true)), main);
        }
    }
}
