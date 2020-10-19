package com.github.steeldev.betternetherite;

import com.github.steeldev.betternetherite.commands.admin.BetterNetheriteReload;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.listeners.blocks.AncientDebris;
import com.github.steeldev.betternetherite.listeners.blocks.SmithingTable;
import com.github.steeldev.betternetherite.listeners.events.NetheriteFishing;
import com.github.steeldev.betternetherite.listeners.items.ReinforcedItem;
import com.github.steeldev.betternetherite.listeners.shrines.CrimsonShrine;
import com.github.steeldev.betternetherite.listeners.shrines.WarpedShrine;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import com.github.steeldev.betternetherite.managers.RecipeManager;
import com.github.steeldev.betternetherite.misc.BNMob;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BetterNetherite extends JavaPlugin {
    private static final Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f0-9]){6}>");
    public static BetterNetherite instance;
    public BetterConfig config = null;
    public Lang lang = null;
    public Random rand = new Random();

    public static BetterNetherite getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        loadCustomConfigs();
        registerCommands();
        registerBlockListeners();
        registerItemListeners();
        registerEventListeners();
        registerCustomMobs();
        RecipeManager.RegisterRecipes();

        getLogger().info(colorize(Lang.ENABLED_MSG.replace("VERSION", getDescription().getVersion())));
    }

    @Override
    public void onDisable() {
        getLogger().info(colorize(Lang.DISABLED_MSG.replace("VERSION", getDescription().getVersion())));
    }

    public void loadCustomConfigs() {
        this.lang = new Lang(this);
        this.config = new BetterConfig(this);
    }

    public void registerCustomMobs() {
        if (BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED) {
            BNMobManager.registerNewBNMob(new BNMob("netherite_marauder", // Key ID to access later
                    "<#571664>Netherite <#8a239f>Marauder", // Display name
                    EntityType.WITHER_SKELETON, // Mob to replace
                    EntityType.WITHER_SKELETON, // Replacement entity type
                    false, // Is the mob angry? ONLY WORKS IF BASE ENTITY TYPE IS WOLF!
                    20, // EXP on death
                    80, // Mobs Max HP
                    0.2f, // Mobs move speed
                    10, // Mobs spawn chance
                    Collections.singletonList(World.Environment.NETHER), // Valid environments the mob can spawn in
                    Collections.singletonList("BLINDNESS;30;1;60"), // Hit effects - EFFECT;CHANCE;AMPLIFIER;DURATION
                    "false;0;0;false", // Explode on death info - ENABLED;CHANCE;SIZE;CREATEFIRE
                    Arrays.asList("NETHERITE_SCRAP;3;2", "NETHERITE_INGOT;1;1"), // Drops - ITEM;MAXAMOUNT;CHANCE
                    Arrays.asList("NETHERITE_SWORD;0.1", "AIR;0", "AIR;0", "NETHERITE_CHESTPLATE;0.05", "AIR;0", "AIR;0"))); // Equipment - ITEM;DROPCHANCE
        }
        if (BetterConfig.CUSTOM_MOB_HELLHOUND_ENABLED) {
            BNMobManager.registerNewBNMob(new BNMob("hellhound",
                    "<#571664>Hellhound",
                    EntityType.ZOMBIFIED_PIGLIN,
                    EntityType.WOLF,
                    true,
                    10,
                    10,
                    0.4f,
                    12,
                    Collections.singletonList(World.Environment.NETHER),
                    Collections.singletonList("WITHER;30;1;60"),
                    "false;0;0;false",
                    Arrays.asList("ROTTEN_FLESH;3;30", "RAW_PORKCHOP;2;20"),
                    Arrays.asList("AIR;0", "AIR;0", "AIR;0", "AIR;0", "AIR;0", "AIR;0")));
        }
    }

    public void registerCommands() {
        this.getCommand("betternetheritereload").setExecutor(new BetterNetheriteReload());
    }

    public void registerEventListeners(){
        getServer().getPluginManager().registerEvents(new NetheriteFishing(), this);
    }

    public void registerBlockListeners() {
        getServer().getPluginManager().registerEvents(new SmithingTable(), this);
        getServer().getPluginManager().registerEvents(new AncientDebris(), this);

        if(BetterConfig.CRIMSON_NETHERITE_SHRINE_ENABLED)
            getServer().getPluginManager().registerEvents(new CrimsonShrine(), this);
        if(BetterConfig.WARPED_NETHERITE_SHRINE_ENABLED)
            getServer().getPluginManager().registerEvents(new WarpedShrine(), this);
    }

    public void registerItemListeners(){
        if(BetterConfig.CRIMSON_NETHERITE_SHRINE_ENABLED)
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

    public String formalizedString(String string){
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
