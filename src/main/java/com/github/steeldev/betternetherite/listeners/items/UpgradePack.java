package com.github.steeldev.betternetherite.listeners.items;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.util.Message;
import com.github.steeldev.monstrorvm.api.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

import static com.github.steeldev.betternetherite.util.Util.formalizedString;
import static com.github.steeldev.betternetherite.util.Util.monstrorvmEnabled;

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
        if(!monstrorvmEnabled()) return;
        if (!e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)
                || !e.getClick().equals(ClickType.RIGHT)
                || e.getCurrentItem() == null
                || e.getCursor() == null
                || e.getCursor().getItemMeta() == null
                || !main.config.UPGRADE_PACK_ENABLED
                || e.isCancelled())
            return;
        Player player = (Player) e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        Damageable cursDam = (Damageable) cursor.getItemMeta();
        if (ItemManager.isMVItem(cursor, "upgrade_pack")) {
            ItemStack clickedItem = e.getCurrentItem();
            if (validItems.contains(clickedItem.getType())) {
                e.setCancelled(true);
                Material replacement = Material.valueOf(clickedItem.getType().toString().replace("DIAMOND", "NETHERITE"));
                clickedItem.setType(replacement);

                cursDam.setDamage(cursDam.getDamage() + 79);
                cursor.setItemMeta((ItemMeta) cursDam);

                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_SMITHING_TABLE_USE, 0.5f, 0.2f);
                Message.UPGRADE_PACK_UPGRADE_SUCCESS.send(player, true, formalizedString(clickedItem.getType().toString()), formalizedString(replacement.toString()));
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
        if(!monstrorvmEnabled()) return;
        if (!main.config.UPGRADE_PACK_ENABLED) return;
        ItemStack slot1 = event.getInventory().getItem(0);
        ItemStack slot2 = event.getInventory().getItem(1);

        if (slot1 == null || slot1.getType().equals(Material.AIR)) return;
        if (slot2 == null || slot2.getType().equals(Material.AIR)) return;

        if (validItems.contains(slot1.getType())) {
            if (ItemManager.isMVItem(slot2, "upgrade_pack")) {
                Material replacement = Material.valueOf(slot1.getType().toString().replace("DIAMOND", "NETHERITE"));
                ItemStack resultItem = new ItemStack(replacement);
                resultItem.setItemMeta(slot1.getItemMeta());
                event.setResult(resultItem);
            }
        }
    }

    @EventHandler
    public void anvilClick(InventoryClickEvent event) {
        if(!monstrorvmEnabled()) return;
        if (!main.config.UPGRADE_PACK_ENABLED) return;
        Inventory evInv = event.getInventory();
        if (!evInv.getType().equals(InventoryType.ANVIL)) return;

        ItemStack slot1 = evInv.getItem(0);
        ItemStack slot2 = evInv.getItem(1);

        if (slot1 == null || slot1.getType().equals(Material.AIR)) return;
        if (slot2 == null || slot2.getType().equals(Material.AIR)) return;

        Player player = (Player) event.getViewers().get(0);

        if (event.getSlot() == 2) {
            if (validItems.contains(slot1.getType())) {
                if (ItemManager.isMVItem(slot2, "upgrade_pack")) {
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

    @EventHandler
    public void useUpgradePack(PlayerInteractEvent event) {
        if(!monstrorvmEnabled()) return;
        if (!main.config.UPGRADE_PACK_ENABLED
                || !event.getAction().equals(Action.RIGHT_CLICK_AIR)
                || !event.getHand().equals(EquipmentSlot.HAND)) return;

        Player player = event.getPlayer();

        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (mainHand.getType().equals(Material.AIR) || offHand.getType().equals(Material.AIR)) return;

        if (player.isSneaking()) {
            if (ItemManager.isMVItem(offHand, "upgrade_pack") && validItems.contains(mainHand.getType())) {
                Damageable upgradePack = (Damageable) offHand.getItemMeta();
                upgradePack.setDamage(upgradePack.getDamage() + 79);
                offHand.setItemMeta((ItemMeta) upgradePack);

                if (upgradePack.getDamage() >= (offHand.getType().getMaxDurability() - 1)) {
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 0.5f, 0.2f);
                    player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                } else
                    player.getInventory().setItemInOffHand(offHand);

                Material replacement = Material.valueOf(mainHand.getType().toString().replace("DIAMOND", "NETHERITE"));
                ItemStack resultItem = new ItemStack(replacement);
                resultItem.setItemMeta(mainHand.getItemMeta());

                player.getInventory().setItemInMainHand(resultItem);

                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_SMITHING_TABLE_USE, 0.5f, 0.2f);

                Message.UPGRADE_PACK_UPGRADE_SUCCESS.send(player, true, formalizedString(mainHand.getType().toString()), formalizedString(replacement.toString()));
            }
        }
    }
}
