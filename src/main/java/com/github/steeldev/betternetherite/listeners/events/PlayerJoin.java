package com.github.steeldev.betternetherite.listeners.events;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.util.UpdateChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

import static com.github.steeldev.betternetherite.util.Util.colorize;

public class PlayerJoin implements Listener {
    BetterNetherite main = BetterNetherite.getInstance();

    @EventHandler
    public void utilPlayerJoin(PlayerJoinEvent event) {
        if (BetterConfig.NEW_UPDATE_MESSAGE_ON_JOIN)
            UpdateChecker.sendNewUpdateMessageToPlayer(event.getPlayer());

        if (BetterConfig.RESOURCE_PACK_ENABLED) {
            if (!BetterConfig.RESOURCE_PACK_URL.equals("")) {

                Player player = event.getPlayer();

                player.setResourcePack(BetterConfig.RESOURCE_PACK_URL);
                if (BetterConfig.RESOURCE_PACK_JOIN_MSG_ENABLED)
                    player.sendMessage(colorize(Lang.PREFIX + "&2This server has custom items, and custom textures. Which require a resource pack to have the best experience. You have been sent it."));
            }
        }
    }

    @EventHandler
    public void playerResourcePackEvent(PlayerResourcePackStatusEvent event) {
        if (!BetterConfig.RESOURCE_PACK_ENABLED) return;
        if (!BetterConfig.RESOURCE_PACK_STATUS_MSGS_ENABLED) return;
        if (BetterConfig.RESOURCE_PACK_URL.equals("")) return;
        Player player = event.getPlayer();
        if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.DECLINED)) {
            player.sendMessage(colorize(Lang.PREFIX + "&2You have denied the custom resource pack. Please accept it for the best experience! If you did not deny it, please ensure Resource Packs are set to either Enabled or Prompt in the servers settings in your server list."));
        } else if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)) {
            player.sendMessage(colorize(Lang.PREFIX + "&2The custom resource pack has failed to download!"));
        } else if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.ACCEPTED)) {
            player.sendMessage(colorize(Lang.PREFIX + "&2You have accepted the custom resource pack! enjoy!"));
        } else if (event.getStatus().equals(PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED)) {
            player.sendMessage(colorize(Lang.PREFIX + "&2Custom Resource pack successfully loaded."));
        }
    }
}
