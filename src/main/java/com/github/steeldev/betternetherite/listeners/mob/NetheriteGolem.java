package com.github.steeldev.betternetherite.listeners.mob;

import com.github.steeldev.betternetherite.util.Util;
import com.github.steeldev.monstrorvm.api.mobs.MVMob;
import com.github.steeldev.monstrorvm.api.mobs.MobManager;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.List;

import static com.github.steeldev.betternetherite.util.Util.main;

public class NetheriteGolem implements Listener {
    @EventHandler
    public void repair(PlayerInteractEntityEvent event){
        if(!(event.getRightClicked() instanceof IronGolem)) return;
        if(!MobManager.isMVMob(event.getRightClicked(),"netherite_golem")) return;
        if(event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        IronGolem golem = (IronGolem) event.getRightClicked();
        ItemStack handItem = event.getPlayer().getInventory().getItemInMainHand();
        double maxHP = golem.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if(!handItem.getType().equals(Material.NETHERITE_INGOT) &&
                !handItem.getType().equals(Material.NETHERITE_SCRAP) &&
                !handItem.getType().equals(Material.NETHERITE_BLOCK)) return;
        event.setCancelled(true);
        if(golem.getHealth() < maxHP) {
            double health = golem.getHealth();
            if (handItem.getType().equals(Material.NETHERITE_SCRAP))
                health += main.config.NETHERITE_GOLEM_SCRAP_REPAIR_AMOUNT;
            if (handItem.getType().equals(Material.NETHERITE_INGOT))
                health += main.config.NETHERITE_GOLEM_INGOT_REPAIR_AMOUNT;
            if (handItem.getType().equals(Material.NETHERITE_BLOCK))
                health += main.config.NETHERITE_GOLEM_BLOCK_REPAIR_AMOUNT;

            golem.getWorld().playSound(golem.getLocation(), Sound.BLOCK_SMITHING_TABLE_USE, SoundCategory.AMBIENT,1,1);

            if(health > maxHP) health = maxHP;
            golem.setHealth(health);
            handItem.setAmount(handItem.getAmount() - 1);
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        if (!main.config.CUSTOM_MOB_NETHERITE_GOLEM_ENABLED) return;
        Block block = event.getBlock();
        if (!block.getType().equals(Material.CARVED_PUMPKIN)) return;
        boolean pass = false;

        if (block.getRelative(0, -1, 0).getType().equals(Material.NETHERITE_BLOCK)) {
            if (block.getRelative(0, -2, 0).getType().equals(Material.NETHERITE_BLOCK)) {
                if (event.getPlayer().getFacing().equals(BlockFace.EAST) ||
                        event.getPlayer().getFacing().equals(BlockFace.WEST)) {
                    if (block.getRelative(0, -1, 1).getType().equals(Material.NETHERITE_BLOCK))
                        if (block.getRelative(0, -1, -1).getType().equals(Material.NETHERITE_BLOCK))
                            pass = true;
                } else if (event.getPlayer().getFacing().equals(BlockFace.SOUTH) ||
                        event.getPlayer().getFacing().equals(BlockFace.NORTH)) {
                    if (block.getRelative(1, -1, 0).getType().equals(Material.NETHERITE_BLOCK))
                        if (block.getRelative(-1, -1, 0).getType().equals(Material.NETHERITE_BLOCK))
                            pass = true;
                }
            }
        }

        if (pass) {
            World world = block.getWorld();
            BlockData netheriteBlockData = Material.NETHERITE_BLOCK.createBlockData();
            block.setType(Material.AIR);
            block.getRelative(0, -1, 0).setType(Material.AIR);
            block.getRelative(0, -2, 0).setType(Material.AIR);

            if (event.getPlayer().getFacing().equals(BlockFace.EAST) ||
                    event.getPlayer().getFacing().equals(BlockFace.WEST)) {
                block.getRelative(0, -1, 1).setType(Material.AIR);
                block.getRelative(0, -1, -1).setType(Material.AIR);
            } else if (event.getPlayer().getFacing().equals(BlockFace.SOUTH) ||
                    event.getPlayer().getFacing().equals(BlockFace.NORTH)) {
                block.getRelative(1, -1, 0).setType(Material.AIR);
                block.getRelative(-1, -1, 0).setType(Material.AIR);
            }

            List<Block> radiusBlocks = com.github.steeldev.monstrorvm.util.pluginutils.Util.getNearbyBlocks(block.getRelative(0,-1,0).getLocation(),1);
            for(Block b : radiusBlocks){
                world.spawnParticle(Particle.BLOCK_DUST,b.getLocation().add(0.5,0.5,0.5),12,0,0,0,netheriteBlockData);
            }

            world.playSound(block.getRelative(0,-2,0).getLocation(),Sound.BLOCK_NETHERITE_BLOCK_BREAK,SoundCategory.MASTER,1,1);

            MVMob golem = MobManager.getMob("netherite_golem");
            ((IronGolem)golem.spawnMob(block.getRelative(0, -2, 0).getLocation().add(0.5, 0.5, 0.5))).setPlayerCreated(true);
        }
    }
}
