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

import java.util.List;

public class SpawnBNMob implements CommandExecutor, TabCompleter {
    BetterNetherite main = BetterNetherite.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            BNMob specifiedMob = BNMobManager.getBNMob(strings[0]);
            if (specifiedMob != null) {
                Player player = (Player) commandSender;
                if(!player.getWorld().getDifficulty().equals(Difficulty.PEACEFUL)) {
                    specifiedMob.spawnMob(player.getLocation(), null);
                    commandSender.sendMessage(main.colorize(Lang.PREFIX + Lang.CUSTOM_MOB_SPAWNED_MSG.replace("MOBNAME", specifiedMob.EntityName)));
                }
                else{
                    commandSender.sendMessage(main.colorize(Lang.PREFIX + Lang.CUSTOM_MOB_SPAWN_FAILED_MSG.replaceAll("MOBNAME", specifiedMob.EntityName)));
                }
            }
            else{
                commandSender.sendMessage(main.colorize(Lang.PREFIX + Lang.CUSTOM_MOB_INVALID_MSG.replaceAll("MOBID", strings[0])));
            }
        }else{
            commandSender.sendMessage(main.colorize(Lang.PREFIX + Lang.PLAYERS_ONLY_MSG));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return BNMobManager.getValidMobList();
    }
}
