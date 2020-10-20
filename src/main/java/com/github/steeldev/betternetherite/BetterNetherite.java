package com.github.steeldev.betternetherite;

import com.github.steeldev.betternetherite.commands.admin.BetterNetheriteReload;
import com.github.steeldev.betternetherite.commands.admin.KillAllBNMobs;
import com.github.steeldev.betternetherite.commands.admin.SpawnBNMob;
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
import com.github.steeldev.betternetherite.util.DeathExplosionInfo;
import com.github.steeldev.betternetherite.util.HitEffect;
import com.github.steeldev.betternetherite.util.ItemChance;
import com.github.steeldev.betternetherite.util.RidingInfo;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

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
        registerBlockListeners();
        registerItemListeners();
        registerEventListeners();
        registerCustomMobs();
        registerCommands();
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
                    "<#571664>Netherite <#3c1a4c>Marauder", // Display name
                    EntityType.WITHER_SKELETON, // Mob to replace
                    EntityType.WITHER_SKELETON, // Replacement entity type
                    new RidingInfo(EntityType.SKELETON_HORSE, 40, Arrays.asList(Material.IRON_HORSE_ARMOR,Material.GOLDEN_HORSE_ARMOR), 20), // Should this mob have a chance of riding another?
                    false, // Is the mob angry? ONLY WORKS IF BASE ENTITY TYPE IS WOLF!
                    20, // EXP on death
                    80, // Mobs Max HP
                    0.2f, // Mobs move speed
                    10, // Mobs spawn chance
                    Collections.singletonList(World.Environment.NETHER), // Valid environments the mob can spawn in
                    Collections.singletonList(new HitEffect(PotionEffectType.BLINDNESS, 30, 1, 60)), // Hit effects
                    new DeathExplosionInfo(false, 0, 0, false), // Explode on death info
                    Arrays.asList(Material.BONE,Material.COAL), // List of drops to remove from this mob
                    Arrays.asList(new ItemChance(Material.NETHERITE_SCRAP, 3, 12), new ItemChance(Material.NETHERITE_INGOT, 2, 8), new ItemChance(Material.BONE, 2, 60, "<#3c1a4c>Marauder Bone", Collections.singletonList("&7A Netherite Marauder's bone."))), // Drops
                    Arrays.asList(new ItemChance(Material.NETHERITE_SWORD, 0.1f, true,"<#3c1a4c>Marauder Sword", Collections.singletonList("&7A Netherite Marauder's sword.")), null, null, new ItemChance(Material.NETHERITE_CHESTPLATE, 0.05f, true,"<#3c1a4c>Marauder Chestplate", Collections.singletonList("&7A Netherite Marauder's chestplate.")), null, null))); // Equipment
        }
        if(BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED) {
            BNMobManager.registerNewBNMob(new BNMob("netherite_marauder_brute",
                    "<#571664>Netherite <#3c1a4c>Marauder Brute",
                    EntityType.WITHER_SKELETON,
                    EntityType.WITHER_SKELETON,
                    new RidingInfo(EntityType.ZOMBIE_HORSE, 40, Arrays.asList(Material.DIAMOND_HORSE_ARMOR,Material.IRON_HORSE_ARMOR), 20),
                    false,
                    20,
                    85,
                    0.3f,
                    8,
                    Collections.singletonList(World.Environment.NETHER),
                    Collections.singletonList(new HitEffect(PotionEffectType.BLINDNESS, 30, 1, 60)),
                    new DeathExplosionInfo(false, 0, 0, false),
                    Arrays.asList(Material.BONE,Material.COAL),
                    Arrays.asList(new ItemChance(Material.NETHERITE_SCRAP, 3, 12), new ItemChance(Material.NETHERITE_INGOT, 2, 8), new ItemChance(Material.BONE, 2, 60, "<#3c1a4c>Marauder Bone", Collections.singletonList("&7A Netherite Marauder's bone."))),
                    Arrays.asList(new ItemChance(Material.NETHERITE_AXE, 0.1f, true,"<#3c1a4c>Marauder Axe", Collections.singletonList("&7A Netherite Marauder's axe.")), null, null, new ItemChance(Material.NETHERITE_CHESTPLATE, 0.05f, true,"<#3c1a4c>Marauder Chestplate", Collections.singletonList("&7A Netherite Marauder's chestplate.")), null, null)));
        }
        if (BetterConfig.CUSTOM_MOB_HELLHOUND_ENABLED) {
            BNMobManager.registerNewBNMob(new BNMob("hellhound",
                    "<#571664>Hellhound",
                    EntityType.ZOMBIFIED_PIGLIN,
                    EntityType.WOLF,
                    new RidingInfo(null, 0),
                    true,
                    10,
                    10,
                    0.4f,
                    12,
                    Collections.singletonList(World.Environment.NETHER),
                    Collections.singletonList(new HitEffect(PotionEffectType.WITHER, 30, 1, 60)),
                    new DeathExplosionInfo(false, 0, 0, false),
                    null,
                    Arrays.asList(new ItemChance(Material.ROTTEN_FLESH, 3, 30, "<#2d2501>Rotted Wolf Meat", null), new ItemChance(Material.PORKCHOP, 3, 40, "<#915103>Wolf Meat", null)),
                    Arrays.asList(null, null, null, null, null, null)));
        }
        if(BetterConfig.CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED) {
            BNMobManager.registerNewBNMob(new BNMob("alpha_hellhound",
                    "<#571664>Alpha Hellhound",
                    EntityType.ZOMBIFIED_PIGLIN,
                    EntityType.WOLF,
                    new RidingInfo(null, 0),
                    true,
                    20,
                    20,
                    0.5f,
                    8,
                    Collections.singletonList(World.Environment.NETHER),
                    Arrays.asList(new HitEffect(PotionEffectType.WITHER, 30, 1, 60), new HitEffect(PotionEffectType.POISON, 70, 1, 50)),
                    new DeathExplosionInfo(false, 0, 0, false),
                    null,
                    Arrays.asList(new ItemChance(Material.ROTTEN_FLESH, 3, 30, "<#2d2501>Rotted Wolf Meat", null), new ItemChance(Material.PORKCHOP, 4, 40, "<#915103>Wolf Meat", null)),
                    Arrays.asList(null, null, null, null, null, null)));
        }
    }

    public void registerCommands() {
        this.getCommand("betternetheritereload").setExecutor(new BetterNetheriteReload());

        if(BNMobManager.getValidMobList().size() > 0) {
            this.getCommand("spawnbetternetheritemob").setExecutor(new SpawnBNMob());
            this.getCommand("killallbetternetheritemobs").setExecutor(new KillAllBNMobs());
        }
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
