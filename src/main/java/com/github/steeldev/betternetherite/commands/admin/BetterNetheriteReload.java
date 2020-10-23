package com.github.steeldev.betternetherite.commands.admin;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.util.UpdateChecker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BetterNetheriteReload implements CommandExecutor {
    final BetterNetherite main = BetterNetherite.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        main.loadCustomConfigs();
        commandSender.sendMessage(main.colorize(String.format("%s%s", Lang.PREFIX, "&aSuccessfully reloaded all configurations! &c&oWarning: A lot of systems require a server restart!")));
        if (commandSender instanceof Player) {
            if (BetterConfig.NEW_UPDATE_MESSAGE_ON_RELOAD)
                UpdateChecker.sendNewUpdateMessageToPlayer((Player) commandSender);
        }
        return true;
    }
}
