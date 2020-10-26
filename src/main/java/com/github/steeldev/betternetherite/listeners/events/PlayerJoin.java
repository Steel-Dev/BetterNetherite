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
    public void playerJoinUpdateCheck(PlayerJoinEvent e) {
        if (!BetterConfig.NEW_UPDATE_MESSAGE_ON_JOIN) return;
        UpdateChecker.sendNewUpdateMessageToPlayer(e.getPlayer());
    }

    @EventHandler
    public void playerJoinResourcePack(PlayerJoinEvent e){
        if(!BetterConfig.RESOURCE_PACK_ENABLED) return;
        if(BetterConfig.RESOURCE_PACK_URL.equals("")) return;

        Player player = e.getPlayer();

        player.setResourcePack(BetterConfig.RESOURCE_PACK_URL);
        if(BetterConfig.RESOURCE_PACK_JOIN_MSG_ENABLED)
            player.sendMessage(colorize(Lang.PREFIX + "&2This server has custom items, and custom textures. Which require a resource pack to have the best experience. You have been sent it."));
    }

    @EventHandler
    public void playerResourcePackEvent(PlayerResourcePackStatusEvent e){
        if(!BetterConfig.RESOURCE_PACK_ENABLED) return;
        if(!BetterConfig.RESOURCE_PACK_STATUS_MSGS_ENABLED) return;
        if(BetterConfig.RESOURCE_PACK_URL.equals("")) return;
        Player player = e.getPlayer();
        if(e.getStatus().equals(PlayerResourcePackStatusEvent.Status.DECLINED)){
            player.sendMessage(colorize(Lang.PREFIX + "&2You have denied the custom resource pack. Please accept it for the best experience! If you did not deny it, please ensure Resource Packs are set to either Enabled or Prompt in the servers settings in your server list."));
        }
        else if(e.getStatus().equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)){
            player.sendMessage(colorize(Lang.PREFIX + "&2The custom resource pack has failed to download!"));
        }
        else if(e.getStatus().equals(PlayerResourcePackStatusEvent.Status.ACCEPTED)){
            player.sendMessage(colorize(Lang.PREFIX + "&2You have accepted the custom resource pack! enjoy!"));
        }
        else if(e.getStatus().equals(PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED)){
            player.sendMessage(colorize(Lang.PREFIX + "&2Custom Resource pack successfully loaded."));
        }
    }
}
