package com.github.steeldev.betternetherite.listeners.baselisteners;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.managers.BNItemManager;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import com.github.steeldev.betternetherite.misc.BNItem;
import com.github.steeldev.betternetherite.misc.BNMob;
import com.github.steeldev.betternetherite.util.items.ItemUseEffectType;
import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import static com.github.steeldev.betternetherite.util.Util.chanceOf;

public class CustomItemBase implements Listener {

    BetterNetherite main = BetterNetherite.getInstance();

    BNItem item;

    public CustomItemBase(String itemID) {
        item = BNItemManager.getBNItem(itemID);
    }

    @EventHandler
    public void customItemAttack(EntityDamageByEntityEvent event) {
        if (item == null) return;
        if (event.isCancelled()) return;
        if (event.getDamager() instanceof Player) {
            ItemStack attackItem = ((Player) event.getDamager()).getInventory().getItemInMainHand();
            if (attackItem.getType().equals(Material.AIR)) return;
            if (attackItem.getType() != item.baseItem) return;
            NBTItem attackItemNBT = new NBTItem(attackItem);
            if (!attackItemNBT.hasKey("BNItem")) return;
            if (!attackItemNBT.getString("BNItem").equals(item.key)) return;

            if (item.attackEffect == null || item.attackEffect.size() < 1) return;

            if (event.getEntity() instanceof LivingEntity) {
                for (BNPotionEffect entry : item.attackEffect) {
                    LivingEntity victim = (LivingEntity) event.getEntity();
                    if (chanceOf(entry.chance)) {
                        victim.addPotionEffect(entry.getPotionEffect(), false);
                        if (BetterConfig.DEBUG)
                            main.getLogger().info(String.format("&aCustom item &6%s &cinflicted &e%s &cwith &4%s&c!", item.displayName, victim.getName(), entry.effect));
                    }
                }
            }
        }
    }

    @EventHandler
    public void customItemUse(PlayerInteractEvent event) {
        if (item == null) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.isCancelled()) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK &&
                event.getAction() != Action.RIGHT_CLICK_AIR) return;
        Player player = event.getPlayer();
        ItemStack useItem = event.getPlayer().getInventory().getItemInMainHand();
        if (useItem.getType().equals(Material.AIR)) return;
        if (useItem.getType() != item.baseItem) return;
        NBTItem useItemNBT = new NBTItem(useItem);
        if (!useItemNBT.hasKey("BNItem")) return;
        if (!useItemNBT.getString("BNItem").equals(item.key)) return;

        if (item.useEffect == null) return;

        event.setCancelled(true);

        if (item.useEffect.type == ItemUseEffectType.SPAWN_CUSTOM_MOB) {
            if (event.getClickedBlock() == null) return;
            if (event.getClickedBlock().getType() == Material.AIR) return;
            if (item.useEffect.mobID.equals("")) return;

            BNMob mobToSpawn = BNMobManager.getBNMob(item.useEffect.mobID);

            if (mobToSpawn != null)
                mobToSpawn.spawnMob(event.getClickedBlock().getLocation().add(0, 1, 0), null);
        } else if (item.useEffect.type == ItemUseEffectType.EFFECT_HOLDER) {
            for (BNPotionEffect effect : item.useEffect.potionEffects) {
                if (chanceOf(effect.chance)) {
                    player.addPotionEffect(effect.getPotionEffect(), false);
                    if (BetterConfig.DEBUG)
                        main.getLogger().info(String.format("&aCustom Item &6%s &cinflicted &e%s &cwith &4%s&c!", item.displayName, event.getPlayer().getName(), item.consumeEffect.effectDisplay));
                }
            }
        }
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE && item.useEffect.consumeItemOnUse ||
                (event.getPlayer().getGameMode() != GameMode.CREATIVE && item.useEffect.type == ItemUseEffectType.SPAWN_CUSTOM_MOB))
            useItem.setAmount(useItem.getAmount() - 1);
    }

    @EventHandler
    public void customItemUseOnEntity(PlayerInteractAtEntityEvent event) {
        if (item == null) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        ItemStack useItem = event.getPlayer().getInventory().getItemInMainHand();
        if (useItem.getType().equals(Material.AIR)) return;
        if (useItem.getType() != item.baseItem) return;
        NBTItem useItemNBT = new NBTItem(useItem);
        if (!useItemNBT.hasKey("BNItem")) return;
        if (!useItemNBT.getString("BNItem").equals(item.key)) return;

        if (item.useEffect == null) return;

        event.setCancelled(true);

        if (item.useEffect.type == ItemUseEffectType.EFFECT_CLICKED) {
            if (!(event.getRightClicked() instanceof LivingEntity))
                return;
            for (BNPotionEffect effect : item.useEffect.potionEffects) {
                LivingEntity victim = (LivingEntity) event.getRightClicked();
                if (chanceOf(effect.chance)) {
                    victim.addPotionEffect(effect.getPotionEffect(), false);
                    if (BetterConfig.DEBUG)
                        main.getLogger().info(String.format("&aCustom Item &6%s &aheld by &e%s &cinflicted &e%s &cwith &4%s&c!", item.displayName, player.getName(), victim.getName(), effect));
                }
            }
        }

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE && item.useEffect.consumeItemOnUse ||
                (event.getPlayer().getGameMode() != GameMode.CREATIVE && item.useEffect.type == ItemUseEffectType.SPAWN_CUSTOM_MOB))
            useItem.setAmount(useItem.getAmount() - 1);
    }

    @EventHandler
    public void customItemConsume(PlayerItemConsumeEvent event) {
        if (item == null) return;
        if (event.isCancelled()) return;

        ItemStack consumedItem = event.getItem();
        if (consumedItem.getType().equals(Material.AIR)) return;
        if (consumedItem.getType() != item.baseItem) return;
        NBTItem consumedItemNBT = new NBTItem(consumedItem);
        if (!consumedItemNBT.hasKey("BNItem")) return;
        if (!consumedItemNBT.getString("BNItem").equals(item.key)) return;

        if (item.consumeEffect == null) return;

        boolean effected = false;

        for (BNPotionEffect effect : item.consumeEffect.potionEffects) {
            if (chanceOf(effect.chance)) {
                event.getPlayer().addPotionEffect(effect.getPotionEffect(), false);
                if (BetterConfig.DEBUG)
                    main.getLogger().info(String.format("&aCustom Item &6%s &cinflicted &e%s &cwith &4%s&c!", item.displayName, event.getPlayer().getName(), item.consumeEffect.effectDisplay));
                effected = true;
            }
        }
        if (effected)
            event.getPlayer().sendMessage(String.format("&7You ate %s &7and got effected with %s", item.displayName, item.consumeEffect.effectDisplay));
    }
}
