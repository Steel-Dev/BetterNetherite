package com.github.steeldev.betternetherite.listeners.inventory;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.commands.admin.ListBNItems;
import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.managers.BNItemManager;
import com.github.steeldev.betternetherite.misc.BNItem;
import com.google.common.collect.Lists;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static com.github.steeldev.betternetherite.util.Util.colorize;
import static com.github.steeldev.betternetherite.util.Util.getUncoloredItemName;

public class BNItemListInventory implements Listener {
    static BetterNetherite main = BetterNetherite.getInstance();
    public static String INVENTORY_NAME = "&6Better&cNetherite &eItems";

    @EventHandler
    public void bnItemListInvClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (p.getOpenInventory().getTitle().contains(colorize(INVENTORY_NAME))) {
            e.setCancelled(true);
            if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType().equals(Material.AIR))) {
                return;
            }

            ItemStack clickedItem = e.getCurrentItem();
            NBTItem clickedItemNBT = new NBTItem(clickedItem);

            if (clickedItemNBT.hasKey("InventoryAction")) {
                if(clickedItemNBT.getString("InventoryAction").equals("CLOSE")) {
                    p.closeInventory();
                    p.getPersistentDataContainer().remove(ListBNItems.bnPageKey);
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

                if(clickedItemNBT.getString("InventoryAction").equals("NEXT_PAGE")){
                    int curPage = clickedItemNBT.getInteger("CurrentPage")+1;

                    p.sendMessage("New page : " + (curPage+1));

                    openListInventory(p,curPage);
                }
                if(clickedItemNBT.getString("InventoryAction").equals("LAST_PAGE")){
                    int curPage = clickedItemNBT.getInteger("CurrentPage")-1;

                    p.sendMessage("New page : " + (curPage+1));

                    openListInventory(p,curPage);
                }
            }
        }
    }

    public static void openListInventory(Player player, int page) {
        Inventory bnItems = Bukkit.getServer().createInventory(player, 54, colorize(BNItemListInventory.INVENTORY_NAME + " &7Page: &e"+ (page+1)));

        for (int i = 0; i < 53; i++) {
            ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta meta = (blackGlass.getItemMeta() == null) ? Bukkit.getItemFactory().getItemMeta(blackGlass.getType()) : blackGlass.getItemMeta();
            meta.setDisplayName(colorize("&7"));
            blackGlass.setItemMeta(meta);
            bnItems.setItem(i, blackGlass);
        }

        List<ItemStack> listedItems = new ArrayList<>();

        for (String item : BNItemManager.getValidItemList()) {
            BNItem customItem = BNItemManager.getBNItem(item);
            ItemStack bnItem = customItem.getItem(false);
            NBTItem bnItemNBT = new NBTItem(bnItem);
            bnItemNBT.addCompound("InventoryAction");
            bnItemNBT.setString("InventoryAction", "GIVE_ITEM");
            bnItemNBT.addCompound("ItemToGive");
            bnItemNBT.setString("ItemToGive", customItem.key);
            bnItem = bnItemNBT.getItem();
            listedItems.add(bnItem);
        }

        // For testing
        /*for (int i = 0; i < 100; i++) {
            listedItems.add(new ItemStack(Material.DIAMOND));
        }*/

        listedItems.sort((o1, o2) -> getUncoloredItemName(o1).compareToIgnoreCase(getUncoloredItemName(o2)));

        final List<List<ItemStack>> pages = Lists.partition(listedItems, 51);

        player.sendMessage(colorize("Now on page " + (page+1) + " ("+page+")"));
        player.sendMessage(colorize("Items : " + listedItems.size() + " - " + pages.size()));

        List<ItemStack> content = pages.get(page);

        for (int i = 0; i < content.size(); i++) {
            bnItems.setItem(i, content.get(i));
        }

        ItemStack closeInv = new ItemStack(Material.BARRIER);
        ItemMeta closeInvMeta = closeInv.getItemMeta();
        closeInvMeta.setDisplayName(colorize("&cClose"));
        closeInv.setItemMeta(closeInvMeta);
        NBTItem closeInvNBT = new NBTItem(closeInv);
        closeInvNBT.addCompound("InventoryAction");
        closeInvNBT.setString("InventoryAction", "CLOSE");
        closeInv = closeInvNBT.getItem();
        bnItems.setItem(53, closeInv);

        boolean showNextPage = false;
        boolean showLastPage = false;

        if (page > 0 && page < (pages.size()-1)) {
            showNextPage = true;
            showLastPage = true;
        } else if (page == 0 && pages.size() > 1) {
            showNextPage = true;
            showLastPage = false;
        } else if (page == (pages.size()-1) && pages.size() > 1) {
            showNextPage = false;
            showLastPage = true;
        }

        if (showNextPage) {
            ItemStack nextPage = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta nextPageMeta = (SkullMeta) nextPage.getItemMeta();
            nextPageMeta.setOwningPlayer(Bukkit.getOfflinePlayer("MHF_arrowright"));
            nextPageMeta.setDisplayName(colorize("&2Next Page"));
            nextPage.setItemMeta(nextPageMeta);
            NBTItem nextPageNBT = new NBTItem(nextPage);
            nextPageNBT.addCompound("InventoryAction");
            nextPageNBT.setString("InventoryAction", "NEXT_PAGE");
            nextPageNBT.addCompound("CurrentPage");
            nextPageNBT.setInteger("CurrentPage", page);
            nextPage = nextPageNBT.getItem();
            bnItems.setItem(52, nextPage);
        }

        if(showLastPage) {
            ItemStack lastPage = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta lastPageMeta = (SkullMeta) lastPage.getItemMeta();
            lastPageMeta.setOwningPlayer(Bukkit.getOfflinePlayer("MHF_arrowleft"));
            lastPageMeta.setDisplayName(colorize("&2Last Page"));
            lastPage.setItemMeta(lastPageMeta);
            NBTItem lastPageNBT = new NBTItem(lastPage);
            lastPageNBT.addCompound("InventoryAction");
            lastPageNBT.setString("InventoryAction", "LAST_PAGE");
            lastPageNBT.addCompound("CurrentPage");
            lastPageNBT.setInteger("CurrentPage", page);
            lastPage = lastPageNBT.getItem();
            bnItems.setItem(51, lastPage);
        }

        player.openInventory(bnItems);
    }
}
