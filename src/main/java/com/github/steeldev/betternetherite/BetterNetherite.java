package com.github.steeldev.betternetherite;

import com.github.steeldev.betternetherite.commands.admin.BetterNetheriteReload;
import com.github.steeldev.betternetherite.commands.admin.GiveBNItem;
import com.github.steeldev.betternetherite.commands.admin.KillAllBNMobs;
import com.github.steeldev.betternetherite.commands.admin.SpawnBNMob;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.listeners.blocks.AncientDebris;
import com.github.steeldev.betternetherite.listeners.blocks.SmithingTable;
import com.github.steeldev.betternetherite.listeners.events.NetheriteFishing;
import com.github.steeldev.betternetherite.listeners.events.PlayerJoin;
import com.github.steeldev.betternetherite.listeners.items.ReinforcedItem;
import com.github.steeldev.betternetherite.managers.BNItemManager;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import com.github.steeldev.betternetherite.managers.BNShrineManager;
import com.github.steeldev.betternetherite.managers.RecipeManager;
import com.github.steeldev.betternetherite.util.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BetterNetherite extends JavaPlugin {
    private static final Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f0-9]){6}>");
    public static BetterNetherite instance;
    public BetterConfig config = null;
    public Lang lang = null;
    public Random rand = new Random();
    public boolean outdated;
    public String newVersion;

    public static BetterNetherite getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        loadCustomConfigs();
        registerBlockListeners();
        registerItemListeners();
        registerEventListeners();
        BNShrineManager.registerShrines();
        BNItemManager.registerCustomItems();
        BNMobManager.registerCustomMobs();
        registerCommands();
        RecipeManager.RegisterRecipes();

        getLogger().info(colorize("&6Better &7Netherite &eVersion " + getDescription().getVersion() + " &aenabled!"));

        checkForNewVersion();
    }

    @Override
    public void onDisable() {
        getLogger().info(colorize("&6Better &7Netherite &eVersion " + getDescription().getVersion() + " &cdisabled!"));
    }

    public void checkForNewVersion() {
        getLogger().info(colorize("&e&oChecking for plugin update..."));
        new UpdateChecker(this, 84526).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                outdated = false;
                getLogger().info(colorize("&2&oYou are on the latest version of &6&oBetter &7&oNetherite&2&o! &7&o(" + version + ")"));
            } else {
                outdated = true;
                newVersion = version;
                getLogger().info(colorize("&a&oA new version of &6&oBetter &7&oNetherite&a&o is available! &7&o(Current: " + this.getDescription().getVersion() + ", Latest: " + version + ")"));
            }
        });
    }

    public void loadCustomConfigs() {
        this.lang = new Lang(this);
        this.config = new BetterConfig(this);
    }

    public void registerCommands() {
        this.getCommand("betternetheritereload").setExecutor(new BetterNetheriteReload());

        if (BNMobManager.getValidMobList().size() > 0) {
            this.getCommand("spawnbetternetheritemob").setExecutor(new SpawnBNMob());
            this.getCommand("killallbetternetheritemobs").setExecutor(new KillAllBNMobs());
        }
        if (BNItemManager.getValidItemList().size() > 0) {
            this.getCommand("givebetternetheriteitem").setExecutor(new GiveBNItem());
        }
    }

    public void registerEventListeners() {
        getServer().getPluginManager().registerEvents(new NetheriteFishing(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    public void registerBlockListeners() {
        getServer().getPluginManager().registerEvents(new SmithingTable(), this);
        getServer().getPluginManager().registerEvents(new AncientDebris(), this);
    }


    public void registerItemListeners() {
        if (BetterConfig.CRIMSON_NETHERITE_SHRINE_ENABLED)
            getServer().getPluginManager().registerEvents(new ReinforcedItem(), this);
    }

    public String colorize(String string) {
        Matcher matcher = HEX_PATTERN.matcher(string);
        while (matcher.find()) {
            final net.md_5.bungee.api.ChatColor hexColor = net.md_5.bungee.api.ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
            final String before = string.substring(0, matcher.start());
            final String after = string.substring(matcher.end());
            string = before + hexColor + after;
            matcher = HEX_PATTERN.matcher(string);
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public boolean chanceOf(int chance) {
        return rand.nextInt(100) < chance;
    }

    public String formalizedString(String string) {
        String[] itemSplit = string.toLowerCase().split("_");
        StringBuilder finalIt = new StringBuilder();
        for (int i = 0; i < itemSplit.length; i++) {
            finalIt.append(itemSplit[i].substring(0, 1).toUpperCase() + itemSplit[i].substring(1));
            if (i < itemSplit.length - 1)
                finalIt.append(" ");
        }
        return finalIt.toString();
    }
}
