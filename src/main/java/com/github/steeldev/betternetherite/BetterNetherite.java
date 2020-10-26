package com.github.steeldev.betternetherite;

import com.github.steeldev.betternetherite.commands.admin.*;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.listeners.blocks.AncientDebris;
import com.github.steeldev.betternetherite.listeners.blocks.SmithingTable;
import com.github.steeldev.betternetherite.listeners.events.NetheriteFishing;
import com.github.steeldev.betternetherite.listeners.events.PlayerJoin;
import com.github.steeldev.betternetherite.listeners.inventory.BNItemListInventory;
import com.github.steeldev.betternetherite.listeners.items.ReinforcedItem;
import com.github.steeldev.betternetherite.listeners.world.BNWorldListener;
import com.github.steeldev.betternetherite.managers.BNItemManager;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import com.github.steeldev.betternetherite.managers.BNShrineManager;
import com.github.steeldev.betternetherite.managers.RecipeManager;
import com.github.steeldev.betternetherite.util.BNLogger;
import com.github.steeldev.betternetherite.util.UpdateChecker;
import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class BetterNetherite extends JavaPlugin {
    public static BetterNetherite instance;
    public BetterConfig config = null;
    public Lang lang = null;
    public boolean outdated;
    public String newVersion;

    public Logger logger;

    public static BetterNetherite getInstance() {
        return instance;
    }

    @Override
    public @NotNull Logger getLogger() {
        if (logger == null) logger = BNLogger.getLogger();
        return this.logger;
    }

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;

        MinecraftVersion.logger = getLogger();

        loadNBTAPI();

        loadCustomConfigs();
        BNMobManager.init();
        registerEventListeners();
        registerBlockListeners();
        registerItemListeners();
        BNShrineManager.registerShrines();
        BNItemManager.registerCustomItems();
        BNMobManager.registerCustomMobs();
        registerCommands();
        RecipeManager.RegisterRecipes();
        registerInventoryListeners();

        enableMetrics();

        getLogger().info(String.format("&aSuccessfully enabled &2%s &ain &e%s Seconds&a.", getDescription().getVersion(), (float) (System.currentTimeMillis() - start) / 1000));

        checkForNewVersion();
    }

    @Override
    public void onDisable() {
        getLogger().info("&cSuccessfully disabled!");
    }

    public void checkForNewVersion() {
        getLogger().info("&e&oChecking for a new version...");
        new UpdateChecker(this, 84526).getVersion(version -> {
            int latestVersion = Integer.parseInt(version.replaceAll("\\.", ""));
            int currentVersion = Integer.parseInt(this.getDescription().getVersion().replaceAll("\\.", ""));

            if (currentVersion == latestVersion) {
                outdated = false;
                getLogger().info(String.format("&2&oYou are on the latest version! &7&o(%s)", version));
            } else if (currentVersion > latestVersion) {
                outdated = false;
                getLogger().info(String.format("&e&oYou are on an in-dev preview version! &7&o(%s)", this.getDescription().getVersion()));
            } else {
                outdated = true;
                newVersion = version;
                getLogger().info(String.format("&a&oA new version is available! &7&o(Current: %s, Latest: %s)", this.getDescription().getVersion(), version));
                getLogger().info("&a&oDownload it here: &e&ohttps://www.spigotmc.org/resources/better-netherite.84526/");
            }
        });
    }

    public void loadNBTAPI() {
        getLogger().info("&aLoading NBT-API...");
        NBTItem loadingItem = new NBTItem(new ItemStack(Material.STONE));
        loadingItem.mergeCompound(new NBTContainer("{}"));
        getLogger().info("&aSuccessfully loaded NBT-API!");
    }

    public void loadCustomConfigs() {
        this.lang = new Lang(this);
        this.config = new BetterConfig(this);
    }

    public void enableMetrics() {
        Metrics metrics = new Metrics(this, 9202);

        if (metrics.isEnabled())
            getLogger().info("&7Starting Metrics. Opt-out using the global bStats config.");
    }

    public void registerCommands() {
        this.getCommand("betternetheritereload").setExecutor(new BetterNetheriteReload());

        if (BNMobManager.getValidMobList().size() > 0) {
            this.getCommand("spawnbetternetheritemob").setExecutor(new SpawnBNMob());
            this.getCommand("killallbetternetheritemobs").setExecutor(new KillAllBNMobs());
        }
        if (BNItemManager.getValidItemList().size() > 0) {
            this.getCommand("listbetternetheriteitems").setExecutor(new ListBNItems());
            this.getCommand("givebetternetheriteitem").setExecutor(new GiveBNItem());
        }
    }

    public void registerInventoryListeners(){
        if (BNItemManager.getValidItemList().size() > 0) {
            getServer().getPluginManager().registerEvents(new BNItemListInventory(), this);
        }
    }

    public void registerEventListeners() {
        getServer().getPluginManager().registerEvents(new BNWorldListener(), this);
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
}
