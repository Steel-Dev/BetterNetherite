package com.github.steeldev.betternetherite.commands.admin;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class KillAllBNMobs implements CommandExecutor {
    BetterNetherite main = BetterNetherite.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            int entityAmount = 0;

            for (LivingEntity entity : player.getWorld().getLivingEntities()) {
                if (entity.getCustomName() != null) {
                    if (entity.getPersistentDataContainer().has(BNMobManager.MobsKey, PersistentDataType.STRING)) {
                        entity.remove();
                        entityAmount++;
                    }
                }
            }

            if (entityAmount == 0) {
                commandSender.sendMessage(main.colorize(String.format("%s%s", Lang.PREFIX, Lang.CUSTOM_MOBS_KILL_FAILED_MSG)));
                return true;
            }

            commandSender.sendMessage(main.colorize(String.format("%s%s", Lang.PREFIX, Lang.CUSTOM_MOBS_KILLED_MSG.replaceAll("MOBAMOUNT", String.valueOf(entityAmount)))));
        } else {
            commandSender.sendMessage(main.colorize(String.format("%s%s", Lang.PREFIX, Lang.PLAYERS_ONLY_MSG)));
        }
        return true;
    }
}
