package com.github.steeldev.betternetherite.listeners.items;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.Lang;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

import static com.github.steeldev.betternetherite.util.Util.colorize;
import static com.github.steeldev.betternetherite.util.Util.formalizedString;

public class UpgradePack implements Listener {
    BetterNetherite main = BetterNetherite.getInstance();

    List<Material> validItems = Arrays.asList(Material.DIAMOND_SWORD,
            Material.DIAMOND_AXE,
            Material.DIAMOND_PICKAXE,
            Material.DIAMOND_SHOVEL,
            Material.DIAMOND_HOE,
            Material.DIAMOND_HELMET,
            Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_LEGGINGS,
            Material.DIAMOND_BOOTS);

    @EventHandler
    public void useUpgradePack(InventoryClickEvent e) {
        if (!e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)
                || !e.getClick().equals(ClickType.RIGHT)
                || e.getCurrentItem() == null
                || e.getCursor() == null
                || e.getCursor().getItemMeta() == null
                || e.isCancelled())
            return;
        Player player = (Player) e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        Damageable cursDam = (Damageable) cursor.getItemMeta();
        if (com.github.steeldev.monstrorvm.util.Util.isMVItem(cursor, "upgrade_pack")) {
            ItemStack clickedItem = e.getCurrentItem();
            if (validItems.contains(clickedItem.getType())) {
                e.setCancelled(true);
                Material replacement = Material.valueOf(clickedItem.getType().toString().replace("DIAMOND", "NETHERITE"));
                String finalIt = formalizedString(clickedItem.getType().toString());
                String upgradeSuccessMsg = Lang.PACK_UPGRADE_SUCCESS_MSG.replaceAll("ITEM", finalIt)
                        .replaceAll("UPGRADEDTO", formalizedString(replacement.toString()));
                clickedItem.setType(replacement);

                cursDam.setDamage(cursDam.getDamage() + 79);
                cursor.setItemMeta((ItemMeta) cursDam);

                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_SMITHING_TABLE_USE, 0.5f, 0.2f);
                player.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, upgradeSuccessMsg)));
                if (cursDam.getDamage() >= (cursor.getType().getMaxDurability() - 1)) {
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 0.5f, 0.2f);
                    cursor.setAmount(cursor.getAmount() - 1);
                }
                player.setItemOnCursor(cursor);
            }
        }
    }

    @EventHandler
    public void anvilPrepare(PrepareAnvilEvent event) {
        ItemStack slot1 = event.getInventory().getItem(0);
        ItemStack slot2 = event.getInventory().getItem(1);

        if (slot1 == null || slot1.getType().equals(Material.AIR)) return;
        if (slot2 == null || slot2.getType().equals(Material.AIR)) return;

        if (validItems.contains(slot1.getType())) {
            if (com.github.steeldev.monstrorvm.util.Util.isMVItem(slot2, "upgrade_pack")) {
                Material replacement = Material.valueOf(slot1.getType().toString().replace("DIAMOND", "NETHERITE"));
                ItemStack resultItem = new ItemStack(replacement);
                resultItem.setItemMeta(slot1.getItemMeta());
                event.setResult(resultItem);
            }
        }
    }

    @EventHandler
    public void anvilClick(InventoryClickEvent event) {
        Inventory evInv = event.getInventory();
        if (!evInv.getType().equals(InventoryType.ANVIL)) return;

        ItemStack slot1 = evInv.getItem(0);
        ItemStack slot2 = evInv.getItem(1);

        if (slot1 == null || slot1.getType().equals(Material.AIR)) return;
        if (slot2 == null || slot2.getType().equals(Material.AIR)) return;

        Player player = (Player) event.getViewers().get(0);

        if (event.getSlot() == 2) {
            if (validItems.contains(slot1.getType())) {
                if (com.github.steeldev.monstrorvm.util.Util.isMVItem(slot2, "upgrade_pack")) {
                    player.setItemOnCursor(event.getCurrentItem());
                    slot1.setType(Material.AIR);
                    evInv.setItem(0, slot1);

                    Damageable upgradePack = (Damageable) slot2.getItemMeta();

                    upgradePack.setDamage(upgradePack.getDamage() + 79);
                    slot2.setItemMeta((ItemMeta) upgradePack);

                    if (upgradePack.getDamage() >= (slot2.getType().getMaxDurability() - 1)) {
                        slot2.setAmount(slot2.getAmount() - 1);
                    }
                    evInv.setItem(1, slot2);

                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 0.6f, 1f);
                }
            }
        }
    }
}
