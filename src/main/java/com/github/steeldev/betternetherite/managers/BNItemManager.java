package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.monstrorvm.Monstrorvm;
import com.github.steeldev.monstrorvm.api.items.*;
import com.github.steeldev.monstrorvm.api.items.types.ItemUseEffectType;
import com.github.steeldev.monstrorvm.api.misc.MVPotionEffect;
import com.github.steeldev.monstrorvm.api.recipes.ItemCraftingRecipe;
import com.github.steeldev.monstrorvm.api.recipes.ItemSmeltingRecipe;
import com.github.steeldev.monstrorvm.api.recipes.RecipeManager;
import com.github.steeldev.monstrorvm.api.recipes.types.CraftType;
import com.github.steeldev.monstrorvm.api.recipes.types.SmeltType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static com.github.steeldev.betternetherite.util.Util.main;

public class BNItemManager {
    public static void registerCustomItems() {
        if (main.config.UPGRADE_PACK_ENABLED) {
            MVItem upgradePack = new MVItem("upgrade_pack", Material.SHEARS)
                    .withDisplayName("&6Upgrade Pack")
                    .withLore("&7Upgrade Diamond items to Netherite")
                    .withLore("&7within your inventory.")
                    .withCustomModelData(main.config.ITEM_MODEL_DATAS.get("UpgradePack"));
            ItemManager.registerNewItem(upgradePack, main);
            RecipeManager.registerNewRecipe(new ItemCraftingRecipe(CraftType.SHAPED, Arrays.asList("NIN", "DSD", "NIN"),
                    new HashMap<Character, RecipeChoice>() {{
                        put('N', new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT));
                        put('I', new RecipeChoice.MaterialChoice(Material.IRON_INGOT));
                        put('S', new RecipeChoice.MaterialChoice(Material.STICK));
                        put('D', new RecipeChoice.MaterialChoice(Material.DIAMOND));
                    }},
                    upgradePack.getItemStack(),
                    1,
                    "upgrade_pack_crafting"), main);
        }
        String reinforcedLore = (BNShrineManager.getBNShrine("crimson_shrine") != null) ? BNShrineManager.getBNShrine("crimson_shrine").effect.effectLoreDisplay : "<#11349c>REINFORCED";
        if (main.config.CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED || main.config.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED) {
            ItemManager.registerNewItem(new MVItem("marauder_bone", Material.BONE)
                    .withDisplayName("<#3c1a4c>Marauder Bone")
                    .withCustomModelData(main.config.ITEM_MODEL_DATAS.get("MarauderBone"))
                    .withLore("&7A Netherite Marauder's bone."), main);

            ItemManager.registerNewItem(new MVItem("marauder_sword", Material.NETHERITE_SWORD)
                    .withDisplayName("<#3c1a4c>Marauder Sword")
                    .withNBT(new ItemNBTCompound("netherite_reinforced", true))
                    .withLore(reinforcedLore)
                    .withCustomModelData(main.config.ITEM_MODEL_DATAS.get("MarauderSword"))
                    .withLore("&7A Netherite Marauder's sword.")
                    .withAttribute(new ItemAttributeInfo("generic.attack_damage", EquipmentSlot.HAND, Attribute.GENERIC_ATTACK_DAMAGE, 9))
                    .withAttribute(new ItemAttributeInfo("generic.attack_speed", EquipmentSlot.HAND, Attribute.GENERIC_ATTACK_SPEED, 1.7)), main);

            ItemManager.registerNewItem(new MVItem("marauder_chestplate", Material.NETHERITE_CHESTPLATE)
                    .withDisplayName("<#3c1a4c>Marauder Chestplate")
                    .withNBT(new ItemNBTCompound("netherite_reinforced", true))
                    .withLore(reinforcedLore)
                    .withLore("&7A Netherite Marauder's chestplate.")
                    .withAttribute(new ItemAttributeInfo("generic.armor", EquipmentSlot.CHEST, Attribute.GENERIC_ARMOR, 9))
                    .withAttribute(new ItemAttributeInfo("generic.armor_toughness", EquipmentSlot.CHEST, Attribute.GENERIC_ARMOR_TOUGHNESS, 3.5))
                    .withAttribute(new ItemAttributeInfo("generic.knockback_resistance", EquipmentSlot.CHEST, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 1.2)), main);
        }
        if (main.config.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED) {
            ItemManager.registerNewItem(new MVItem("marauder_axe", Material.NETHERITE_AXE)
                    .withDisplayName("<#3c1a4c>Marauder Axe")
                    .withNBT(new ItemNBTCompound("netherite_reinforced", true))
                    .withLore(reinforcedLore)
                    .withCustomModelData(main.config.ITEM_MODEL_DATAS.get("MarauderAxe"))
                    .withLore("&7A Netherite Marauder's axe.")
                    .withAttribute(new ItemAttributeInfo("generic.attack_damage", EquipmentSlot.HAND, Attribute.GENERIC_ATTACK_DAMAGE, 11))
                    .withAttribute(new ItemAttributeInfo("generic.attack_speed", EquipmentSlot.HAND, Attribute.GENERIC_ATTACK_SPEED, 1.8)), main);
        }
        if (main.config.CUSTOM_MOB_HELLHOUND_ENABLED || main.config.CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED) {
            MVItem cookedHoundMeat = new MVItem("cooked_hound_meat", Material.COOKED_PORKCHOP)
                    .withDisplayName("<#915103>Cooked Hound Meat")
                    .withCustomModelData(main.config.ITEM_MODEL_DATAS.get("CookedHoundMeat"))
                    .withConsumeEffect(new ItemConsumeEffect("&bHounds Grace",
                            Arrays.asList(new MVPotionEffect(PotionEffectType.ABSORPTION, 12, 2, 2000),
                                    new MVPotionEffect(PotionEffectType.REGENERATION, 25, 2, 2000)), 7));
            ItemManager.registerNewItem(cookedHoundMeat, main);

            MVItem houndMeat = new MVItem("hound_meat", Material.PORKCHOP)
                    .withCustomModelData(main.config.ITEM_MODEL_DATAS.get("HoundMeat"))
                    .withDisplayName("<#915103>Raw Hound Meat");

            ItemManager.registerNewItem(houndMeat, main);

            RecipeManager.registerNewRecipe(new ItemSmeltingRecipe(SmeltType.FURNACE,
                    houndMeat.getItemStack(),
                    cookedHoundMeat.getItemStack(),
                    130,
                    2,
                    1,
                    "cooked_hound_meat_smelting"), main);

            RecipeManager.registerNewRecipe(new ItemSmeltingRecipe(SmeltType.SMOKER,
                    houndMeat.getItemStack(),
                    cookedHoundMeat.getItemStack(),
                    100,
                    4,
                    1,
                    "cooked_hound_meat_smoker"), main);

            ItemManager.registerNewItem(new MVItem("rotten_hound_meat", Material.ROTTEN_FLESH)
                    .withDisplayName("<#2d2501>Rotted Hound Meat")
                    .withCustomModelData(main.config.ITEM_MODEL_DATAS.get("RottenHoundMeat"))
                    .withConsumeEffect(new ItemConsumeEffect("&cFood Poisoning",
                            Arrays.asList(new MVPotionEffect(PotionEffectType.POISON, 50, 1, 2000),
                                    new MVPotionEffect(PotionEffectType.CONFUSION, 50, 1, 2000)), 2)), main);
        }
        if (main.config.CUSTOM_MOB_LOST_SOUL_ENABLED) {
            ItemManager.registerNewItem(new MVItem("soul", Material.GHAST_TEAR)
                    .withDisplayName("<#1152a6>Soul")
                    .withCustomModelData(main.config.ITEM_MODEL_DATAS.get("Soul"))
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.EFFECT_HOLDER,
                            Collections.singletonList(new MVPotionEffect(PotionEffectType.REGENERATION, 90, 2, 2000)), true)), main);
        }
        if (main.config.CUSTOM_MOB_ZOMBIFIED_DEMON_ENABLED) {
            ItemManager.registerNewItem(new MVItem("rotten_demon_flesh", Material.ROTTEN_FLESH)
                    .withDisplayName("<#2d2501>Rotten Demon Flesh")
                    .withCustomModelData(main.config.ITEM_MODEL_DATAS.get("RottenDemonFlesh"))
                    .withConsumeEffect(new ItemConsumeEffect("&cFood Poisoning",
                            Arrays.asList(new MVPotionEffect(PotionEffectType.POISON, 50, 1, 2000),
                                    new MVPotionEffect(PotionEffectType.CONFUSION, 50, 1, 2000)), 1)), main);

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
        if (main.config.CUSTOM_MOB_TANK_ENABLED) {
            ItemManager.registerNewItem(new MVItem("tank_sword", Material.STONE_SWORD)
                    .withDisplayName("<#f1e46a>Tenonus")
                    .withCustomModelData(main.config.ITEM_MODEL_DATAS.get("TankSword"))
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

            ItemManager.registerNewItem(new MVItem("demon_tank_totem", Material.TOTEM_OF_UNDYING)
                    .withDisplayName("&6Tank Totem")
                    .withCustomModelData(main.config.ITEM_MODEL_DATAS.get("DemonTankTotem"))
                    .withLore("&7A special rare totem that can be used as a revival token")
                    .withLore("&7or for limited special effects.")
                    .withUseEffect(new ItemUseEffect(ItemUseEffectType.EFFECT_HOLDER, Arrays.asList(new MVPotionEffect(PotionEffectType.ABSORPTION, 12, 2, 3000),
                            new MVPotionEffect(PotionEffectType.REGENERATION, 25, 2, 3000),
                            new MVPotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 25, 2, 3000)), true)), main);
        }
    }
}
