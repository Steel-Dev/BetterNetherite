package com.github.steeldev.betternetherite.commands.admin;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.managers.BNItemManager;
import com.github.steeldev.betternetherite.misc.BNItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GiveBNItem implements CommandExecutor, TabCompleter {
    BetterNetherite main = BetterNetherite.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player specifiedPlayer = (Player) commandSender;
            if (strings.length < 1) return false;
            BNItem specifiedItem = BNItemManager.getBNItem(strings[0]);
            int amount = 1;
            if (strings.length > 1) {
                try {
                    amount = Integer.parseInt(strings[1]);
                } catch (NumberFormatException e) {
                    commandSender.sendMessage(main.colorize(String.format("%s&cExpected a number.", Lang.PREFIX)));
                    return true;
                }
            }
            if (strings.length > 2) {
                if (main.getServer().getPlayer(strings[2]) != null) {
                    specifiedPlayer = main.getServer().getPlayer(strings[2]);
                } else {
                    commandSender.sendMessage(main.colorize(Lang.PREFIX + Lang.INVALID_PLAYER_MSG));
                    return true;
                }
            }
            if (specifiedPlayer == null) {
                commandSender.sendMessage(main.colorize(Lang.PREFIX + Lang.INVALID_PLAYER_MSG));
                return true;
            }
            if (specifiedItem != null) {
                if (specifiedPlayer.getInventory().firstEmpty() != -1) {
                    ItemStack item = specifiedItem.getItem(false);
                    for (int i = 0; i < amount; i++) {
                        specifiedPlayer.getInventory().addItem(item);
                    }
                    commandSender.sendMessage(main.colorize(String.format("%s%s", Lang.PREFIX, Lang.CUSTOM_ITEM_GIVEN_MSG.replace("ITEMNAME", specifiedItem.displayName).replace("PLAYERNAME", specifiedPlayer.getDisplayName()).replace("ITEMAMOUNT", String.valueOf(amount)))));
                } else
                    commandSender.sendMessage(main.colorize(String.format("%s%s", Lang.PREFIX, Lang.CUSTOM_ITEM_PLAYER_INVENTORY_FULL_MSG.replace("ITEMNAME", specifiedItem.displayName).replace("PLAYERNAME", specifiedPlayer.getDisplayName()).replace("ITEMAMOUNT", String.valueOf(amount)))));
            } else {
                commandSender.sendMessage(main.colorize(String.format("%s%s", Lang.PREFIX, Lang.CUSTOM_ITEM_INVALID_MSG.replaceAll("ITEMID", strings[0]))));
            }
        } else {
            commandSender.sendMessage(main.colorize(String.format("%s%s", Lang.PREFIX, Lang.PLAYERS_ONLY_MSG)));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length > 2) {
            List<String> onlinePlayers = new ArrayList<>();
            for (Player player : main.getServer().getOnlinePlayers()) {
                onlinePlayers.add(player.getName());
            }

            final List<String> completions = new ArrayList<>();
            StringUtil.copyPartialMatches(strings[1], onlinePlayers, completions);
            Collections.sort(completions);
            return completions;
        }
        if (strings.length > 1)
            return new ArrayList<>();

        List<String> items = BNItemManager.getValidItemList();

        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(strings[0], items, completions);
        Collections.sort(completions);
        return completions;
    }
}
