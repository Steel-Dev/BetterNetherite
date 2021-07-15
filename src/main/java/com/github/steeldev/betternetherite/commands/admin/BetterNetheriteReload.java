package com.github.steeldev.betternetherite.commands.admin;

import com.github.steeldev.betternetherite.listeners.baselisteners.ShrineBase;
import com.github.steeldev.betternetherite.managers.BNItemManager;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import com.github.steeldev.betternetherite.managers.BNShrineManager;
import com.github.steeldev.betternetherite.util.Message;
import com.github.steeldev.betternetherite.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.steeldev.betternetherite.util.Util.main;


public class BetterNetheriteReload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        main.loadConfigurations();

        Util.unregisterEvents(new ShrineBase());
        BNShrineManager.registerShrines();

        if (main.monstrorvmPlugin != null && main.monstrorvmPlugin.isEnabled()) {
            BNItemManager.registerCustomItems();
            BNMobManager.registerCustomMobs();
        }

        Message.PLUGIN_RELOADED.send(sender, true);
        main.versionManager.checkForNewVersion();
        if (main.config.NEW_UPDATE_MESSAGE_ON_RELOAD) {
            if (sender instanceof Player)
                main.versionManager.sendNewUpdateMessageToPlayer((Player) sender);

        }
        return true;
    }
}
