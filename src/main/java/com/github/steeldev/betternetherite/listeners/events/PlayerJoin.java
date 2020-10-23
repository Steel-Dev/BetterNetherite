package com.github.steeldev.betternetherite.listeners.events;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.util.UpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    BetterNetherite main = BetterNetherite.getInstance();

    @EventHandler
    public void playerJoinUpdateCheck(PlayerJoinEvent e) {
        if (!BetterConfig.NEW_UPDATE_MESSAGE_ON_JOIN) return;
        UpdateChecker.sendNewUpdateMessageToPlayer(e.getPlayer());
    }
}
