package com.github.steeldev.betternetherite.listeners.inventory;

import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.managers.BNItemManager;
import com.github.steeldev.betternetherite.misc.BNItem;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static com.github.steeldev.betternetherite.util.Util.colorize;

public class BNItemListInventory implements Listener {
    public static String INVENTORY_NAME = "&6Better&cNetherite &eItems";

    @EventHandler
    public void bnItemListInvClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (p.getOpenInventory().getTitle().equals(colorize(INVENTORY_NAME))) {
            e.setCancelled(true);
            if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType().equals(Material.AIR))) {
                return;
            }

            ItemStack clickedItem = e.getCurrentItem();
            NBTItem clickedItemNBT = new NBTItem(clickedItem);

            if (clickedItemNBT.hasKey("InventoryAction")) {
                if(clickedItemNBT.getString("InventoryAction").equals("CLOSE")) {
                    p.closeInventory();
                    return;
                }

                if(clickedItemNBT.getString("InventoryAction").equals("GIVE_ITEM")){
                    BNItem bnItem = BNItemManager.getBNItem(clickedItemNBT.getString("ItemToGive"));
                    ItemStack itemToGive = bnItem.getItem(false);

                    if(p.getInventory().firstEmpty() != -1){
                        p.getInventory().addItem(itemToGive);
                        p.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.CUSTOM_ITEM_GIVEN_MSG
                                .replace("ITEMNAME", bnItem.displayName).replace("PLAYERNAME", p.getDisplayName())
                                .replace("ITEMAMOUNT", String.valueOf(1)))));
                    }else{
                        p.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.CUSTOM_ITEM_PLAYER_INVENTORY_FULL_MSG
                                .replace("ITEMNAME", bnItem.displayName).replace("PLAYERNAME", p.getDisplayName())
                                .replace("ITEMAMOUNT", String.valueOf(1)))));
                    }
                }
            }
        }
    }
}
