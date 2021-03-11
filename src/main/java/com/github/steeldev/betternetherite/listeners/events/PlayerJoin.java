package com.github.steeldev.betternetherite.listeners.events;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class PlayerJoin implements Listener {
    BetterNetherite main = BetterNetherite.getInstance();

    @EventHandler
    public void utilPlayerJoin(PlayerJoinEvent event) {
        if (main.config.NEW_UPDATE_MESSAGE_ON_JOIN)
            main.versionManager.sendNewUpdateMessageToPlayer(event.getPlayer());

        if (main.config.RESOURCE_PACK_ENABLED) {
            if (!main.config.RESOURCE_PACK_URL.isEmpty()) {

                Player player = event.getPlayer();

                player.setResourcePack(main.config.RESOURCE_PACK_URL);
                if (main.config.RESOURCE_PACK_JOIN_MSG_ENABLED)
                    Message.SERVER_USES_RESOURCE_PACK.send(player, true);
            }
        }
    }

    @EventHandler
    public void playerResourcePackEvent(PlayerResourcePackStatusEvent event) {
        if (!main.config.RESOURCE_PACK_ENABLED
                || !main.config.RESOURCE_PACK_STATUS_MSGS_ENABLED
                || main.config.RESOURCE_PACK_URL.isEmpty()) return;
        Player player = event.getPlayer();
        switch (event.getStatus()) {
            case DECLINED:
                Message.RESOURCE_PACK_DECLINED.send(player, true);
                if (main.config.FORCE_RESOURCE_PACK)
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> player.kickPlayer(Message.RESOURCE_PACK_DECLINED_KICKED.toString()), 10l);
                break;
            case ACCEPTED:
                Message.RESOURCE_PACK_ACCEPTED.send(player, true);
                break;
            case FAILED_DOWNLOAD:
                Message.RESOURCE_PACK_FAILED.send(player, true);
                if (main.config.FORCE_RESOURCE_PACK)
                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> player.kickPlayer(Message.RESOURCE_PACK_FAILED_KICKED.toString()), 10l);
                break;
            case SUCCESSFULLY_LOADED:
                Message.RESOURCE_PACK_LOADED.send(player, true);
                break;
        }
    }
}
