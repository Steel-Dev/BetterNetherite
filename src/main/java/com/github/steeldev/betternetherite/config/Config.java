package com.github.steeldev.betternetherite.config;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.util.WhyNoWorkException;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    private final BetterNetherite plugin;
    // Config stuff
    public String PREFIX;
    public boolean DEBUG;
    public boolean NEW_UPDATE_MESSAGE_ON_JOIN;
    public boolean NEW_UPDATE_MESSAGE_ON_RELOAD;
    public boolean ENABLE_NETHERITE_CRAFTING;
    public boolean IMPROVED_UPGRADING;
    public boolean UPGRADE_PACK_ENABLED;
    // Upgrade Recipes
    public boolean IMPROVED_UPGRADING_WOOD_TO_STONE_ENABLED;
    public int IMPROVED_UPGRADING_WOOD_TO_STONE_AMOUNT;
    public boolean IMPROVED_UPGRADING_STONE_TO_IRON_ENABLED;
    public int IMPROVED_UPGRADING_STONE_TO_IRON_AMOUNT;
    public boolean IMPROVED_UPGRADING_IRON_TO_DIAMOND_ENABLED;
    public int IMPROVED_UPGRADING_IRON_TO_DIAMOND_AMOUNT;
    public boolean IMPROVED_UPGRADING_IRON_TO_GOLD_ENABLED;
    public int IMPROVED_UPGRADING_IRON_TO_GOLD_AMOUNT;
    public boolean IMPROVED_UPGRADING_DIAMOND_TO_NETHERITE_ENABLED;
    public int IMPROVED_UPGRADING_DIAMOND_TO_NETHERITE_AMOUNT;
    // Ancient Debris Better Smelting
    public boolean ANCIENT_DEBRIS_BETTER_SMELTING_ENABLED;
    public int ANCIENT_DEBRIS_BETTER_SMELTING_AMOUNT;
    public int ANCIENT_DEBRIS_BETTER_SMELTING_BLAST_FURNACE_EXP;
    public int ANCIENT_DEBRIS_BETTER_SMELTING_BLAST_FURNACE_TIME;
    public int ANCIENT_DEBRIS_BETTER_SMELTING_FURNACE_EXP;
    public int ANCIENT_DEBRIS_BETTER_SMELTING_FURNACE_TIME;
    // Ancient Debris Scrap Drop
    public boolean ANCIENT_DEBRIS_SCRAP_DROP_ENABLED;
    public int ANCIENT_DEBRIS_SCRAP_DROP_MAX;
    public int ANCIENT_DEBRIS_SCRAP_DROP_CHANCE;
    // Ancient Debris Ingot Drop
    public boolean ANCIENT_DEBRIS_INGOT_DROP_ENABLED;
    public int ANCIENT_DEBRIS_INGOT_DROP_CHANCE;
    // Shrines
    public Map<String, Material> USABLE_SHRINE_ITEMS;
    public boolean CRIMSON_NETHERITE_SHRINE_ENABLED;
    public List<String> CRIMSON_NETHERITE_SHRINE_USABLE_IN;
    public boolean WARPED_NETHERITE_SHRINE_ENABLED;
    public List<String> WARPED_NETHERITE_SHRINE_USABLE_IN;
    public boolean PRISMARINE_NETHERITE_SHRINE_ENABLED;
    public List<String> PRISMARINE_NETHERITE_SHRINE_USABLE_IN;
    //Reinforced items
    public int REINFORCED_ITEM_DURABILITY_LOSS_CHANCE;
    public int REINFORCED_ITEM_EXTRA_DMG_CHANCE;
    public int REINFORCED_ITEM_DAMAGE_INCREASE;
    //Netherite Fish Treasure
    public boolean NETHERITE_FISH_TREASURE_ENABLED;
    public List<String> NETHERITE_FISH_TREASURE_LOOT;
    //Mobs
    public boolean CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED;
    public int CUSTOM_MOB_NETHERITE_MARAUDER_SPAWNCHANCE;
    public List<String> CUSTOM_MOB_NETHERITE_MARAUDER_SPAWN_WORLDS;

    public boolean CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED;
    public int CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_SPAWNCHANCE;
    public List<String> CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_SPAWN_WORLDS;

    public boolean CUSTOM_MOB_HELLHOUND_ENABLED;
    public int CUSTOM_MOB_HELLHOUND_SPAWNCHANCE;
    public List<String> CUSTOM_MOB_HELLHOUND_SPAWN_WORLDS;

    public boolean CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED;
    public int CUSTOM_MOB_ALPHA_HELLHOUND_SPAWNCHANCE;
    public List<String> CUSTOM_MOB_ALPHA_HELLHOUND_SPAWN_WORLDS;

    public boolean CUSTOM_MOB_LOST_SOUL_ENABLED;
    public int CUSTOM_MOB_LOST_SOUL_SPAWNCHANCE;
    public List<String> CUSTOM_MOB_LOST_SOUL_SPAWN_WORLDS;

    public boolean CUSTOM_MOB_ZOMBIFIED_DEMON_ENABLED;
    public int CUSTOM_MOB_ZOMBIFIED_DEMON_SPAWNCHANCE;
    public List<String> CUSTOM_MOB_ZOMBIFIED_DEMON_SPAWN_WORLDS;

    public boolean CUSTOM_MOB_TANK_ENABLED;
    public int CUSTOM_MOB_TANK_SPAWNCHANCE;
    public List<String> CUSTOM_MOB_TANK_SPAWN_WORLDS;

    public boolean CUSTOM_MOB_ANCIENT_PROTECTOR_ENABLED;
    public int CUSTOM_MOB_ANCIENT_PROTECTOR_SPAWNCHANCE;
    public List<String> CUSTOM_MOB_ANCIENT_PROTECTOR_SPAWN_WORLDS;

    public boolean CUSTOM_MOB_NETHERITE_GOLEM_ENABLED;
    public int CUSTOM_MOB_NETHERITE_GOLEM_SPAWNCHANCE;
    public List<String> CUSTOM_MOB_NETHERITE_GOLEM_SPAWN_WORLDS;

    public boolean RESOURCE_PACK_ENABLED;
    public String RESOURCE_PACK_URL;
    public boolean RESOURCE_PACK_JOIN_MSG_ENABLED;
    public boolean RESOURCE_PACK_STATUS_MSGS_ENABLED;
    public boolean RESOURCE_PACK_AUTO_UPDATE;
    public boolean FORCE_RESOURCE_PACK;
    public Map<String, Integer> ITEM_MODEL_DATAS;
    private FileConfiguration config;
    private File configFile;

    public Config(BetterNetherite plugin) {
        this.plugin = plugin;
        loadConfigFile();
    }

    public void setString(String path, String value) throws IOException {
        config.set(path, value);

        config.save(configFile);
    }

    public void setBool(String path, boolean value) throws IOException {
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
        PREFIX = config.getString("Prefix");
        DEBUG = config.getBoolean("Debug");
        NEW_UPDATE_MESSAGE_ON_JOIN = config.getBoolean("UpdateCheck.MessageOnJoin");
        NEW_UPDATE_MESSAGE_ON_RELOAD = config.getBoolean("UpdateCheck.MessageOnReload");
        ENABLE_NETHERITE_CRAFTING = config.getBoolean("NetheriteCrafting");
        IMPROVED_UPGRADING = config.getBoolean("ImprovedUpgrading.Enabled");
        UPGRADE_PACK_ENABLED = config.getBoolean("UpgradePackEnabled");
        if (ENABLE_NETHERITE_CRAFTING && IMPROVED_UPGRADING) {
            throw new WhyNoWorkException("Netherite Crafting and Improved Upgrading cannot both be true.");
        }
        if (UPGRADE_PACK_ENABLED && IMPROVED_UPGRADING) {
            throw new WhyNoWorkException("Upgrade Pack and Improved Upgrading cannot both be true.");
        }
        if (UPGRADE_PACK_ENABLED && ENABLE_NETHERITE_CRAFTING) {
            throw new WhyNoWorkException("Upgrade Pack and Netherite Crafting cannot both be true.");
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
        WARPED_NETHERITE_SHRINE_USABLE_IN = config.getStringList("NetheriteShrines.WarpedShrine.UsableIn");
        CRIMSON_NETHERITE_SHRINE_ENABLED = config.getBoolean("NetheriteShrines.CrimsonShrine.Enabled");
        CRIMSON_NETHERITE_SHRINE_USABLE_IN = config.getStringList("NetheriteShrines.CrimsonShrine.UsableIn");
        PRISMARINE_NETHERITE_SHRINE_ENABLED = config.getBoolean("NetheriteShrines.PrismarineShrine.Enabled");
        PRISMARINE_NETHERITE_SHRINE_USABLE_IN = config.getStringList("NetheriteShrines.PrismarineShrine.UsableIn");

        REINFORCED_ITEM_DURABILITY_LOSS_CHANCE = config.getInt("NetheriteShrines.CrimsonShrine.ReinforcedItems.DurabilityLossChance");
        REINFORCED_ITEM_EXTRA_DMG_CHANCE = config.getInt("NetheriteShrines.CrimsonShrine.ReinforcedItems.ExtraDamageChance");
        REINFORCED_ITEM_DAMAGE_INCREASE = config.getInt("NetheriteShrines.CrimsonShrine.ReinforcedItems.DamageIncrease");

        NETHERITE_FISH_TREASURE_ENABLED = config.getBoolean("NetheriteFishing.Enabled");
        NETHERITE_FISH_TREASURE_LOOT = config.getStringList("NetheriteFishing.Loot");

        CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED = config.getBoolean("CustomMobs.NetheriteMarauder.Enabled");
        CUSTOM_MOB_NETHERITE_MARAUDER_SPAWNCHANCE = config.getInt("CustomMobs.NetheriteMarauder.SpawnChance");
        CUSTOM_MOB_NETHERITE_MARAUDER_SPAWN_WORLDS = config.getStringList("CustomMobs.NetheriteMarauder.ValidSpawnWorlds");

        CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED = config.getBoolean("CustomMobs.NetheriteMarauderBrute.Enabled");
        CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_SPAWNCHANCE = config.getInt("CustomMobs.NetheriteMarauderBrute.SpawnChance");
        CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_SPAWN_WORLDS = config.getStringList("CustomMobs.NetheriteMarauderBrute.ValidSpawnWorlds");

        CUSTOM_MOB_HELLHOUND_ENABLED = config.getBoolean("CustomMobs.Hellhound.Enabled");
        CUSTOM_MOB_HELLHOUND_SPAWNCHANCE = config.getInt("CustomMobs.Hellhound.SpawnChance");
        CUSTOM_MOB_HELLHOUND_SPAWN_WORLDS = config.getStringList("CustomMobs.Hellhound.ValidSpawnWorlds");

        CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED = config.getBoolean("CustomMobs.AlphaHellhound.Enabled");
        CUSTOM_MOB_ALPHA_HELLHOUND_SPAWNCHANCE = config.getInt("CustomMobs.AlphaHellhound.SpawnChance");
        CUSTOM_MOB_ALPHA_HELLHOUND_SPAWN_WORLDS = config.getStringList("CustomMobs.AlphaHellhound.ValidSpawnWorlds");

        CUSTOM_MOB_LOST_SOUL_ENABLED = config.getBoolean("CustomMobs.LostSoul.Enabled");
        CUSTOM_MOB_LOST_SOUL_SPAWNCHANCE = config.getInt("CustomMobs.LostSoul.SpawnChance");
        CUSTOM_MOB_LOST_SOUL_SPAWN_WORLDS = config.getStringList("CustomMobs.LostSoul.ValidSpawnWorlds");

        CUSTOM_MOB_ZOMBIFIED_DEMON_ENABLED = config.getBoolean("CustomMobs.ZombifiedDemon.Enabled");
        CUSTOM_MOB_ZOMBIFIED_DEMON_SPAWNCHANCE = config.getInt("CustomMobs.ZombifiedDemon.SpawnChance");
        CUSTOM_MOB_ZOMBIFIED_DEMON_SPAWN_WORLDS = config.getStringList("CustomMobs.ZombifiedDemon.ValidSpawnWorlds");

        CUSTOM_MOB_TANK_ENABLED = config.getBoolean("CustomMobs.Tank.Enabled");
        CUSTOM_MOB_TANK_SPAWNCHANCE = config.getInt("CustomMobs.Tank.SpawnChance");
        CUSTOM_MOB_TANK_SPAWN_WORLDS = config.getStringList("CustomMobs.Tank.ValidSpawnWorlds");

        CUSTOM_MOB_ANCIENT_PROTECTOR_ENABLED = config.getBoolean("CustomMobs.AncientProtector.Enabled");
        CUSTOM_MOB_ANCIENT_PROTECTOR_SPAWNCHANCE = config.getInt("CustomMobs.AncientProtector.SpawnChance");
        CUSTOM_MOB_ANCIENT_PROTECTOR_SPAWN_WORLDS = config.getStringList("CustomMobs.AncientProtector.ValidSpawnWorlds");

        CUSTOM_MOB_NETHERITE_GOLEM_ENABLED = config.getBoolean("CustomMobs.NetheriteGolem.Enabled");
        CUSTOM_MOB_NETHERITE_GOLEM_SPAWNCHANCE = config.getInt("CustomMobs.NetheriteGolem.SpawnChance");
        CUSTOM_MOB_NETHERITE_GOLEM_SPAWN_WORLDS = config.getStringList("CustomMobs.NetheriteGolem.ValidSpawnWorlds");

        RESOURCE_PACK_ENABLED = config.getBoolean("ResourcePack.Enabled");
        RESOURCE_PACK_URL = config.getString("ResourcePack.URL");
        RESOURCE_PACK_JOIN_MSG_ENABLED = config.getBoolean("ResourcePack.JoinMsgEnabled");
        RESOURCE_PACK_STATUS_MSGS_ENABLED = config.getBoolean("ResourcePack.StatusMsgsEnabled");
        RESOURCE_PACK_AUTO_UPDATE = config.getBoolean("ResourcePack.AutoUpdate");
        FORCE_RESOURCE_PACK = config.getBoolean("ResourcePack.Force");

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


        ITEM_MODEL_DATAS = new HashMap<>();
        ConfigurationSection itemModelDatasSection = config.getConfigurationSection("ItemModelDatas");
        for (String key : itemModelDatasSection.getKeys(false)) {
            ITEM_MODEL_DATAS.put(key, itemModelDatasSection.getInt(key));
        }
    }
}
