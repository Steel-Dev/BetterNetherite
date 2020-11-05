package com.github.steeldev.betternetherite;

import com.github.steeldev.betternetherite.commands.admin.BetterNetheriteReload;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.listeners.blocks.AncientDebris;
import com.github.steeldev.betternetherite.listeners.blocks.SmithingTable;
import com.github.steeldev.betternetherite.listeners.events.NetheriteFishing;
import com.github.steeldev.betternetherite.listeners.events.PlayerJoin;
import com.github.steeldev.betternetherite.listeners.items.ReinforcedItem;
import com.github.steeldev.betternetherite.managers.*;
import com.github.steeldev.betternetherite.util.BNLogger;
import com.github.steeldev.betternetherite.util.UpdateChecker;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class BetterNetherite extends JavaPlugin {
    private static BetterNetherite instance;
    public BetterConfig config = null;
    public Lang lang = null;
    public boolean outdated;
    public String newVersion;

    public Plugin monstrorvmPlugin;

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

        MinecraftVersion.replaceLogger(getLogger());

        loadNBTAPI();

        loadCustomConfigs();
        try {
            BNResourcePackManager.checkResourcePack();
        } catch (IOException e) {
            e.printStackTrace();
        }

        registerEventListeners();
        registerBlockListeners();
        registerItemListeners();
        BNShrineManager.registerShrines();
        if (loadMonstrorvm() != null) {
            monstrorvmPlugin = loadMonstrorvm();
            if (monstrorvmPlugin.isEnabled()) {
                getLogger().info("&aFound &2Monstrorvm " + monstrorvmPlugin.getDescription().getVersion() + "&a! Custom mobs and items enabled!");

                BNItemManager.registerCustomItems();
                BNMobManager.registerCustomMobs();
            } else
                getLogger().info("&cFound &2Monstrorvm " + monstrorvmPlugin.getDescription().getVersion() + ", but its disabled! Custom mobs and items disabled!");
        } else {
            getLogger().info("&cCould not find &2Monstrorvm &con the server! Custom mobs and items disabled!");
        }

        registerCommands();
        RecipeManager.RegisterRecipes();

        enableMetrics();


        getLogger().info(String.format("&aSuccessfully enabled &2%s &ain &e%s Seconds&a.", getDescription().getVersion(), (float) (System.currentTimeMillis() - start) / 1000));

        checkForNewVersion();
    }

    public Plugin loadMonstrorvm() {
        return Bukkit.getServer().getPluginManager().getPlugin("Monstrorvm");
    }

    @Override
    public void onDisable() {
        getLogger().info("&cSuccessfully disabled!");
        instance = null;
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
                getLogger().info("&e&ohttps://www.spigotmc.org/resources/better-netherite.84526/");
            }
        });
    }

    public void loadNBTAPI() {
        getLogger().info("&aLoading NBT-API...");
        NBTItem loadingItem = new NBTItem(new ItemStack(Material.STONE));
        loadingItem.addCompound("Glob");
        loadingItem.setString("Glob", "yes");
        getLogger().info("&aSuccessfully loaded NBT-API!");
    }

    public void loadCustomConfigs() {
        this.config = new BetterConfig(this);
        this.lang = new Lang(this);
    }

    public void enableMetrics() {
        Metrics metrics = new Metrics(this, 9202);

        if (metrics.isEnabled()) {
            getLogger().info("&7Starting Metrics. Opt-out using the global bStats config.");
            metrics.addCustomChart(new Metrics.SimplePie("using_monstrorvm", new Callable<String>(){
                @Override
                public String call() throws Exception {
                    if(monstrorvmPlugin != null){
                        if(monstrorvmPlugin.isEnabled()){
                            return monstrorvmPlugin.getDescription().getVersion();
                        }
                    }
                    return "No Monstrorvm";
                }
            }));
        }
    }

    public void registerCommands() {
        this.getCommand("betternetheritereload").setExecutor(new BetterNetheriteReload());

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
}
