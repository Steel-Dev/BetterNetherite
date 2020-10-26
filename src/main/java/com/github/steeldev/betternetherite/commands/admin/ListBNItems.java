package com.github.steeldev.betternetherite.commands.admin;

import com.github.steeldev.betternetherite.config.Lang;
import com.github.steeldev.betternetherite.listeners.inventory.BNItemListInventory;
import com.github.steeldev.betternetherite.managers.BNItemManager;
import com.github.steeldev.betternetherite.misc.BNItem;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.github.steeldev.betternetherite.util.Util.colorize;
import static com.github.steeldev.betternetherite.util.Util.getUncoloredItemName;

public class ListBNItems implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Inventory bnItems = Bukkit.getServer().createInventory(player, 54, colorize(BNItemListInventory.INVENTORY_NAME));

            for (int i = 0; i < bnItems.getSize()-1; i++) {
                ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                ItemMeta meta = (blackGlass.getItemMeta() == null) ? Bukkit.getItemFactory().getItemMeta(blackGlass.getType()) : blackGlass.getItemMeta();
                meta.setDisplayName(colorize("&7"));
                blackGlass.setItemMeta(meta);
                bnItems.setItem(i, blackGlass);
            }

            List<ItemStack> listedItems = new ArrayList<>();

            for(String item : BNItemManager.getValidItemList()){
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

            listedItems.sort((o1, o2) -> getUncoloredItemName(o1).compareToIgnoreCase(getUncoloredItemName(o2)));

            for (int i = 0; i < listedItems.size()-1; i++) {
                bnItems.setItem(i,listedItems.get(i));
            }

            ItemStack closeInv = new ItemStack(Material.BARRIER);
            NBTItem closeInvNBT = new NBTItem(closeInv);
            closeInvNBT.addCompound("InventoryAction");
            closeInvNBT.setString("InventoryAction", "CLOSE");
            closeInv = closeInvNBT.getItem();
            bnItems.setItem(bnItems.getSize()-1, closeInv);

            player.openInventory(bnItems);
        }
        else{
            commandSender.sendMessage(colorize(String.format("%s%s", Lang.PREFIX, Lang.PLAYERS_ONLY_MSG)));
        }
        return true;
    }
}
