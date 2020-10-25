package com.github.steeldev.betternetherite.listeners.events;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

import static com.github.steeldev.betternetherite.util.Util.chanceOf;
import static com.github.steeldev.betternetherite.util.Util.rand;

public class NetheriteFishing implements Listener {
    final static BetterNetherite main = BetterNetherite.getInstance();

    @EventHandler
    public void playerFishEvent(PlayerFishEvent e) {
        if (!BetterConfig.NETHERITE_FISH_TREASURE_ENABLED) return;

        if (e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            boolean gotNewItem = false;
            if (e.getCaught() != null) {
                ItemStack caught = ((Item) e.getCaught()).getItemStack();
                if (!caught.getType().equals(Material.COD) &&
                        !caught.getType().equals(Material.TROPICAL_FISH) &&
                        !caught.getType().equals(Material.SALMON) &&
                        !caught.getType().equals(Material.PUFFERFISH)) {
                    for (String lootEntry : BetterConfig.NETHERITE_FISH_TREASURE_LOOT) {
                        List<String> lootEntrySplit = Arrays.asList(lootEntry.split(";"));

                        int maxAm = Integer.parseInt(lootEntrySplit.get(1));
                        int finalAm = (maxAm > 1) ? rand.nextInt(maxAm) : 1;
                        if (finalAm < 1) finalAm = 1;
                        if (finalAm > 64) finalAm = 64;

                        ItemStack lootItem = new ItemStack(Material.valueOf(lootEntrySplit.get(0)), finalAm);
                        ItemMeta lootItemMeta = (lootItem.getItemMeta() == null) ? Bukkit.getItemFactory().getItemMeta(lootItem.getType()) : lootItem.getItemMeta();

                        if (lootItemMeta instanceof Damageable) {
                            if (lootItem.getType().getMaxDurability() > 0)
                                ((Damageable) lootItemMeta).setDamage(lootItem.getType().getMaxDurability() - 20);
                        }

                        lootItem.setItemMeta(lootItemMeta);

                        int chance = Integer.parseInt(lootEntrySplit.get(2));

                        if (chanceOf(chance)) {
                            if (!gotNewItem) {
                                caught.setItemMeta(lootItem.getItemMeta());
                                caught.setType(lootItem.getType());
                                caught.setAmount(lootItem.getAmount());
                                ((Item) e.getCaught()).setItemStack(caught);
                                gotNewItem = true;
                            }
                        }
                    }
                }
            }
        }
    }
}
