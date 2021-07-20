package com.github.steeldev.betternetherite.listeners.mob;

import com.github.steeldev.betternetherite.util.Util;
import com.github.steeldev.monstrorvm.api.mobs.MVMob;
import com.github.steeldev.monstrorvm.api.mobs.MobManager;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

import static com.github.steeldev.betternetherite.util.Util.*;

public class NetheriteGolem implements Listener {
    @EventHandler
    public void repair(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof IronGolem)) return;
        if(!monstrorvmEnabled()) return;
        if (!MobManager.isMVMob(event.getRightClicked(), "netherite_golem")) return;
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        IronGolem golem = (IronGolem) event.getRightClicked();
        ItemStack handItem = event.getPlayer().getInventory().getItemInMainHand();
        double maxHP = golem.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if (!handItem.getType().equals(Material.NETHERITE_INGOT) &&
                !handItem.getType().equals(Material.NETHERITE_SCRAP) &&
                !handItem.getType().equals(Material.NETHERITE_BLOCK)) return;
        event.setCancelled(true);
        if (golem.getHealth() < maxHP) {
            double health = golem.getHealth();
            if (handItem.getType().equals(Material.NETHERITE_SCRAP))
                health += getMain().config.NETHERITE_GOLEM_SCRAP_REPAIR_AMOUNT;
            if (handItem.getType().equals(Material.NETHERITE_INGOT))
                health += getMain().config.NETHERITE_GOLEM_INGOT_REPAIR_AMOUNT;
            if (handItem.getType().equals(Material.NETHERITE_BLOCK))
                health += getMain().config.NETHERITE_GOLEM_BLOCK_REPAIR_AMOUNT;

            golem.getWorld().playSound(golem.getLocation(), Sound.BLOCK_SMITHING_TABLE_USE, SoundCategory.AMBIENT, 1, 1);

            if (health > maxHP) health = maxHP;
            golem.setHealth(health);
            handItem.setAmount(handItem.getAmount() - 1);
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        if(!monstrorvmEnabled()) return;
        if (!getMain().config.CUSTOM_MOB_NETHERITE_GOLEM_ENABLED) return;
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

            List<Block> radiusBlocks = com.github.steeldev.monstrorvm.util.pluginutils.Util.getNearbyBlocks(block.getRelative(0, -1, 0).getLocation(), 1);
            for (Block b : radiusBlocks) {
                world.spawnParticle(Particle.BLOCK_DUST, b.getLocation().add(0.5, 0.5, 0.5), 12, 0, 0, 0, netheriteBlockData);
            }

            world.playSound(block.getRelative(0, -2, 0).getLocation(), Sound.BLOCK_NETHERITE_BLOCK_BREAK, SoundCategory.MASTER, 1, 1);

            MVMob golem = MobManager.getMob("netherite_golem");
            ((IronGolem) golem.spawnMob(block.getRelative(0, -2, 0).getLocation().add(0.5, 0.5, 0.5))).setPlayerCreated(true);
        }
    }

    @EventHandler
    public void giveGolemRose(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof IronGolem)) return;
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        Player player = event.getPlayer();
        IronGolem golem = (IronGolem) event.getRightClicked();
        ItemStack handItem = event.getPlayer().getInventory().getItemInMainHand();
        if (!handItem.getType().equals(Material.POPPY)) return;
        event.setCancelled(true);
        if (golem.getTarget() != null) {
            if (golem.getTarget() instanceof Player)
                golem.setTarget(null);
            else return;
        }

        golem.playEffect(EntityEffect.IRON_GOLEM_ROSE);
        if (!player.getGameMode().equals(GameMode.CREATIVE))
            handItem.setAmount(handItem.getAmount() - 1);

        if(!monstrorvmEnabled()) return;
        if (MobManager.isMVMob(event.getRightClicked(), "netherite_golem")) {
            if (Util.chanceOf(2)) {
                // Credit to : CoKoC on spigotmc for this snippet (https://www.spigotmc.org/threads/how-to-rotate-mobs-around-one-location.80498/)
                // I modified it a bit to fit my needs lol
                new BukkitRunnable() {
                    int tick = 0;
                    Location center = golem.getLocation();
                    float radius = 1.5f;
                    float radPerSec = 3f;
                    float radPerTick = radPerSec / 20f;

                    public void run() {
                        tick++;
                        radPerSec += 0.05f;
                        radius -= 0.003f;
                        if (radius <= 1)
                            radius = 1;
                        if (radPerSec >= 8)
                            radPerSec = 8f;
                        radPerTick = radPerSec / 20f;

                        Location loc = Util.getLocationAroundCircle(center, radius, radPerTick * tick);
                        golem.setVelocity(new Vector(1, 0, 0));
                        golem.teleport(loc);
                        golem.getWorld().spawnParticle(Particle.BLOCK_DUST, golem.getLocation(), 12, 0, 0, 0, Material.NETHERITE_BLOCK.createBlockData());

                        if (tick >= 400) {
                            golem.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, golem.getLocation(), 1);
                            golem.getWorld().playSound(golem.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 1, 0.3f);
                            ItemStack item = new ItemStack(Material.DIAMOND);
                            ItemMeta meta = item.getItemMeta();
                            meta.setLore(Arrays.asList(colorize("&aYou found the secret!")));
                            item.setItemMeta(meta);
                            int am = Util.rand.nextInt(20);
                            if (am <= 0) am = 1;
                            item.setAmount(am);
                            golem.getWorld().dropItemNaturally(golem.getLocation(), item);
                            this.cancel();
                        }
                    }
                }.runTaskTimer(getMain(), 0L, 1L);
            }
        }
    }
}
