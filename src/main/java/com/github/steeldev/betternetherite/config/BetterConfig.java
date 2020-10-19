package com.github.steeldev.betternetherite.config;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.util.WhyNoWorkException;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BetterConfig {
    // Config stuff
    public static boolean DEBUG;
    public static boolean ENABLE_NETHERITE_CRAFTING;
    public static boolean IMPROVED_UPGRADING;
    // Upgrade Recipes
    public static Map<String, Integer> UPGRADE_RECIPES_WOOD_TO_STONE;
    public static Map<String, Integer> UPGRADE_RECIPES_STONE_TO_IRON;
    public static Map<String, Integer> UPGRADE_RECIPES_IRON_TO_DIAMOND;
    public static Map<String, Integer> UPGRADE_RECIPES_IRON_TO_GOLD;
    public static Map<String, Integer> UPGRADE_RECIPES_DIAMOND_TO_NETHERITE;
    // Ancient Debris Better Smelting
    public static boolean ANCIENT_DEBRIS_BETTER_SMELTING_ENABLED;
    public static int ANCIENT_DEBRIS_BETTER_SMELTING_AMOUNT;
    public static int ANCIENT_DEBRIS_BETTER_SMELTING_BLAST_FURNACE_EXP;
    public static int ANCIENT_DEBRIS_BETTER_SMELTING_BLAST_FURNACE_TIME;
    public static int ANCIENT_DEBRIS_BETTER_SMELTING_FURNACE_EXP;
    public static int ANCIENT_DEBRIS_BETTER_SMELTING_FURNACE_TIME;
    // Ancient Debris Scrap Drop
    public static boolean ANCIENT_DEBRIS_SCRAP_DROP_ENABLED;
    public static int ANCIENT_DEBRIS_SCRAP_DROP_MAX;
    public static int ANCIENT_DEBRIS_SCRAP_DROP_CHANCE;
    // Ancient Debris Ingot Drop
    public static boolean ANCIENT_DEBRIS_INGOT_DROP_ENABLED;
    public static int ANCIENT_DEBRIS_INGOT_DROP_CHANCE;
    // Shrines
    public static Map<String, Material> USABLE_SHRINE_ITEMS;

    public static boolean CRIMSON_NETHERITE_SHRINE_ENABLED;
    public static boolean WARPED_NETHERITE_SHRINE_ENABLED;

    public static int CRIMSON_NETHERITE_SHRINE_EXPLODE_CHANCE;
    public static String CRIMSON_NETHERITE_SHRINE_DISPLAY;
    public static String CRIMSON_NETHERITE_SHRINE_EFFECT_DISPLAY;
    public static Material CRIMSON_NETHERITE_SHRINE_CHARGE_MAT;

    public static int WARPED_NETHERITE_SHRINE_EXPLODE_CHANCE;
    public static String WARPED_NETHERITE_SHRINE_DISPLAY;
    public static String WARPED_NETHERITE_SHRINE_EFFECT_DISPLAY;
    public static Material WARPED_NETHERITE_SHRINE_CHARGE_MAT;

    //Reinforced items
    public static int REINFORCED_ITEM_DURABILITY_LOSS_CHANCE;
    public static int REINFORCED_ITEM_EXTRA_DMG_CHANCE;
    public static int REINFORCED_ITEM_DAMAGE_INCREASE;

    //Netherite Fish Treasure
    public static boolean NETHERITE_FISH_TREASURE_ENABLED;
    public static List<String> NETHERITE_FISH_TREASURE_LOOT;

    //Mobs
    public static boolean CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED;
    public static boolean CUSTOM_MOB_HELLHOUND_ENABLED;

    private final BetterNetherite plugin;
    private FileConfiguration config;
    private File configFile;

    public BetterConfig(BetterNetherite plugin) {
        this.plugin = plugin;
        loadConfigFile();
    }

    private void loadConfigFile() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "Config.yml");
        }
        if (!configFile.exists()) {
            plugin.saveResource("Config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        matchConfig();
        loadConfigs();
    }

    // Used to update config
    @SuppressWarnings("ConstantConditions")
    private void matchConfig() {
        try {
            boolean hasUpdated = false;
            InputStream stream = plugin.getResource(configFile.getName());
            assert stream != null;
            InputStreamReader is = new InputStreamReader(stream);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(is);
            for (String key : defConfig.getConfigurationSection("").getKeys(true)) {
                if (!config.contains(key)) {
                    config.set(key, defConfig.get(key));
                    hasUpdated = true;
                }
            }
            for (String key : config.getConfigurationSection("").getKeys(true)) {
                if (!defConfig.contains(key)) {
                    config.set(key, null);
                    hasUpdated = true;
                }
            }
            if (hasUpdated)
                config.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadConfigs() {
        DEBUG = config.getBoolean("Debug");
        ENABLE_NETHERITE_CRAFTING = config.getBoolean("NetheriteCrafting");
        IMPROVED_UPGRADING = config.getBoolean("ImprovedUpgrading.Enabled");
        if(ENABLE_NETHERITE_CRAFTING && IMPROVED_UPGRADING){
            throw new WhyNoWorkException("Netherite Crafting and Improved Upgrading cannot both be true.");
        }
        ANCIENT_DEBRIS_BETTER_SMELTING_ENABLED = config.getBoolean("AncientDebris.BetterSmelting.Enabled");
        ANCIENT_DEBRIS_BETTER_SMELTING_AMOUNT = config.getInt("AncientDebris.BetterSmelting.Amount");
        ANCIENT_DEBRIS_BETTER_SMELTING_BLAST_FURNACE_EXP = config.getInt("AncientDebris.BetterSmelting.BlastFurnace.EXP");
        ANCIENT_DEBRIS_BETTER_SMELTING_BLAST_FURNACE_TIME = config.getInt("AncientDebris.BetterSmelting.BlastFurnace.Time");
        ANCIENT_DEBRIS_BETTER_SMELTING_FURNACE_EXP = config.getInt("AncientDebris.BetterSmelting.Furnace.EXP");
        ANCIENT_DEBRIS_BETTER_SMELTING_FURNACE_TIME = config.getInt("AncientDebris.BetterSmelting.Furnace.Time");
        ANCIENT_DEBRIS_SCRAP_DROP_ENABLED = config.getBoolean("AncientDebris.ScrapDrop.Enabled");
        ANCIENT_DEBRIS_SCRAP_DROP_MAX = config.getInt("AncientDebris.ScrapDrop.Maximum");
        ANCIENT_DEBRIS_SCRAP_DROP_CHANCE = config.getInt("AncientDebris.ScrapDrop.Chance");
        ANCIENT_DEBRIS_INGOT_DROP_ENABLED = config.getBoolean("AncientDebris.IngotDrop.Enabled");
        ANCIENT_DEBRIS_INGOT_DROP_CHANCE = config.getInt("AncientDebris.IngotDrop.Chance");

        WARPED_NETHERITE_SHRINE_ENABLED = config.getBoolean("NetheriteShrines.WarpedShrine.Enabled");
        CRIMSON_NETHERITE_SHRINE_ENABLED = config.getBoolean("NetheriteShrines.CrimsonShrine.Enabled");
        
        CRIMSON_NETHERITE_SHRINE_EXPLODE_CHANCE = config.getInt("NetheriteShrines.CrimsonShrine.ExplosionChance");
        CRIMSON_NETHERITE_SHRINE_DISPLAY = config.getString("NetheriteShrines.CrimsonShrine.Display");
        CRIMSON_NETHERITE_SHRINE_EFFECT_DISPLAY = config.getString("NetheriteShrines.CrimsonShrine.EffectDisplay");
        CRIMSON_NETHERITE_SHRINE_CHARGE_MAT = Material.valueOf(config.getString("NetheriteShrines.CrimsonShrine.ChargeMaterial"));

        WARPED_NETHERITE_SHRINE_EXPLODE_CHANCE = config.getInt("NetheriteShrines.WarpedShrine.ExplosionChance");
        WARPED_NETHERITE_SHRINE_DISPLAY = config.getString("NetheriteShrines.WarpedShrine.Display");
        WARPED_NETHERITE_SHRINE_EFFECT_DISPLAY = config.getString("NetheriteShrines.WarpedShrine.EffectDisplay");
        WARPED_NETHERITE_SHRINE_CHARGE_MAT = Material.valueOf(config.getString("NetheriteShrines.WarpedShrine.ChargeMaterial"));

        REINFORCED_ITEM_DURABILITY_LOSS_CHANCE = config.getInt("NetheriteShrines.CrimsonShrine.ReinforcedItems.DurabilityLossChance");
        REINFORCED_ITEM_EXTRA_DMG_CHANCE = config.getInt("NetheriteShrines.CrimsonShrine.ReinforcedItems.ExtraDamageChance");
        REINFORCED_ITEM_DAMAGE_INCREASE = config.getInt("NetheriteShrines.CrimsonShrine.ReinforcedItems.DamageIncrease");

        NETHERITE_FISH_TREASURE_ENABLED = config.getBoolean("NetheriteFishing.Enabled");
        NETHERITE_FISH_TREASURE_LOOT = config.getStringList("NetheriteFishing.Loot");

        CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED = config.getBoolean("CustomMobs.NetheriteMarauder.Enabled");
        CUSTOM_MOB_HELLHOUND_ENABLED = config.getBoolean("CustomMobs.Hellhound.Enabled");

        USABLE_SHRINE_ITEMS = new HashMap<>();
        List<String> shrineItemSection = config.getStringList("NetheriteShrines.UsableItems");
        for (String key : shrineItemSection) {
            Material mat = Material.valueOf(key);
            USABLE_SHRINE_ITEMS.put(key, mat);
        }

        UPGRADE_RECIPES_WOOD_TO_STONE = new HashMap<>();
        ConfigurationSection woodSection = config.getConfigurationSection("ImprovedUpgrading.UpgradeRecipes.WoodToStone");
        if (woodSection == null) return;
        for (String key : woodSection.getKeys(false)) {
            UPGRADE_RECIPES_WOOD_TO_STONE.put(key, woodSection.getInt(key));
        }

        UPGRADE_RECIPES_STONE_TO_IRON = new HashMap<>();
        ConfigurationSection stoneSection = config.getConfigurationSection("ImprovedUpgrading.UpgradeRecipes.StoneToIron");
        if (stoneSection == null) return;
        for (String key : stoneSection.getKeys(false)) {
            UPGRADE_RECIPES_STONE_TO_IRON.put(key, stoneSection.getInt(key));
        }

        UPGRADE_RECIPES_IRON_TO_DIAMOND = new HashMap<>();
        ConfigurationSection ironSection = config.getConfigurationSection("ImprovedUpgrading.UpgradeRecipes.IronToDiamond");
        if (ironSection == null) return;
        for (String key : ironSection.getKeys(false)) {
            UPGRADE_RECIPES_IRON_TO_DIAMOND.put(key, ironSection.getInt(key));
        }

        UPGRADE_RECIPES_IRON_TO_GOLD = new HashMap<>();
        ConfigurationSection goldSection = config.getConfigurationSection("ImprovedUpgrading.UpgradeRecipes.IronToGold");
        if (goldSection == null) return;
        for (String key : goldSection.getKeys(false)) {
            UPGRADE_RECIPES_IRON_TO_GOLD.put(key, goldSection.getInt(key));
        }

        UPGRADE_RECIPES_DIAMOND_TO_NETHERITE = new HashMap<>();
        ConfigurationSection diamondSection = config.getConfigurationSection("ImprovedUpgrading.UpgradeRecipes.DiamondToNetherite");
        if (diamondSection == null) return;
        for (String key : diamondSection.getKeys(false)) {
            UPGRADE_RECIPES_DIAMOND_TO_NETHERITE.put(key, diamondSection.getInt(key));
        }
    }
}
