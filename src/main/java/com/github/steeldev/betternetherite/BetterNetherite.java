package com.github.steeldev.betternetherite;

import com.github.steeldev.betternetherite.commands.admin.BetterNetheriteReload;
import com.github.steeldev.betternetherite.config.Config;
import com.github.steeldev.betternetherite.listeners.blocks.AncientDebris;
import com.github.steeldev.betternetherite.listeners.blocks.SmithingTable;
import com.github.steeldev.betternetherite.listeners.events.NetheriteFishing;
import com.github.steeldev.betternetherite.listeners.events.PlayerJoin;
import com.github.steeldev.betternetherite.listeners.items.ReinforcedItem;
import com.github.steeldev.betternetherite.listeners.items.UpgradePack;
import com.github.steeldev.betternetherite.listeners.mob.NetheriteGolem;
import com.github.steeldev.betternetherite.managers.*;
import com.github.steeldev.betternetherite.util.BNLogger;
import com.github.steeldev.betternetherite.util.Message;
import com.github.steeldev.betternetherite.util.UpdateChecker;
import com.github.steeldev.betternetherite.util.Util;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.utils.MinecraftVersion;
import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getPluginManager;

public class BetterNetherite extends JavaPlugin {
    private static BetterNetherite instance;
    public Config config = null;
    public UpdateChecker versionManager;

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
        Util.main = instance;

        MinecraftVersion.replaceLogger(getLogger());

        loadNBTAPI();

        loadConfigurations();
        try {
            BNResourcePackManager.checkResourcePack();
        } catch (IOException e) {
            e.printStackTrace();
        }

        registerEvents();
        BNShrineManager.registerShrines();

        registerCommands();
        RecipeManager.RegisterRecipes();

        if (loadMonstrorvm() != null) {
            monstrorvmPlugin = loadMonstrorvm();
            if (monstrorvmPlugin.isEnabled()) {
                Message.MONSTRORVM_FOUND.log(monstrorvmPlugin.getDescription().getVersion());
                BNItemManager.registerCustomItems();
                BNMobManager.registerCustomMobs();
            } else
                Message.MONSTRORVM_FOUND_DISABLED.log(monstrorvmPlugin.getDescription().getVersion());
        } else
            Message.MONSTRORVM_NOT_FOUND.log();

        enableMetrics();

        Message.PLUGIN_ENABLED.log(getDescription().getVersion(), (float) (System.currentTimeMillis() - start) / 1000);

        versionManager = new UpdateChecker(this, 84526);
        versionManager.checkForNewVersion();
    }

    public Plugin loadMonstrorvm() {
        return getPluginManager().getPlugin("Monstrorvm");
    }

    @Override
    public void onDisable() {
        Message.PLUGIN_DISABLED.log();
        instance = null;
    }

    public void loadNBTAPI() {
        Message.LOADING_NBT_API.log();
        NBTItem loadingItem = new NBTItem(new ItemStack(Material.STONE));
        loadingItem.addCompound("Glob");
        loadingItem.setString("Glob", "yes");
        Message.NBT_API_LOADED.log();
    }

    public void loadConfigurations() {
        config = new Config(this);
    }

    public void enableMetrics() {
        Metrics metrics = new Metrics(this, 9202);

        if (metrics.isEnabled()) {
            Message.STARTING_METRICS.log();
            metrics.addCustomChart(new Metrics.SimplePie("using_monstrorvm", () -> {
                if (monstrorvmPlugin != null) {
                    if (monstrorvmPlugin.isEnabled())
                        return monstrorvmPlugin.getDescription().getVersion();
                }
                return "No Monstrorvm";
            }));
        }
    }

    public void registerCommands() {
        Util.registerCommand("betternetheritereload", new BetterNetheriteReload());
    }

    public void registerEvents() {
        Util.registerEvent(new PlayerJoin());
        Util.registerEvent(new NetheriteFishing());
        Util.registerEvent(new UpgradePack());
        Util.registerEvent(new SmithingTable());
        Util.registerEvent(new AncientDebris());
        Util.registerEvent(new ReinforcedItem());
        Util.registerEvent(new NetheriteGolem());
    }
}
