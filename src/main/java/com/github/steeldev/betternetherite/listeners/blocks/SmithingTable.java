package com.github.steeldev.betternetherite.listeners.blocks;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.util.Message;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.steeldev.betternetherite.util.Util.colorize;
import static com.github.steeldev.betternetherite.util.Util.formalizedString;

public class SmithingTable implements Listener {
    BetterNetherite main = BetterNetherite.getInstance();

    @EventHandler
    public void useSmithingTable(PlayerInteractEvent event) {
        if (!main.config.ENABLE_NETHERITE_CRAFTING) return;
        if (main.config.IMPROVED_UPGRADING) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Block block = event.getClickedBlock();
        if (block == null) return;
        Player player = event.getPlayer();
        if (block.getType().equals(Material.SMITHING_TABLE)) {
            event.setCancelled(true);
            Message.SMITHING_TABLE_DISABLED.send(player, true);
        }
    }

    @EventHandler
    public void smithingTableClick(InventoryClickEvent event) {
        //definitely in need of improvements, i cant be fucked right now, so, deal with it :p
        if (!main.config.IMPROVED_UPGRADING) return;

        Player p = (Player) event.getWhoClicked();
        if (p.getOpenInventory().getTitle().contains(colorize("Upgrade Gear"))) {
            Material matNeeded = null;
            ItemStack slot0Item = p.getOpenInventory().getItem(0);
            ItemStack slot1Item = p.getOpenInventory().getItem(1);
            ItemStack slot2Item = p.getOpenInventory().getItem(2);

            List<Material> validUpgradableItems = null;

            if (event.getSlot() != 2 ||
                    slot0Item == null ||
                    slot1Item == null ||
                    slot2Item == null ||
                    slot0Item.getType() == Material.AIR ||
                    slot2Item.getType() == Material.AIR ||
                    slot1Item.getType() == Material.AIR)
                return;

            int matAmount = 0;

            if (slot0Item.getType().toString().contains("WOODEN")) {
                if (!main.config.IMPROVED_UPGRADING_WOOD_TO_STONE_ENABLED) return;
                matAmount = main.config.IMPROVED_UPGRADING_WOOD_TO_STONE_AMOUNT;
                validUpgradableItems = new ArrayList<>(Arrays.asList(Material.WOODEN_AXE,
                        Material.WOODEN_HOE,
                        Material.WOODEN_PICKAXE,
                        Material.WOODEN_SWORD,
                        Material.WOODEN_SHOVEL));
                matNeeded = Material.COBBLESTONE;
            } else if (slot0Item.getType().toString().contains("STONE")) {
                if (!main.config.IMPROVED_UPGRADING_STONE_TO_IRON_ENABLED) return;
                matAmount = main.config.IMPROVED_UPGRADING_STONE_TO_IRON_AMOUNT;
                validUpgradableItems = new ArrayList<>(Arrays.asList(Material.STONE_AXE,
                        Material.STONE_HOE,
                        Material.STONE_PICKAXE,
                        Material.STONE_SWORD,
                        Material.STONE_SHOVEL));
                matNeeded = Material.IRON_INGOT;
            } else if (slot0Item.getType().toString().contains("IRON") && slot1Item.getType().equals(Material.DIAMOND)) {
                if (!main.config.IMPROVED_UPGRADING_IRON_TO_DIAMOND_ENABLED) return;
                matAmount = main.config.IMPROVED_UPGRADING_IRON_TO_DIAMOND_AMOUNT;
                validUpgradableItems = new ArrayList<>(Arrays.asList(Material.IRON_AXE,
                        Material.IRON_HOE,
                        Material.IRON_PICKAXE,
                        Material.IRON_SWORD,
                        Material.IRON_SHOVEL,
                        Material.IRON_HELMET,
                        Material.IRON_CHESTPLATE,
                        Material.IRON_LEGGINGS,
                        Material.IRON_BOOTS));
                matNeeded = Material.DIAMOND;
            } else if (slot0Item.getType().toString().contains("IRON") && slot1Item.getType().equals(Material.GOLD_INGOT)) {
                if (!main.config.IMPROVED_UPGRADING_IRON_TO_GOLD_ENABLED) return;
                matAmount = main.config.IMPROVED_UPGRADING_IRON_TO_GOLD_AMOUNT;
                validUpgradableItems = new ArrayList<>(Arrays.asList(Material.IRON_AXE,
                        Material.IRON_HOE,
                        Material.IRON_PICKAXE,
                        Material.IRON_SWORD,
                        Material.IRON_SHOVEL,
                        Material.IRON_HELMET,
                        Material.IRON_CHESTPLATE,
                        Material.IRON_LEGGINGS,
                        Material.IRON_BOOTS));
                matNeeded = Material.GOLD_INGOT;
            } else if (slot0Item.getType().toString().contains("DIAMOND")) {
                if (!main.config.IMPROVED_UPGRADING_DIAMOND_TO_NETHERITE_ENABLED) return;
                matAmount = main.config.IMPROVED_UPGRADING_DIAMOND_TO_NETHERITE_AMOUNT;
                validUpgradableItems = new ArrayList<>(Arrays.asList(Material.DIAMOND_AXE,
                        Material.DIAMOND_HOE,
                        Material.DIAMOND_PICKAXE,
                        Material.DIAMOND_SWORD,
                        Material.DIAMOND_SHOVEL,
                        Material.DIAMOND_HELMET,
                        Material.DIAMOND_CHESTPLATE,
                        Material.DIAMOND_LEGGINGS,
                        Material.DIAMOND_BOOTS));
                matNeeded = Material.NETHERITE_INGOT;
            }

            if (slot1Item.getType() != matNeeded) return;

            if (!validUpgradableItems.contains(slot0Item.getType())) return;

            if (slot1Item.getAmount() < matAmount) {
                event.setCancelled(true);
                Message.SMITHING_NOT_ENOUGH_MATS_UPGRADE.send(p, true, matAmount, formalizedString(matNeeded.toString()), formalizedString(slot0Item.getType().toString()));
            } else {
                slot1Item.setAmount(slot1Item.getAmount() - (matAmount - 1));
                Message.SMITHING_UPGRADE_SUCCESS.send(p, true, formalizedString(slot0Item.getType().toString()), formalizedString(slot2Item.getType().toString()), matAmount, formalizedString(matNeeded.toString()));
            }
        }
    }
}
