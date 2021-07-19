package com.github.steeldev.betternetherite.listeners.baselisteners;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.misc.BNShrine;
import com.github.steeldev.betternetherite.util.Message;
import com.github.steeldev.betternetherite.util.shrines.BNPotionEffect;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.steeldev.betternetherite.util.Util.*;

public class ShrineBase implements Listener {
    BetterNetherite main = BetterNetherite.getInstance();

    BNShrine shrine;

    boolean onCooldown;

    public ShrineBase() {
    }

    public ShrineBase(BNShrine shrine) {
        updateShrine(shrine);
    }

    public void updateShrine(BNShrine shrine) {
        this.shrine = shrine;
    }

    @EventHandler
    public void useShrine(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Block clickedBlock = event.getClickedBlock();
        if (!event.getHand().equals(EquipmentSlot.HAND)) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        if (clickedBlock.getType().equals(shrine.core.coreBlock)) {
            Block coreSupport = clickedBlock.getRelative(0, -1, 0);
            if (coreSupport.getType().equals(shrine.core.coreSupport)) {
                Block cryingObsidian = coreSupport.getRelative(0, -1, 0);
                if (cryingObsidian.getType().equals(Material.CRYING_OBSIDIAN)) {
                    if (isRingValid(1, cryingObsidian, Material.MAGMA_BLOCK, Material.POLISHED_BLACKSTONE)) {
                        if (isRingValid(2, cryingObsidian, Material.BLACKSTONE, Material.NETHERITE_BLOCK)) {
                            event.setCancelled(true);

                            if (onCooldown) {
                                onCooldown = false;
                                return;
                            }

                            if (shrine.requiresValidItems && !main.config.USABLE_SHRINE_ITEMS.containsKey(item.getType().toString())) {
                                player.getWorld().playSound(clickedBlock.getLocation(), Sound.BLOCK_BEEHIVE_SHEAR, 1.6f, 1.6f);
                                Message.SHRINE_INVALID_ITEM.send(player, true, shrine.display);
                                return;
                            }

                            if (!shrine.validUseWorlds.contains(player.getWorld().getName())) {
                                player.getWorld().playSound(clickedBlock.getLocation(), Sound.BLOCK_BEEHIVE_SHEAR, 1.6f, 1.6f);
                                Message.SHRINE_CANT_USE_IN_WORLD.send(player, true, shrine.display);
                                return;
                            }

                            List<Block> charges = new ArrayList<>(Arrays.asList(
                                    cryingObsidian.getRelative(-2, 2, 1),
                                    cryingObsidian.getRelative(-2, 2, -1),
                                    cryingObsidian.getRelative(-1, 2, -2),
                                    cryingObsidian.getRelative(1, 2, -2),
                                    cryingObsidian.getRelative(2, 2, -1),
                                    cryingObsidian.getRelative(2, 2, 1),
                                    cryingObsidian.getRelative(1, 2, 2),
                                    cryingObsidian.getRelative(-1, 2, 2)
                            ));

                            int chargesLeft = 0;
                            for (Block charge : charges) {
                                if (charge.getType().equals(shrine.charge.chargeMaterial)) {
                                    if (!charge.getRelative(0, -1, 0).getType().equals(Material.CHAIN)) {
                                        player.getWorld().playSound(clickedBlock.getLocation(), shrine.effect.noChargesSound, 1.6f, 1.6f);
                                        Message.SHRINE_CHARGES_MUST_BE_ON_CHAINS.send(player, true, shrine.display);
                                        return;
                                    }
                                    chargesLeft++;
                                }
                            }

                            if (chargesLeft > 0) {
                                switch (shrine.effect.shrineEffectType) {
                                    case REINFORCE_ITEM:
                                        NBTItem nbtItem = new NBTItem(item);
                                        if (nbtItem.hasKey("netherite_reinforced")) {
                                            player.getWorld().playSound(clickedBlock.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.6f, 1.6f);
                                            Message.SHRINE_ITEM_ALREADY_EFFECTED.send(player, true, shrine.effect.effectDisplay);
                                            return;
                                        }
                                        nbtItem.setBoolean("netherite_reinforced", true);
                                        item = nbtItem.getItem();
                                        ItemMeta meta = (item.getItemMeta() == null) ? Bukkit.getItemFactory().getItemMeta(item.getType()) : item.getItemMeta();
                                        List<String> curLore = (meta.getLore() == null) ? new ArrayList<>() : meta.getLore();
                                        curLore.add(colorize(shrine.effect.effectLoreDisplay));
                                        meta.setLore(curLore);
                                        item.setItemMeta(meta);
                                        player.getInventory().setItemInMainHand(item);
                                        break;
                                    case HEAL_ITEM:
                                        Damageable damMeta = (item.getItemMeta() != null) ? (Damageable) item.getItemMeta() : (Damageable) Bukkit.getItemFactory().getItemMeta(item.getType());
                                        if (damMeta.getDamage() <= 0) {
                                            player.getWorld().playSound(clickedBlock.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1.6f, 1.6f);
                                            Message.SHRINE_CANT_USE_ITEM_NOT_DAMAGED.send(player, true, shrine.effect.effectDisplay);
                                            return;
                                        }
                                        damMeta.setDamage(0);
                                        item.setItemMeta((ItemMeta) damMeta);
                                        player.getInventory().setItemInMainHand(item);
                                        break;
                                    case APPLY_POTION_EFFECT:
                                        if (shrine.effect.potionEffects != null) {
                                            if (shrine.effect.potionEffects.size() > 0) {
                                                for (BNPotionEffect effect : shrine.effect.potionEffects) {
                                                    if (chanceOf(effect.chance)) {
                                                        effect.getPotionEffect().apply(player);
                                                        if (main.config.DEBUG)
                                                            Message.X_INFLICTED_X_WITH_X.log(shrine.display, player.getName(), effect.effect.toString());
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                }

                                chargesLeft--;

                                Block chargeChosen = null;
                                for (Block charge : charges) {
                                    if (!charge.getType().equals(Material.AIR))
                                        chargeChosen = charge;
                                }
                                chargeChosen.setType(Material.AIR);

                                player.getWorld().strikeLightning(clickedBlock.getLocation());
                                player.getWorld().spawnParticle(shrine.charge.usedParticle, chargeChosen.getLocation(), 5);
                                player.getWorld().playSound(chargeChosen.getLocation(), shrine.charge.usedSound, 1.5f, 1.5f);
                                player.getWorld().playSound(clickedBlock.getLocation(), shrine.effect.useSound, 1.6f, 1.6f);

                                if (chargesLeft < 1) {
                                    if (chanceOf(shrine.explodeChance)) {
                                        player.getWorld().playSound(clickedBlock.getLocation(), shrine.effect.breakSound, 1.6f, 1.6f);
                                        player.getWorld().strikeLightning(clickedBlock.getLocation());
                                        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> player.getWorld().strikeLightning(clickedBlock.getLocation()), 10);
                                        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                                            player.getWorld().strikeLightning(clickedBlock.getLocation());
                                            player.getWorld().createExplosion(clickedBlock.getLocation(), 8, true);
                                        }, 5);
                                    } else {
                                        player.getWorld().spawnParticle(shrine.effect.breakParticle, clickedBlock.getLocation(), 2);
                                        player.getWorld().playEffect(clickedBlock.getLocation(), shrine.effect.breakEffect, 2);
                                        clickedBlock.setType(Material.AIR);
                                    }
                                }
                                player.getWorld().spawnParticle(shrine.effect.shrineUsedParticle, clickedBlock.getLocation(), 2);
                                if (shrine.requiresValidItems)
                                    Message.SHRINE_USED_ITEM.send(player, true, shrine.display, shrine.effect.effectDisplay, shrine.effect.effectResultDisplay);
                                else
                                    Message.SHRINE_USED_POTION.send(player, true, shrine.display, shrine.effect.effectDisplay, shrine.effect.effectResultDisplay);
                                if (chargesLeft < 3) {
                                    String chargeMat = formalizedString(shrine.charge.chargeMaterial.toString());
                                    Message.SHRINE_CHARGES_LOW.send(player, false, chargeMat);
                                } else {
                                    Message.SHRINE_CHARGES_REMAINING.send(player, false, chargesLeft);
                                }
                                onCooldown = true;
                                // Cooldown is so this all doesn't run twice
                                //  For some reason with the Crimson Shrine
                                //  This would all execute twice
                                //  even with an equipment slot check to make
                                //  sure its not running for both hand & offhand
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        onCooldown = false;
                                    }
                                }.runTaskLater(main, 5);
                            } else {
                                String chargeMat = formalizedString(shrine.charge.chargeMaterial.toString());
                                player.getWorld().playSound(clickedBlock.getLocation(), shrine.effect.noChargesSound, 1.6f, 1.6f);
                                Message.SHRINE_NO_CHARGES.send(player, true, shrine.display, chargeMat);
                            }
                        } else {
                            player.getWorld().playSound(clickedBlock.getLocation(), Sound.BLOCK_BEEHIVE_SHEAR, 1.6f, 1.6f);
                            Message.SHRINE_BUILT_INCORRECTLY.send(player, true, shrine.display);
                        }
                    } else {
                        player.getWorld().playSound(clickedBlock.getLocation(), Sound.BLOCK_BEEHIVE_SHEAR, 1.6f, 1.6f);
                        Message.SHRINE_BUILT_INCORRECTLY.send(player, true, shrine.display);
                    }
                }
            }
        }
    }
}
