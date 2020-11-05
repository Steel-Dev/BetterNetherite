package com.github.steeldev.betternetherite.config;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.util.WhyNoWorkException;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static com.github.steeldev.betternetherite.util.Util.formalizedString;

public class BetterConfig {
    // Config stuff
    public static boolean DEBUG;
    public static String SELECTED_LANGUAGE;
    public static boolean NEW_UPDATE_MESSAGE_ON_JOIN;
    public static boolean NEW_UPDATE_MESSAGE_ON_RELOAD;
    public static boolean ENABLE_NETHERITE_CRAFTING;
    public static boolean IMPROVED_UPGRADING;
    // Upgrade Recipes
    public static boolean IMPROVED_UPGRADING_WOOD_TO_STONE_ENABLED;
    public static int IMPROVED_UPGRADING_WOOD_TO_STONE_AMOUNT;

    public static boolean IMPROVED_UPGRADING_STONE_TO_IRON_ENABLED;
    public static int IMPROVED_UPGRADING_STONE_TO_IRON_AMOUNT;

    public static boolean IMPROVED_UPGRADING_IRON_TO_DIAMOND_ENABLED;
    public static int IMPROVED_UPGRADING_IRON_TO_DIAMOND_AMOUNT;

    public static boolean IMPROVED_UPGRADING_IRON_TO_GOLD_ENABLED;
    public static int IMPROVED_UPGRADING_IRON_TO_GOLD_AMOUNT;

    public static boolean IMPROVED_UPGRADING_DIAMOND_TO_NETHERITE_ENABLED;
    public static int IMPROVED_UPGRADING_DIAMOND_TO_NETHERITE_AMOUNT;
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
    public static boolean PRISMARINE_NETHERITE_SHRINE_ENABLED;

    //Reinforced items
    public static int REINFORCED_ITEM_DURABILITY_LOSS_CHANCE;
    public static int REINFORCED_ITEM_EXTRA_DMG_CHANCE;
    public static int REINFORCED_ITEM_DAMAGE_INCREASE;

    //Netherite Fish Treasure
    public static boolean NETHERITE_FISH_TREASURE_ENABLED;
    public static List<String> NETHERITE_FISH_TREASURE_LOOT;

    //Mobs
    public static boolean CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED;
    public static int CUSTOM_MOB_NETHERITE_MARAUDER_SPAWNCHANCE;

    public static boolean CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED;
    public static int CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_SPAWNCHANCE;

    public static boolean CUSTOM_MOB_HELLHOUND_ENABLED;
    public static int CUSTOM_MOB_HELLHOUND_SPAWNCHANCE;

    public static boolean CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED;
    public static int CUSTOM_MOB_ALPHA_HELLHOUND_SPAWNCHANCE;

    public static boolean CUSTOM_MOB_LOST_SOUL_ENABLED;
    public static int CUSTOM_MOB_LOST_SOUL_SPAWNCHANCE;

    public static boolean CUSTOM_MOB_ZOMBIFIED_DEMON_ENABLED;
    public static int CUSTOM_MOB_ZOMBIFIED_DEMON_SPAWNCHANCE;

    public static boolean CUSTOM_MOB_TANK_ENABLED;
    public static int CUSTOM_MOB_TANK_SPAWNCHANCE;


    public static boolean RESOURCE_PACK_ENABLED;
    public static String RESOURCE_PACK_URL;
    public static boolean RESOURCE_PACK_JOIN_MSG_ENABLED;
    public static boolean RESOURCE_PACK_STATUS_MSGS_ENABLED;
    public static boolean RESOURCE_PACK_AUTO_UPDATE;
    static List<String> supportedLanguages = new ArrayList<>(Arrays.asList("English"));
    private static FileConfiguration config;
    private static File configFile;
    private final BetterNetherite plugin;

    public BetterConfig(BetterNetherite plugin) {
        this.plugin = plugin;
        loadConfigFile();
    }

    public static void setString(String path, String value) throws IOException {
        config.set(path, value);

        config.save(configFile);
    }

    public static void setBool(String path, boolean value) throws IOException {
        config.set(path, value);

        config.save(configFile);
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
        SELECTED_LANGUAGE = formalizedString(config.getString("Language"));
        if (!supportedLanguages.contains(SELECTED_LANGUAGE)) {
            throw new WhyNoWorkException("The specified language " + SELECTED_LANGUAGE + " is invalid, or not yet supported!");
        }
        NEW_UPDATE_MESSAGE_ON_JOIN = config.getBoolean("UpdateCheck.MessageOnJoin");
        NEW_UPDATE_MESSAGE_ON_RELOAD = config.getBoolean("UpdateCheck.MessageOnReload");
        ENABLE_NETHERITE_CRAFTING = config.getBoolean("NetheriteCrafting");
        IMPROVED_UPGRADING = config.getBoolean("ImprovedUpgrading.Enabled");
        if (ENABLE_NETHERITE_CRAFTING && IMPROVED_UPGRADING) {
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
        PRISMARINE_NETHERITE_SHRINE_ENABLED = config.getBoolean("NetheriteShrines.PrismarineShrine.Enabled");

        REINFORCED_ITEM_DURABILITY_LOSS_CHANCE = config.getInt("NetheriteShrines.CrimsonShrine.ReinforcedItems.DurabilityLossChance");
        REINFORCED_ITEM_EXTRA_DMG_CHANCE = config.getInt("NetheriteShrines.CrimsonShrine.ReinforcedItems.ExtraDamageChance");
        REINFORCED_ITEM_DAMAGE_INCREASE = config.getInt("NetheriteShrines.CrimsonShrine.ReinforcedItems.DamageIncrease");

        NETHERITE_FISH_TREASURE_ENABLED = config.getBoolean("NetheriteFishing.Enabled");
        NETHERITE_FISH_TREASURE_LOOT = config.getStringList("NetheriteFishing.Loot");

        CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED = config.getBoolean("CustomMobs.NetheriteMarauder.Enabled");
        CUSTOM_MOB_NETHERITE_MARAUDER_SPAWNCHANCE = config.getInt("CustomMobs.NetheriteMarauder.SpawnChance");

        CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED = config.getBoolean("CustomMobs.NetheriteMarauderBrute.Enabled");
        CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_SPAWNCHANCE = config.getInt("CustomMobs.NetheriteMarauderBrute.SpawnChance");

        CUSTOM_MOB_HELLHOUND_ENABLED = config.getBoolean("CustomMobs.Hellhound.Enabled");
        CUSTOM_MOB_HELLHOUND_SPAWNCHANCE = config.getInt("CustomMobs.Hellhound.SpawnChance");

        CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED = config.getBoolean("CustomMobs.AlphaHellhound.Enabled");
        CUSTOM_MOB_ALPHA_HELLHOUND_SPAWNCHANCE = config.getInt("CustomMobs.AlphaHellhound.SpawnChance");

        CUSTOM_MOB_LOST_SOUL_ENABLED = config.getBoolean("CustomMobs.LostSoul.Enabled");
        CUSTOM_MOB_LOST_SOUL_SPAWNCHANCE = config.getInt("CustomMobs.LostSoul.SpawnChance");

        CUSTOM_MOB_ZOMBIFIED_DEMON_ENABLED = config.getBoolean("CustomMobs.ZombifiedDemon.Enabled");
        CUSTOM_MOB_ZOMBIFIED_DEMON_SPAWNCHANCE = config.getInt("CustomMobs.ZombifiedDemon.SpawnChance");

        CUSTOM_MOB_TANK_ENABLED = config.getBoolean("CustomMobs.Tank.Enabled");
        CUSTOM_MOB_TANK_SPAWNCHANCE = config.getInt("CustomMobs.Tank.SpawnChance");


        RESOURCE_PACK_ENABLED = config.getBoolean("ResourcePack.Enabled");
        RESOURCE_PACK_URL = config.getString("ResourcePack.URL");
        RESOURCE_PACK_JOIN_MSG_ENABLED = config.getBoolean("ResourcePack.JoinMsgEnabled");
        RESOURCE_PACK_STATUS_MSGS_ENABLED = config.getBoolean("ResourcePack.StatusMsgsEnabled");
        RESOURCE_PACK_AUTO_UPDATE = config.getBoolean("ResourcePack.AutoUpdate");

        USABLE_SHRINE_ITEMS = new HashMap<>();
        List<String> shrineItemSection = config.getStringList("NetheriteShrines.UsableItems");
        for (String key : shrineItemSection) {
            Material mat = Material.valueOf(key);
            USABLE_SHRINE_ITEMS.put(key, mat);
        }

        IMPROVED_UPGRADING_WOOD_TO_STONE_ENABLED = config.getBoolean("ImprovedUpgrading.UpgradeRecipes.WoodToStone.Enabled");
        IMPROVED_UPGRADING_WOOD_TO_STONE_AMOUNT = config.getInt("ImprovedUpgrading.UpgradeRecipes.WoodToStone.MaterialAmount");

        IMPROVED_UPGRADING_STONE_TO_IRON_ENABLED = config.getBoolean("ImprovedUpgrading.UpgradeRecipes.StoneToIron.Enabled");
        IMPROVED_UPGRADING_STONE_TO_IRON_AMOUNT = config.getInt("ImprovedUpgrading.UpgradeRecipes.StoneToIron.MaterialAmount");

        IMPROVED_UPGRADING_IRON_TO_GOLD_ENABLED = config.getBoolean("ImprovedUpgrading.UpgradeRecipes.IronToGold.Enabled");
        IMPROVED_UPGRADING_IRON_TO_GOLD_AMOUNT = config.getInt("ImprovedUpgrading.UpgradeRecipes.IronToGold.MaterialAmount");

        IMPROVED_UPGRADING_IRON_TO_DIAMOND_ENABLED = config.getBoolean("ImprovedUpgrading.UpgradeRecipes.IronToDiamond.Enabled");
        IMPROVED_UPGRADING_IRON_TO_DIAMOND_AMOUNT = config.getInt("ImprovedUpgrading.UpgradeRecipes.IronToDiamond.MaterialAmount");

        IMPROVED_UPGRADING_DIAMOND_TO_NETHERITE_ENABLED = config.getBoolean("ImprovedUpgrading.UpgradeRecipes.DiamondToNetherite.Enabled");
        IMPROVED_UPGRADING_DIAMOND_TO_NETHERITE_AMOUNT = config.getInt("ImprovedUpgrading.UpgradeRecipes.DiamondToNetherite.MaterialAmount");
    }
}
