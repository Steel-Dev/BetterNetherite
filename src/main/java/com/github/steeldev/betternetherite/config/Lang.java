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
    public static String RELOAD_MSG;
    public static String RECIPE_REMOVED_MSG;
    public static String RECIPE_ADDED_MSG;
    public static String ENABLED_MSG;
    public static String DISABLED_MSG;
    public static String SHRINE_USED_MSG;
    public static String SHRINE_BUILT_INCORRECT_MSG;
    public static String SHRINE_CHARGES_MSG;
    public static String SHRINE_CHARGES_LOW_MSG;
    public static String SHRINE_INVALID_ITEM_MSG;
    public static String SHRINE_ITEM_ALREADY_EFFECTED_MSG;
    public static String SHRINE_ITEM_FULL_DUR_MSG;
    public static String SHRINE_NO_CHARGES_MSG;

    public static String PLAYERS_ONLY_MSG;

    public static String CUSTOM_MOB_INVALID_MSG;
    public static String CUSTOM_MOB_SPAWNED_MSG;
    public static String CUSTOM_MOB_SPAWN_FAILED_MSG;
    public static String CUSTOM_MOBS_KILLED_MSG;
    public static String CUSTOM_MOGS_KILL_FAILED_MSG;

    private final BetterNetherite plugin;
    private FileConfiguration lang;
    private File langFile;

    public Lang(BetterNetherite plugin) {
        this.plugin = plugin;
        loadLangFile();
    }

    private void loadLangFile() {
        if (langFile == null) {
            langFile = new File(plugin.getDataFolder(), "Messages.yml");
        }
        if (!langFile.exists()) {
            plugin.saveResource("Messages.yml", false);
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
        PREFIX = lang.getString("Prefix");
        NETHERITE_UPGRADING_DISABLE_MSG = lang.getString("NetheriteUpgradingDisabledMsg");
        NOT_ENOUGH_MATS_UPGRADE_MSG = lang.getString("NotEnoughMatsToUpgradeMsg");
        UPGRADE_SUCCESS_MSG = lang.getString("UpgradeSuccessMsg");
        RELOAD_MSG = lang.getString("ReloadMsg");
        RECIPE_REMOVED_MSG = lang.getString("RecipeRemovedMsg");
        RECIPE_ADDED_MSG = lang.getString("RecipeAddedMsg");
        ENABLED_MSG = lang.getString("EnabledMsg");
        DISABLED_MSG = lang.getString("DisabledMsg");

        SHRINE_USED_MSG = lang.getString("ShrineUsedMsg");
        SHRINE_BUILT_INCORRECT_MSG = lang.getString("ShrineBuiltIncorrectlyMsg");
        SHRINE_CHARGES_MSG = lang.getString("ShrineChargesMsg");
        SHRINE_CHARGES_LOW_MSG = lang.getString("ShrineLowOnChargeMsg");
        SHRINE_INVALID_ITEM_MSG = lang.getString("ShrineInvalidItemMsg");
        SHRINE_ITEM_ALREADY_EFFECTED_MSG = lang.getString("ShrineItemAlreadyEffectedMsg");
        SHRINE_ITEM_FULL_DUR_MSG = lang.getString("ShrineItemDurabilityFull");
        SHRINE_NO_CHARGES_MSG = lang.getString("ShrineNoChargesMsg");

        PLAYERS_ONLY_MSG = lang.getString("PlayersOnlyMsg");

        CUSTOM_MOB_INVALID_MSG = lang.getString("InvalidBNMobMsg");
        CUSTOM_MOB_SPAWNED_MSG = lang.getString("BNMobSpawnedMsg");
        CUSTOM_MOB_SPAWN_FAILED_MSG = lang.getString("BNMobSpawnFailed");
        CUSTOM_MOBS_KILLED_MSG = lang.getString("BNMobsKilled");
        CUSTOM_MOGS_KILL_FAILED_MSG = lang.getString("NoBNMobsSpawned");
    }
}
