package com.github.steeldev.betternetherite.listeners.items.enchantments;

import com.github.steeldev.betternetherite.util.Util;
import com.github.steeldev.monstrorvm.api.enchantments.EnchantManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static com.github.steeldev.betternetherite.util.Util.getMain;


public class AncientRage implements Listener {
    Map<UUID, Integer> hitCount = new HashMap<>();
    List<UUID> cooldown = new ArrayList<>();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!getMain().config.CUSTOM_MOB_ANCIENT_PROTECTOR_ENABLED) return;
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        ItemStack handItem = player.getInventory().getItemInMainHand();

        if (EnchantManager.itemHasCustomEnchantment(handItem, "ancient_rage")) {
            if (cooldown.contains(player.getUniqueId())) return;
            int hitAm = 0;
            if (hitCount.containsKey(player.getUniqueId()))
                hitAm = hitCount.get(player.getUniqueId());

            hitAm++;
            hitCount.put(player.getUniqueId(), hitAm);
            if (hitAm >= 3 && hitAm <= 8) {
                if (hitAm == 3) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 1));
                    Util.sendActionBar(player, "&4&lAncient Rage activated! You will now do bonus damage for your next few hits!");
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 0.5f);
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 1));
                event.setDamage(event.getDamage() + 3);
                if (hitAm == 8) {
                    Util.sendActionBar(player, "&4&lAncient Rage deactivated!");
                    player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_HURT, 1, 0.6f);
                    player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                    hitCount.remove(player.getUniqueId());
                    if (!cooldown.contains(player.getUniqueId())) {
                        cooldown.add(player.getUniqueId());
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                cooldown.remove(player.getUniqueId());
                            }
                        }.runTaskLater(getMain(), 500);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (hitCount.containsKey(player.getUniqueId()))
            hitCount.remove(player.getUniqueId());
        if (cooldown.contains(player.getUniqueId()))
            cooldown.remove(player.getUniqueId());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (hitCount.containsKey(player.getUniqueId()))
            hitCount.remove(player.getUniqueId());
        if (cooldown.contains(player.getUniqueId()))
            cooldown.remove(player.getUniqueId());
    }
}
