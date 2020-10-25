package com.github.steeldev.betternetherite.commands.admin;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import com.github.steeldev.betternetherite.misc.BNMob;
import org.bukkit.Difficulty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnBNMob implements CommandExecutor, TabCompleter {
    BetterNetherite main = BetterNetherite.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (strings.length < 1) return false;
            BNMob specifiedMob = BNMobManager.getBNMob(strings[0]);
            if (specifiedMob != null) {
                Player player = (Player) commandSender;
                if (!player.getWorld().getDifficulty().equals(Difficulty.PEACEFUL)) {
                    specifiedMob.spawnMob(player.getLocation(), null);
                    commandSender.sendMessage(main.colorize(String.format("%s%s", Lang.PREFIX, Lang.CUSTOM_MOB_SPAWNED_MSG.replace("MOBNAME", specifiedMob.entityName))));
                } else {
                    commandSender.sendMessage(main.colorize(String.format("%s%s", Lang.PREFIX, Lang.CUSTOM_MOB_SPAWN_FAILED_MSG.replaceAll("MOBNAME", specifiedMob.entityName))));
                }
            } else {
                commandSender.sendMessage(main.colorize(String.format("%s%s", Lang.PREFIX, Lang.CUSTOM_MOB_INVALID_MSG.replaceAll("MOBID", strings[0]))));
            }
        } else {
            commandSender.sendMessage(main.colorize(String.format("%s%s", Lang.PREFIX, Lang.PLAYERS_ONLY_MSG)));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> mobs = BNMobManager.getValidMobList();

        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(strings[0], mobs, completions);
        Collections.sort(completions);
        return completions;
    }
}
