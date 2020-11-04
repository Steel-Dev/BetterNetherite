package com.github.steeldev.betternetherite.config;

import com.github.steeldev.betternetherite.BetterNetherite;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Lang {
    public static String PREFIX;
    public static String NETHERITE_UPGRADING_DISABLE_MSG;
    public static String NOT_ENOUGH_MATS_UPGRADE_MSG;
    public static String UPGRADE_SUCCESS_MSG;
    public static String SHRINE_USED_MSG;
    public static String POTION_SHRINE_USED_MSG;
    public static String SHRINE_BUILT_INCORRECT_MSG;
    public static String SHRINE_CHARGES_MSG;
    public static String SHRINE_CHARGES_LOW_MSG;
    public static String SHRINE_INVALID_ITEM_MSG;
    public static String SHRINE_ITEM_ALREADY_EFFECTED_MSG;
    public static String SHRINE_ITEM_FULL_DUR_MSG;
    public static String SHRINE_NO_CHARGES_MSG;
    public static String SHRINE_CANT_USE_IN_WORLD_MSG;
    public static String PLAYERS_ONLY_MSG;
    public static String CUSTOM_MOB_INVALID_MSG;
    public static String CUSTOM_MOB_SPAWNED_MSG;
    public static String CUSTOM_MOB_SPAWN_FAILED_MSG;
    public static String CUSTOM_MOBS_KILLED_MSG;
    public static String CUSTOM_MOBS_KILL_FAILED_MSG;
    public static String CUSTOM_MOB_REGISTERED_MSG;
    public static String CUSTOM_ITEM_GIVEN_MSG;
    public static String CUSTOM_ITEM_PLAYER_INVENTORY_FULL_MSG;
    public static String CUSTOM_ITEM_INVALID_MSG;
    public static String INVALID_PLAYER_MSG;
    static BetterNetherite main = BetterNetherite.getInstance();
    private final BetterNetherite plugin;
    private FileConfiguration lang;
    private File langFile;

    public Lang(BetterNetherite plugin) {
        this.plugin = plugin;
        loadLangFile();
    }

    private void loadLangFile() {
        main.getLogger().info("&7Loading lang file for " + BetterConfig.SELECTED_LANGUAGE);
        if (langFile == null) {
            langFile = new File(plugin.getDataFolder(), "Lang.yml");
        }
        if (!langFile.exists()) {
            plugin.saveResource("Lang.yml", false);
        }
        lang = YamlConfiguration.loadConfiguration(langFile);
        matchLangFile();
        loadLang();
    }

    // Used to update lang
    @SuppressWarnings("ConstantConditions")
    private void matchLangFile() {
        try {
            boolean hasUpdated = false;
            InputStream stream = plugin.getResource(langFile.getName());
            assert stream != null;
            InputStreamReader is = new InputStreamReader(stream);
            YamlConfiguration defLand = YamlConfiguration.loadConfiguration(is);
            for (String key : defLand.getConfigurationSection("").getKeys(true)) {
                if (!lang.contains(key)) {
                    lang.set(key, defLand.get(key));
                    hasUpdated = true;
                }
            }
            for (String key : lang.getConfigurationSection("").getKeys(true)) {
                if (!defLand.contains(key)) {
                    lang.set(key, null);
                    hasUpdated = true;
                }
            }
            if (hasUpdated)
                lang.save(langFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLang() {
        PREFIX = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".Prefix");
        NETHERITE_UPGRADING_DISABLE_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".NetheriteUpgradingDisabledMsg");
        NOT_ENOUGH_MATS_UPGRADE_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".NotEnoughMatsToUpgradeMsg");
        UPGRADE_SUCCESS_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".UpgradeSuccessMsg");

        SHRINE_USED_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".ShrineUsedMsg");
        POTION_SHRINE_USED_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".PotionShrineUsedMsg");
        SHRINE_BUILT_INCORRECT_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".ShrineBuiltIncorrectlyMsg");
        SHRINE_CHARGES_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".ShrineChargesMsg");
        SHRINE_CHARGES_LOW_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".ShrineLowOnChargeMsg");
        SHRINE_INVALID_ITEM_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".ShrineInvalidItemMsg");
        SHRINE_ITEM_ALREADY_EFFECTED_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".ShrineItemAlreadyEffectedMsg");
        SHRINE_ITEM_FULL_DUR_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".ShrineItemDurabilityFull");
        SHRINE_NO_CHARGES_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".ShrineNoChargesMsg");
        SHRINE_CANT_USE_IN_WORLD_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".ShrineCantBeUsedInWorld");

        PLAYERS_ONLY_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".PlayersOnlyMsg");

        CUSTOM_MOB_INVALID_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".InvalidBNMobMsg");
        CUSTOM_MOB_SPAWNED_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".BNMobSpawnedMsg");
        CUSTOM_MOB_SPAWN_FAILED_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".BNMobSpawnFailed");
        CUSTOM_MOBS_KILLED_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".BNMobsKilled");
        CUSTOM_MOBS_KILL_FAILED_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".NoBNMobsSpawned");
        CUSTOM_MOB_REGISTERED_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".BNMobRegistered");

        CUSTOM_ITEM_GIVEN_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".BNItemGivenMsg");
        CUSTOM_ITEM_PLAYER_INVENTORY_FULL_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".BNInventoryFullMsg");
        CUSTOM_ITEM_INVALID_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".BNItemInvalidMsg");

        INVALID_PLAYER_MSG = lang.getString(BetterConfig.SELECTED_LANGUAGE + ".PlayerNotOnline");
    }
}
