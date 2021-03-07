package com.github.steeldev.betternetherite.listeners.baselisteners;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.managers.BNShrineManager;
import com.github.steeldev.betternetherite.misc.BNShrine;
import com.github.steeldev.betternetherite.util.shrines.BNPotionEffect;
import com.github.steeldev.betternetherite.util.shrines.ShrineEffectType;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.steeldev.betternetherite.util.Util.*;

public class ShrineBase implements Listener {
    BetterNetherite main = BetterNetherite.getInstance();

    BNShrine shrine;

    public ShrineBase(String shrineID) {
        shrine = BNShrineManager.getBNShrine(shrineID);
    }

    @EventHandler
    public void useShrine(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        Block clickedBlock = event.getClickedBlock();
        boolean correct = false;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK ||
                clickedBlock == null ||
                (shrine.requiresValidItems && item.getType() == Material.AIR) ||
                event.getHand() != EquipmentSlot.HAND ||
                event.isCancelled())
            return;

        ////////////////////////////// IMPORTANT NOTE YO /////////////////////////////////
        //This is very very yucky, I know, dont get your panties in a twist.            //
        // Yes, its bad, yes its terrible.                                              //
        // and yes, of course im reworking it and rewriting it in the future.           //
        // calm yo tittehs! :D                                                          //
        // I wrote this mess at 5am and rushed it so I didnt forget what I wanted to do //
        // and never got back to it, and never got around to actually fixing it.        //
        // I also never found a better way to do it, so hey, maybe instead of being     //
        // an ass, you actually help me improve this?? :D                               //
        // Oh boi, its 6am as im writing this, whats with me and doing stuff so late??  //
        //////////////////////////////////////////////////////////////////////////////////
        if (clickedBlock.getType().equals(shrine.core.coreBlock)) {
            Block crimsonFence = clickedBlock.getRelative(0, -1, 0);
            if (crimsonFence.getType().equals(shrine.core.coreSupport)) {
                Block cryingObsidian = crimsonFence.getRelative(0, -1, 0);
                if (cryingObsidian.getType().equals(Material.CRYING_OBSIDIAN)) {
                    event.setCancelled(true);
                    if (!shrine.validUseWorlds.contains(p.getWorld().getEnvironment())) {
                        p.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.SHRINE_CANT_USE_IN_WORLD_MSG.replaceAll("SHRINE", shrine.display))));
                        return;
                    }
                    Block polishedBlackstone1 = cryingObsidian.getRelative(0, 0, 1);
                    Block polishedBlackstone2 = cryingObsidian.getRelative(0, 0, -1);
                    Block polishedBlackstone3 = cryingObsidian.getRelative(-1, 0, 0);
                    Block polishedBlackstone4 = cryingObsidian.getRelative(1, 0, 0);
                    if (polishedBlackstone1.getType().equals(Material.POLISHED_BLACKSTONE) &&
                            polishedBlackstone2.getType().equals(Material.POLISHED_BLACKSTONE) &&
                            polishedBlackstone3.getType().equals(Material.POLISHED_BLACKSTONE) &&
                            polishedBlackstone4.getType().equals(Material.POLISHED_BLACKSTONE)) {
                        Block magmaBlock1 = polishedBlackstone4.getRelative(0, 0, 1);
                        Block magmaBlock2 = polishedBlackstone4.getRelative(0, 0, -1);
                        Block magmaBlock3 = polishedBlackstone3.getRelative(0, 0, 1);
                        Block magmaBlock4 = polishedBlackstone3.getRelative(0, 0, -1);
                        if (magmaBlock1.getType().equals(Material.MAGMA_BLOCK) &&
                                magmaBlock2.getType().equals(Material.MAGMA_BLOCK) &&
                                magmaBlock3.getType().equals(Material.MAGMA_BLOCK) &&
                                magmaBlock4.getType().equals(Material.MAGMA_BLOCK)) {
                            Block netheriteBlock1 = magmaBlock1.getRelative(0, 0, 1);
                            Block netheriteBlock2 = magmaBlock1.getRelative(1, 0, 0);
                            Block netheriteBlock3 = magmaBlock2.getRelative(0, 0, -1);
                            Block netheriteBlock4 = magmaBlock2.getRelative(1, 0, 0);
                            Block netheriteBlock5 = magmaBlock3.getRelative(0, 0, 1);
                            Block netheriteBlock6 = magmaBlock3.getRelative(-1, 0, 0);
                            Block netheriteBlock7 = magmaBlock4.getRelative(0, 0, -1);
                            Block netheriteBlock8 = magmaBlock4.getRelative(-1, 0, 0);
                            if (netheriteBlock1.getType().equals(Material.NETHERITE_BLOCK) &&
                                    netheriteBlock2.getType().equals(Material.NETHERITE_BLOCK) &&
                                    netheriteBlock3.getType().equals(Material.NETHERITE_BLOCK) &&
                                    netheriteBlock4.getType().equals(Material.NETHERITE_BLOCK) &&
                                    netheriteBlock5.getType().equals(Material.NETHERITE_BLOCK) &&
                                    netheriteBlock6.getType().equals(Material.NETHERITE_BLOCK) &&
                                    netheriteBlock7.getType().equals(Material.NETHERITE_BLOCK) &&
                                    netheriteBlock8.getType().equals(Material.NETHERITE_BLOCK)) {
                                Block blackstone1 = netheriteBlock1.getRelative(-1, 0, 0);
                                Block blackstone2 = netheriteBlock5.getRelative(-1, 0, 0);
                                Block blackstone3 = netheriteBlock6.getRelative(0, 0, -1);
                                Block blackstone4 = netheriteBlock8.getRelative(0, 0, -1);
                                Block blackstone5 = netheriteBlock7.getRelative(1, 0, 0);
                                Block blackstone6 = netheriteBlock3.getRelative(1, 0, 0);
                                Block blackstone7 = netheriteBlock4.getRelative(0, 0, 1);
                                Block blackstone8 = netheriteBlock2.getRelative(0, 0, 1);
                                if (blackstone1.getType().equals(Material.BLACKSTONE) &&
                                        blackstone2.getType().equals(Material.BLACKSTONE) &&
                                        blackstone3.getType().equals(Material.BLACKSTONE) &&
                                        blackstone4.getType().equals(Material.BLACKSTONE) &&
                                        blackstone5.getType().equals(Material.BLACKSTONE) &&
                                        blackstone6.getType().equals(Material.BLACKSTONE) &&
                                        blackstone7.getType().equals(Material.BLACKSTONE) &&
                                        blackstone8.getType().equals(Material.BLACKSTONE)) {
                                    Block chain1 = netheriteBlock1.getRelative(0, 1, 0);
                                    Block chain2 = netheriteBlock2.getRelative(0, 1, 0);
                                    Block chain3 = netheriteBlock3.getRelative(0, 1, 0);
                                    Block chain4 = netheriteBlock4.getRelative(0, 1, 0);
                                    Block chain5 = netheriteBlock5.getRelative(0, 1, 0);
                                    Block chain6 = netheriteBlock6.getRelative(0, 1, 0);
                                    Block chain7 = netheriteBlock7.getRelative(0, 1, 0);
                                    Block chain8 = netheriteBlock8.getRelative(0, 1, 0);
                                    if (chain1.getType().equals(Material.CHAIN) &&
                                            chain2.getType().equals(Material.CHAIN) &&
                                            chain3.getType().equals(Material.CHAIN) &&
                                            chain4.getType().equals(Material.CHAIN) &&
                                            chain5.getType().equals(Material.CHAIN) &&
                                            chain6.getType().equals(Material.CHAIN) &&
                                            chain7.getType().equals(Material.CHAIN) &&
                                            chain8.getType().equals(Material.CHAIN)) {
                                        Block chargeMat1 = chain1.getRelative(0, 1, 0);
                                        Block chargeMat2 = chain2.getRelative(0, 1, 0);
                                        Block chargeMat3 = chain3.getRelative(0, 1, 0);
                                        Block chargeMat4 = chain4.getRelative(0, 1, 0);
                                        Block chargeMat5 = chain5.getRelative(0, 1, 0);
                                        Block chargeMat6 = chain6.getRelative(0, 1, 0);
                                        Block chargeMat7 = chain7.getRelative(0, 1, 0);
                                        Block chargeMat8 = chain8.getRelative(0, 1, 0);

                                        List<Block> charges = new ArrayList<>(Arrays.asList(
                                                chargeMat6,
                                                chargeMat5,
                                                chargeMat1,
                                                chargeMat2,
                                                chargeMat4,
                                                chargeMat3,
                                                chargeMat7,
                                                chargeMat8));

                                        correct = true;
                                        int chargesLeft = 0;
                                        for (Block charge : charges) {
                                            if (charge.getType().equals(shrine.charge.chargeMaterial)) {
                                                chargesLeft++;
                                            }
                                        }
                                        if (chargesLeft > 0) {
                                            if (shrine.requiresValidItems && !BetterConfig.USABLE_SHRINE_ITEMS.containsKey(item.getType().toString())) {
                                                p.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.SHRINE_INVALID_ITEM_MSG.replaceAll("SHRINE", shrine.display))));
                                                return;
                                            }
                                            if (shrine.effect.shrineEffectType == ShrineEffectType.REINFORCE_ITEM) {
                                                NBTItem nbtItem = new NBTItem(item);
                                                if (nbtItem.hasKey("netherite_reinforced")) {
                                                    if (nbtItem.getBoolean("netherite_reinforced")) {
                                                        p.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.SHRINE_ITEM_ALREADY_EFFECTED_MSG.replaceAll("EFFECT", shrine.effect.effectDisplay))));
                                                        return;
                                                    }
                                                }
                                                nbtItem.addCompound("netherite_reinforced");
                                                nbtItem.setBoolean("netherite_reinforced", true);
                                                item = nbtItem.getItem();
                                                ItemMeta meta = (item.getItemMeta() == null) ? Bukkit.getItemFactory().getItemMeta(item.getType()) : item.getItemMeta();
                                                List<String> curLore = (meta.getLore() == null) ? new ArrayList<>() : meta.getLore();
                                                curLore.add(colorize(shrine.effect.effectLoreDisplay));
                                                meta.setLore(curLore);
                                                item.setItemMeta(meta);
                                                p.getInventory().setItemInMainHand(item);
                                            } else if (shrine.effect.shrineEffectType == ShrineEffectType.HEAL_ITEM) {
                                                Damageable damMeta = (item.getItemMeta() != null) ? (Damageable) item.getItemMeta() : (Damageable) Bukkit.getItemFactory().getItemMeta(item.getType());

                                                if (damMeta.getDamage() <= 0) {
                                                    p.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.SHRINE_ITEM_FULL_DUR_MSG.replaceAll("EFFECT", shrine.effect.effectDisplay))));
                                                    return;
                                                }
                                                damMeta.setDamage(0);
                                                item.setItemMeta((ItemMeta) damMeta);
                                                p.getInventory().setItemInMainHand(item);
                                            } else if (shrine.effect.shrineEffectType == ShrineEffectType.APPLY_POTION_EFFECT) {
                                                if (shrine.effect.potionEffects != null) {
                                                    if (shrine.effect.potionEffects.size() > 0) {
                                                        for (BNPotionEffect effect : shrine.effect.potionEffects) {
                                                            if (chanceOf(effect.chance)) {
                                                                p.addPotionEffect(effect.getPotionEffect(), false);
                                                                if (BetterConfig.DEBUG)
                                                                    main.getLogger().info(String.format("&6%s &cinflicted &e%s &cwith &4%s&c!", shrine.display, p.getName(), effect.effect));
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            chargesLeft--;

                                            Block chargeChosen = null;

                                            for (Block charge : charges) {
                                                if (!charge.getType().equals(Material.AIR))
                                                    chargeChosen = charge;
                                            }
                                            chargeChosen.setType(Material.AIR);

                                            p.getWorld().strikeLightning(clickedBlock.getLocation());
                                            p.getWorld().spawnParticle(shrine.charge.usedParticle, chargeChosen.getLocation(), 5);
                                            p.getWorld().playSound(chargeChosen.getLocation(), shrine.charge.usedSound, 1.5f, 1.5f);
                                            p.getWorld().playSound(clickedBlock.getLocation(), shrine.effect.useSound, 1.6f, 1.6f);
                                            if (chargesLeft < 3) {
                                                String chargeMat = formalizedString(shrine.charge.chargeMaterial.toString());
                                                p.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.SHRINE_CHARGES_LOW_MSG.replaceAll("CHARGEMAT", chargeMat))));
                                            } else {
                                                p.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.SHRINE_CHARGES_MSG.replaceAll("CHARGESAVAILABLE", String.valueOf(chargesLeft)))));
                                            }

                                            if (chargesLeft < 1) {
                                                if (chanceOf(shrine.explodeChance)) {
                                                    p.getWorld().playSound(clickedBlock.getLocation(), shrine.effect.breakSound, 1.6f, 1.6f);
                                                    p.getWorld().strikeLightning(clickedBlock.getLocation());
                                                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            p.getWorld().strikeLightning(clickedBlock.getLocation());
                                                        }
                                                    }, 10);
                                                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            p.getWorld().strikeLightning(clickedBlock.getLocation());
                                                            p.getWorld().createExplosion(clickedBlock.getLocation(), 8, true);
                                                        }
                                                    }, 5);
                                                } else {
                                                    p.getWorld().spawnParticle(shrine.effect.breakParticle, clickedBlock.getLocation(), 2);
                                                    p.getWorld().playEffect(clickedBlock.getLocation(), shrine.effect.breakEffect, 2);
                                                    clickedBlock.setType(Material.AIR);
                                                }
                                            }
                                            p.getWorld().spawnParticle(shrine.effect.shrineUsedParticle, clickedBlock.getLocation(), 2);
                                            String usedMSG = (shrine.requiresValidItems) ? Lang.SHRINE_USED_MSG : Lang.POTION_SHRINE_USED_MSG;
                                            p.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, usedMSG.replaceAll("SHRINE", shrine.display).replaceAll("EFFECT", shrine.effect.effectDisplay).replaceAll("EFFRES", shrine.effect.effectResultDisplay))));
                                        } else {
                                            String chargeMat = formalizedString(shrine.charge.chargeMaterial.toString());
                                            p.getWorld().playSound(clickedBlock.getLocation(), shrine.effect.noChargesSound, 1.6f, 1.6f);
                                            p.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.SHRINE_NO_CHARGES_MSG.replaceAll("SHRINE", shrine.display).replaceAll("CHARGEMAT", chargeMat))));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!correct)
                p.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.SHRINE_BUILT_INCORRECT_MSG.replaceAll("SHRINE", shrine.display))));
        }
    }
}
