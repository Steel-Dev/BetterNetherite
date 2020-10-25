package com.github.steeldev.betternetherite.commands.admin;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import static com.github.steeldev.betternetherite.util.Util.colorize;

public class KillAllBNMobs implements CommandExecutor {
    BetterNetherite main = BetterNetherite.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            int entityAmount = BNMobManager.getSpawnedMobs().size();
            if (entityAmount == 0) {
                commandSender.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.CUSTOM_MOBS_KILL_FAILED_MSG)));
                return true;
            }

            Iterator<Map.Entry<UUID, LivingEntity>> it = BNMobManager.getSpawnedMobs().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<UUID, LivingEntity> entityEntry = it.next();
                if (entityEntry != null) {
                    entityEntry.getValue().remove();
                    it.remove();
                }
            }

            commandSender.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.CUSTOM_MOBS_KILLED_MSG.replaceAll("MOBAMOUNT", String.valueOf(entityAmount)))));
        } else {
            commandSender.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.PLAYERS_ONLY_MSG)));
        }
        return true;
    }
}
