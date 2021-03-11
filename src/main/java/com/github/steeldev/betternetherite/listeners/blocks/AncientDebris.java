package com.github.steeldev.betternetherite.listeners.blocks;

import com.github.steeldev.betternetherite.BetterNetherite;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import static com.github.steeldev.betternetherite.util.Util.chanceOf;
import static com.github.steeldev.betternetherite.util.Util.rand;

public class AncientDebris implements Listener {
    BetterNetherite main = BetterNetherite.getInstance();

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (event.isCancelled() ||
                !block.getType().equals(Material.ANCIENT_DEBRIS) ||
                block.hasMetadata("PlacedByPlayer"))
            return;

        int sw = rand.nextInt(3);
        if (sw == 1) {
            if (main.config.ANCIENT_DEBRIS_SCRAP_DROP_ENABLED) {
                ItemStack netheriteScrap = new ItemStack(Material.NETHERITE_SCRAP);

                int randAmount = rand.nextInt(main.config.ANCIENT_DEBRIS_SCRAP_DROP_MAX);
                int amount = (randAmount == 0) ? 1 : randAmount;
                if (amount > 64) amount = 64;

                netheriteScrap.setAmount(amount);
                if (chanceOf(main.config.ANCIENT_DEBRIS_SCRAP_DROP_CHANCE)) {
                    block.getWorld().dropItem(block.getLocation(), netheriteScrap);
                    block.getWorld().spawnParticle(Particle.CRIMSON_SPORE, block.getLocation(), 3);
                    block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_FALL, 1, 0.5f);
                }
            }
        } else if (sw == 2) {
            if (main.config.ANCIENT_DEBRIS_INGOT_DROP_ENABLED) {
                ItemStack netheriteIngot = new ItemStack(Material.NETHERITE_INGOT);

                netheriteIngot.setAmount(1);
                if (chanceOf(main.config.ANCIENT_DEBRIS_INGOT_DROP_CHANCE)) {
                    block.getWorld().dropItem(block.getLocation(), netheriteIngot);
                    block.getWorld().spawnParticle(Particle.CRIMSON_SPORE, block.getLocation(), 5);
                    block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_FALL, 1.3f, 0.5f);
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        //We only want to add this metadata value if the placed block is ancient debris
        //So we dont put metedata on EVERY placed block.
        //This is so people cant farm the extra scrap drops by placing and
        //mining debris over and over, aka abuse the system
        //this value of course wont persist through a server restart
        //but the players dont have control over restarts
        if (event.isCancelled() ||
                !block.getType().equals(Material.ANCIENT_DEBRIS))
            return;

        block.setMetadata("PlacedByPlayer", new FixedMetadataValue(main, true));
    }
}
