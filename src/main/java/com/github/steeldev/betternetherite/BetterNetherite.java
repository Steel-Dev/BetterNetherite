package com.github.steeldev.betternetherite;

import com.github.steeldev.betternetherite.commands.admin.BetterNetheriteReload;
import com.github.steeldev.betternetherite.commands.admin.KillAllBNMobs;
import com.github.steeldev.betternetherite.commands.admin.SpawnBNMob;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.listeners.blocks.AncientDebris;
import com.github.steeldev.betternetherite.listeners.blocks.SmithingTable;
import com.github.steeldev.betternetherite.listeners.events.NetheriteFishing;
import com.github.steeldev.betternetherite.listeners.events.PlayerJoin;
import com.github.steeldev.betternetherite.listeners.items.ReinforcedItem;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import com.github.steeldev.betternetherite.managers.BNShrineManager;
import com.github.steeldev.betternetherite.managers.RecipeManager;
import com.github.steeldev.betternetherite.misc.BNMob;
import com.github.steeldev.betternetherite.misc.BNShrine;
import com.github.steeldev.betternetherite.util.UpdateChecker;
import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;
import com.github.steeldev.betternetherite.util.mobs.DeathExplosionInfo;
import com.github.steeldev.betternetherite.util.mobs.ItemChance;
import com.github.steeldev.betternetherite.util.mobs.MountInfo;
import com.github.steeldev.betternetherite.util.shrines.ShrineCharge;
import com.github.steeldev.betternetherite.util.shrines.ShrineCore;
import com.github.steeldev.betternetherite.util.shrines.ShrineEffect;
import com.github.steeldev.betternetherite.util.shrines.ShrineEffectType;
import org.bukkit.*;
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

    public boolean outdated;
    public String newVersion;

    @Override
    public void onEnable() {
        instance = this;

        loadCustomConfigs();
        registerBlockListeners();
        registerItemListeners();
        registerEventListeners();
        registerCustomMobs();
        registerShrines();
        registerCommands();
        RecipeManager.RegisterRecipes();

        getLogger().info(colorize("&6Better &7Netherite &eVersion "+getDescription().getVersion()+" &aenabled!"));

        checkForNewVersion();
    }

    @Override
    public void onDisable() {
        getLogger().info(colorize("&6Better &7Netherite &eVersion "+getDescription().getVersion()+" &cdisabled!"));
    }

    public void checkForNewVersion(){
        getLogger().info(colorize("&e&oChecking for plugin update..."));
        new UpdateChecker(this,84526).getVersion(version -> {
            if(this.getDescription().getVersion().equalsIgnoreCase(version)){
                outdated = false;
                getLogger().info(colorize("&2&oYou are on the latest version of &6&oBetter &7&oNetherite&2&o! &7&o("+version+")"));
            }else{
                outdated = true;
                newVersion = version;
                getLogger().info(colorize("&a&oA new version of &6&oBetter &7&oNetherite&a&o is available! &7&o(Current: "+this.getDescription().getVersion()+", Latest: " + version + ")"));
            }
        });
    }

    public void loadCustomConfigs() {
        this.lang = new Lang(this);
        this.config = new BetterConfig(this);
    }

    public void registerCustomMobs() {
        if (BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_ENABLED) {
            BNMobManager.registerNewBNMob(new BNMob("netherite_marauder", // Key ID to access later
                    "<#571664>Netherite <#3c1a4c>Marauder", // Display name
                    Collections.singletonList(EntityType.WITHER_SKELETON), // Mob to replace
                    EntityType.WITHER_SKELETON, // Replacement entity type
                    new MountInfo(EntityType.SKELETON_HORSE, 40, Arrays.asList(Material.IRON_HORSE_ARMOR,Material.GOLDEN_HORSE_ARMOR), 20), // Should this mob have a chance of riding another?
                    false, // Is the mob angry? ONLY WORKS IF BASE ENTITY TYPE IS WOLF!
                    20, // EXP on death
                    80, // Mobs Max HP
                    0.2f, // Mobs move speed
                    10, // Mobs spawn chance
                    Collections.singletonList(World.Environment.NETHER), // Valid environments the mob can spawn in
                    Collections.singletonList(new BNPotionEffect(PotionEffectType.BLINDNESS, 30, 1, 60)), // Hit effects
                    new DeathExplosionInfo(false, 0, 0, false), // Explode on death info
                    Arrays.asList(Material.BONE,Material.COAL), // List of drops to remove from this mob
                    Arrays.asList(new ItemChance(Material.NETHERITE_SCRAP, 3, 12), new ItemChance(Material.NETHERITE_INGOT, 2, 8), new ItemChance(Material.BONE, 2, 60, "<#3c1a4c>Marauder Bone", Collections.singletonList("&7A Netherite Marauder's bone."))), // Drops
                    Arrays.asList(new ItemChance(Material.NETHERITE_SWORD, 0.1f, true,"<#3c1a4c>Marauder Sword", Collections.singletonList("&7A Netherite Marauder's sword.")), null, null, new ItemChance(Material.NETHERITE_CHESTPLATE, 0.05f, true,"<#3c1a4c>Marauder Chestplate", Collections.singletonList("&7A Netherite Marauder's chestplate.")), null, null))); // Equipment
        }
        if(BetterConfig.CUSTOM_MOB_NETHERITE_MARAUDER_BRUTE_ENABLED) {
            BNMobManager.registerNewBNMob(new BNMob("netherite_marauder_brute",
                    "<#571664>Netherite <#3c1a4c>Marauder Brute",
                    Collections.singletonList(EntityType.WITHER_SKELETON),
                    EntityType.WITHER_SKELETON,
                    new MountInfo(EntityType.ZOMBIE_HORSE, 40, Arrays.asList(Material.DIAMOND_HORSE_ARMOR,Material.IRON_HORSE_ARMOR), 20),
                    false,
                    20,
                    85,
                    0.3f,
                    8,
                    Collections.singletonList(World.Environment.NETHER),
                    Collections.singletonList(new BNPotionEffect(PotionEffectType.BLINDNESS, 30, 1, 60)),
                    new DeathExplosionInfo(false, 0, 0, false),
                    Arrays.asList(Material.BONE,Material.COAL),
                    Arrays.asList(new ItemChance(Material.NETHERITE_SCRAP, 3, 12), new ItemChance(Material.NETHERITE_INGOT, 2, 8), new ItemChance(Material.BONE, 2, 60, "<#3c1a4c>Marauder Bone", Collections.singletonList("&7A Netherite Marauder's bone."))),
                    Arrays.asList(new ItemChance(Material.NETHERITE_AXE, 0.1f, true,"<#3c1a4c>Marauder Axe", Collections.singletonList("&7A Netherite Marauder's axe.")), null, null, new ItemChance(Material.NETHERITE_CHESTPLATE, 0.05f, true,"<#3c1a4c>Marauder Chestplate", Collections.singletonList("&7A Netherite Marauder's chestplate.")), null, null)));
        }
        if (BetterConfig.CUSTOM_MOB_HELLHOUND_ENABLED) {
            BNMobManager.registerNewBNMob(new BNMob("hellhound",
                    "<#571664>Hellhound",
                    Collections.singletonList(EntityType.ZOMBIFIED_PIGLIN),
                    EntityType.WOLF,
                    new MountInfo(null, 0),
                    true,
                    10,
                    10,
                    0.4f,
                    12,
                    Collections.singletonList(World.Environment.NETHER),
                    Collections.singletonList(new BNPotionEffect(PotionEffectType.WITHER, 30, 1, 60)),
                    new DeathExplosionInfo(false, 0, 0, false),
                    null,
                    Arrays.asList(new ItemChance(Material.ROTTEN_FLESH, 3, 30, "<#2d2501>Rotted Wolf Meat", null), new ItemChance(Material.PORKCHOP, 3, 40, "<#915103>Wolf Meat", null)),
                    Arrays.asList(null, null, null, null, null, null)));
        }
        if(BetterConfig.CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED) {
            BNMobManager.registerNewBNMob(new BNMob("alpha_hellhound",
                    "<#571664>Alpha Hellhound",
                    Collections.singletonList(EntityType.ZOMBIFIED_PIGLIN),
                    EntityType.WOLF,
                    new MountInfo(null, 0),
                    true,
                    20,
                    20,
                    0.5f,
                    8,
                    Collections.singletonList(World.Environment.NETHER),
                    Arrays.asList(new BNPotionEffect(PotionEffectType.WITHER, 30, 1, 60), new BNPotionEffect(PotionEffectType.POISON, 70, 1, 50)),
                    new DeathExplosionInfo(false, 0, 0, false),
                    null,
                    Arrays.asList(new ItemChance(Material.ROTTEN_FLESH, 3, 30, "<#2d2501>Rotted Wolf Meat", null), new ItemChance(Material.PORKCHOP, 4, 40, "<#915103>Wolf Meat", null)),
                    Arrays.asList(null, null, null, null, null, null)));
        }
        if(BetterConfig.CUSTOM_MOB_LOST_SOUL_ENABLED){
            BNMobManager.registerNewBNMob(new BNMob("lost_soul",
                    "<#118e9c>Lost Soul",
                    Collections.singletonList(EntityType.ZOMBIFIED_PIGLIN),
                    EntityType.VEX,
                    new MountInfo(null, 0),
                    false,
                    30,
                    10,
                    0.5f,
                    5,
                    Collections.singletonList(World.Environment.NETHER),
                    Arrays.asList(new BNPotionEffect(PotionEffectType.WITHER, 30, 1, 60), new BNPotionEffect(PotionEffectType.POISON, 70, 1, 50), new BNPotionEffect(PotionEffectType.BLINDNESS,50,1,50)),
                    new DeathExplosionInfo(true, 90, 2, true),
                    null,
                    Collections.singletonList(new ItemChance(Material.GHAST_TEAR, 3, 5, "<#2d2501>Soul", null)),
                    Arrays.asList(null, null, null, null, null, null)));
        }
        if(BetterConfig.CUSTOM_MOB_ZOMBIFIED_DEMON_ENABLED){
            BNMobManager.registerNewBNMob(new BNMob("zombified_demon",
                    "<#0f853c>Zombified Demon",
                    Arrays.asList(EntityType.ZOMBIFIED_PIGLIN,EntityType.ZOMBIE),
                    EntityType.ZOMBIE,
                    new MountInfo(null, 0),
                    false,
                    30,
                    16,
                    0.4f,
                    5,
                    Arrays.asList(World.Environment.NETHER, World.Environment.NORMAL),
                    Arrays.asList(new BNPotionEffect(PotionEffectType.HUNGER, 30, 1, 60), new BNPotionEffect(PotionEffectType.POISON, 70, 1, 50)),
                    new DeathExplosionInfo(false, 0, 0, false),
                    Collections.singletonList(Material.ROTTEN_FLESH),
                    Collections.singletonList(new ItemChance(Material.ROTTEN_FLESH, 3, 30, "<#2d2501>Rotten Demon Flesh", null)),
                    Arrays.asList(null, null, new ItemChance(Material.IRON_HELMET,0.3f,true), null, null, null)));
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
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    public void registerBlockListeners() {
        getServer().getPluginManager().registerEvents(new SmithingTable(), this);
        getServer().getPluginManager().registerEvents(new AncientDebris(), this);
    }

    public void registerShrines(){
        if(BetterConfig.CRIMSON_NETHERITE_SHRINE_ENABLED) {
            BNShrineManager.registerNewShrine(new BNShrine("crimson_shrine",
                    "<#aa1313>Crimson <#560a57>Shrine",
                    new ShrineCore(Material.CRIMSON_STEM,
                            Material.CRIMSON_FENCE),
                    new ShrineCharge(Sound.BLOCK_REDSTONE_TORCH_BURNOUT,
                            Material.SOUL_LANTERN,
                            Particle.FLAME),
                    new ShrineEffect(ShrineEffectType.REINFORCE_ITEM,
                            Sound.BLOCK_ANVIL_USE,
                            Sound.BLOCK_ANVIL_BREAK,
                            Sound.BLOCK_BEACON_DEACTIVATE,
                            Effect.ZOMBIE_DESTROY_DOOR,
                            Particle.EXPLOSION_NORMAL,
                            "<#600b0b>Hells Reinforcement",
                            "<#11349c>REINFORCED",
                            "<#11349c>Reinforced",
                            Particle.ENCHANTMENT_TABLE),
                    Arrays.asList(World.Environment.NORMAL, World.Environment.NETHER),
                    40,
                    true));
        }
        if(BetterConfig.WARPED_NETHERITE_SHRINE_ENABLED) {
            BNShrineManager.registerNewShrine(new BNShrine("warped_shrine",
                    "<#119c95>Warped <#560a57>Shrine",
                    new ShrineCore(Material.WARPED_STEM,
                            Material.WARPED_FENCE),
                    new ShrineCharge(Sound.BLOCK_REDSTONE_TORCH_BURNOUT,
                            Material.SOUL_LANTERN,
                            Particle.FLAME),
                    new ShrineEffect(ShrineEffectType.HEAL_ITEM,
                            Sound.BLOCK_ANVIL_USE,
                            Sound.BLOCK_ANVIL_BREAK,
                            Sound.BLOCK_BEACON_DEACTIVATE,
                            Effect.ZOMBIE_DESTROY_DOOR,
                            Particle.EXPLOSION_NORMAL,
                            "<#600b0b>Hells Mend",
                            "<#11349c>Mended",
                            Particle.ENCHANTMENT_TABLE),
                    Arrays.asList(World.Environment.NORMAL, World.Environment.NETHER),
                    40,
                    true));
        }
        if(BetterConfig.PRISMARINE_NETHERITE_SHRINE_ENABLED) {
            BNShrineManager.registerNewShrine(new BNShrine("prismarine_shrine",
                    "<#1297a1>Prismarine <#560a57>Shrine",
                    new ShrineCore(Material.PRISMARINE,
                            Material.PRISMARINE_WALL),
                    new ShrineCharge(Sound.BLOCK_REDSTONE_TORCH_BURNOUT,
                            Material.SOUL_TORCH,
                            Particle.FLAME),
                    new ShrineEffect(ShrineEffectType.APPLY_POTION_EFFECT,
                            Sound.BLOCK_BEACON_ACTIVATE,
                            Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE,
                            Sound.BLOCK_BEACON_DEACTIVATE,
                            Effect.ZOMBIE_DESTROY_DOOR,
                            Particle.EXPLOSION_NORMAL,
                            "<#1297a1>Heavens Grace",
                            "<#11349c>Graced",
                            Particle.ENCHANTMENT_TABLE,
                            Arrays.asList(new BNPotionEffect(PotionEffectType.REGENERATION,30,1,3000),
                                    new BNPotionEffect(PotionEffectType.DAMAGE_RESISTANCE,10,2,3000),
                                    new BNPotionEffect(PotionEffectType.FAST_DIGGING,16,2,3000),
                                    new BNPotionEffect(PotionEffectType.HEALTH_BOOST,23,3,3000),
                                    new BNPotionEffect(PotionEffectType.FIRE_RESISTANCE,21,2,3000))),
                    Collections.singletonList(World.Environment.NORMAL),
                    80,
                    false));
        }
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
