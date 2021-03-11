package com.github.steeldev.betternetherite.commands.admin;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BetterNetheriteReload implements CommandExecutor {
    final BetterNetherite main = BetterNetherite.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        main.loadConfigurations();
        Message.PLUGIN_RELOADED.send(sender, true);
        main.versionManager.checkForNewVersion();
        if (main.config.NEW_UPDATE_MESSAGE_ON_RELOAD) {
            if (sender instanceof Player)
                main.versionManager.sendNewUpdateMessageToPlayer((Player) sender);

        }
        return true;
    }
}
