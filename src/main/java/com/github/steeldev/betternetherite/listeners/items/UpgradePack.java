package com.github.steeldev.betternetherite.listeners.items;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.Lang;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
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
        NBTItem cursorNBT = new NBTItem(cursor);
        if (cursorNBT.hasKey("MVItem")) {
            ItemStack clickedItem = e.getCurrentItem();
            if(cursorNBT.getString("MVItem").equals("upgrade_pack")){
                if(validItems.contains(clickedItem.getType())) {
                    e.setCancelled(true);
                    Material replacement = Material.valueOf(clickedItem.getType().toString().replace("DIAMOND", "NETHERITE"));
                    String finalIt = formalizedString(clickedItem.getType().toString());
                    String upgradeSuccessMsg = Lang.PACK_UPGRADE_SUCCESS_MSG.replaceAll("ITEM", finalIt)
                            .replaceAll("UPGRADEDTO", formalizedString(replacement.toString()));
                    clickedItem.setType(replacement);

                    cursDam.setDamage(cursDam.getDamage() + 79);
                    cursor.setItemMeta((ItemMeta) cursDam);

                    player.getWorld().playSound(player.getLocation(),Sound.BLOCK_SMITHING_TABLE_USE,0.5f,0.2f);
                    player.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, upgradeSuccessMsg)));
                    if(cursDam.getDamage() >= (cursor.getType().getMaxDurability()-1)){
                        player.getWorld().playSound(player.getLocation(),Sound.BLOCK_ANVIL_BREAK,0.5f,0.2f);
                        cursor.setAmount(cursor.getAmount()-1);
                    }
                    player.setItemOnCursor(cursor);
                }
            }
        }
    }
}
