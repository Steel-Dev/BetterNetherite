package com.github.steeldev.betternetherite.listeners.items;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import static com.github.steeldev.betternetherite.util.Util.chanceOf;

public class ReinforcedItem implements Listener {
    BetterNetherite main = BetterNetherite.getInstance();

    @EventHandler
    public void reinforcedItemDamage(PlayerItemDamageEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL &&
                player.getGameMode() != GameMode.ADVENTURE)
            return;
        ItemStack tool = event.getItem();
        if (tool.getType() == Material.AIR)
            return;

        NBTItem toolNBT = new NBTItem(tool);

        if (!toolNBT.hasKey("netherite_reinforced"))
            return;

        if (!chanceOf(BetterConfig.REINFORCED_ITEM_DURABILITY_LOSS_CHANCE))
            event.setCancelled(true);
    }

    @EventHandler
    public void reinforcedItemAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player player = (Player) event.getDamager();
            if (player.getGameMode() != GameMode.SURVIVAL &&
                    player.getGameMode() != GameMode.ADVENTURE)
                return;
            LivingEntity entity = (LivingEntity) event.getEntity();

            ItemStack tool = player.getInventory().getItemInMainHand();
            if (tool.getType() == Material.AIR)
                return;

            NBTItem toolNBT = new NBTItem(tool);

            if (!toolNBT.hasKey("netherite_reinforced"))
                return;

            if (chanceOf(BetterConfig.REINFORCED_ITEM_EXTRA_DMG_CHANCE))
                entity.damage(BetterConfig.REINFORCED_ITEM_DAMAGE_INCREASE);
        }
    }
}
