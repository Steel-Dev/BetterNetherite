package com.github.steeldev.betternetherite.listeners.baselisteners;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import com.github.steeldev.betternetherite.misc.BNMob;
import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;
import com.github.steeldev.betternetherite.util.mobs.ItemChance;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class CustomMobBase implements Listener {
    BetterNetherite main = BetterNetherite.getInstance();
    BNMob mob;

    public CustomMobBase(String mobID) {
        this.mob = BNMobManager.getBNMob(mobID);
    }

    @EventHandler
    public void customMobSpawn(EntitySpawnEvent e) {
        World world = e.getLocation().getWorld();
        if (mob == null) return;
        if (world == null) return;
        if (mob.validSpawnWorlds == null || !mob.validSpawnWorlds.contains(world.getEnvironment())) return;
        if (e.getEntity().getCustomName() != null) return;
        if (mob.entityToReplace == null || mob.entityToReplace.size() < 1 || !mob.entityToReplace.contains(e.getEntityType()))
            return;


        if (main.chanceOf(mob.spawnChance))
            mob.spawnMob(e.getLocation(), (LivingEntity) e.getEntity());
    }

    @EventHandler
    public void customMobDeath(EntityDeathEvent e) {
        if (mob == null) return;
        if (!e.getEntityType().equals(mob.baseEntity)) return;
        if (e.getEntity().getCustomName() == null) return;
        if (!e.getEntity().getPersistentDataContainer().has(BNMobManager.MobsKey, PersistentDataType.STRING)) return;
        if (!ChatColor.stripColor(e.getEntity().getCustomName()).equals(mob.getUncoloredName())) return;

        if (mob.dropsToRemove != null && mob.dropsToRemove.size() > 0)
            e.getDrops().removeIf(item -> mob.dropsToRemove.contains(item.getType()));

        if (mob.drops != null && mob.drops.size() > 0) {
            for (ItemChance entry : mob.drops) {
                if (main.chanceOf(entry.chance)) {
                    ItemStack dropItem = entry.getItem(entry.damaged);
                    e.getDrops().add(dropItem);
                }
            }
        }
        e.setDroppedExp(main.rand.nextInt(mob.deathEXP));

        if (mob.explosionOnDeathInfo == null) return;
        if (!mob.explosionOnDeathInfo.enabled) return;
        if (main.chanceOf(mob.explosionOnDeathInfo.chance))
            e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), mob.explosionOnDeathInfo.size, mob.explosionOnDeathInfo.createsFire);
    }

    @EventHandler
    public void customMobDamageEntity(EntityDamageByEntityEvent e) {
        if (mob == null) return;
        if (!e.getDamager().getType().equals(mob.baseEntity)) return;
        if (e.getDamager().getCustomName() == null) return;
        if (!e.getEntity().getPersistentDataContainer().has(BNMobManager.MobsKey, PersistentDataType.STRING)) return;
        if (!ChatColor.stripColor(e.getDamager().getCustomName()).equals(mob.getUncoloredName())) return;

        if (e.getEntity() instanceof LivingEntity) {
            if (mob.hitEffects != null && mob.hitEffects.size() > 0) {
                for (BNPotionEffect entry : mob.hitEffects) {
                    LivingEntity victim = (LivingEntity) e.getEntity();
                    if (main.chanceOf(entry.chance)) {
                        victim.addPotionEffect(entry.getPotionEffect(), false);
                        if (BetterConfig.DEBUG)
                            main.getLogger().info(main.colorize(String.format("&aCustom Mob &6%s &cinflicted &e%s &cwith &4%s&c!", mob.getUncoloredName(), victim.getName(), entry.effect)));
                    }
                }
            }
        }
    }
}
