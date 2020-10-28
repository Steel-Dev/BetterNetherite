package com.github.steeldev.betternetherite.commands.admin;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.listeners.inventory.BNItemListInventory;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.steeldev.betternetherite.util.Util.colorize;

public class ListBNItems implements CommandExecutor {
    static BetterNetherite main = BetterNetherite.getInstance();
    public static NamespacedKey bnPageKey = new NamespacedKey(main, "BNItemInvPage");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            BNItemListInventory.openListInventory(player, 0);
        } else {
            sender.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.PLAYERS_ONLY_MSG)));
        }
        return true;
    }
}
