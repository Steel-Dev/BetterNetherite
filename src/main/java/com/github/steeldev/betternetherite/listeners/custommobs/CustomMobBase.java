package com.github.steeldev.betternetherite.listeners.custommobs;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import com.github.steeldev.betternetherite.misc.BNMob;
import com.github.steeldev.betternetherite.util.HitEffect;
import com.github.steeldev.betternetherite.util.ItemChance;
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
    public CustomMobBase(String mobID){
        this.mob = BNMobManager.getBNMob(mobID);
    }

    @EventHandler
    public void customMobSpawn(EntitySpawnEvent e){
        World world = e.getLocation().getWorld();
        if(mob == null) return;
        if(world == null) return;
        if(!mob.ValidSpawnWorlds.contains(world.getEnvironment())) return;
        if(e.getEntity().getCustomName() != null) return;
        if(!e.getEntityType().equals(mob.EntityToReplace)) return;


        if(main.chanceOf(mob.SpawnChance))
            mob.spawnMob(e.getLocation(), (LivingEntity) e.getEntity());
    }

    @EventHandler
    public void customMobDeath(EntityDeathEvent e){
        if(mob == null) return;
        if(!e.getEntityType().equals(mob.BaseEntity)) return;
        if(e.getEntity().getCustomName() == null) return;
        if(!e.getEntity().getPersistentDataContainer().has(BNMobManager.MobsKey, PersistentDataType.STRING)) return;
        if(!ChatColor.stripColor(e.getEntity().getCustomName()).equals(mob.getUncoloredName())) return;

        if(mob.DropsToRemove != null && mob.DropsToRemove.size() > 0)
            e.getDrops().removeIf(item -> mob.DropsToRemove.contains(item.getType()));

        for(ItemChance entry : mob.Drops){
            if(main.chanceOf(entry.Chance)) {
                ItemStack dropItem = entry.getItem();
                e.getDrops().add(dropItem);
            }
        }
        e.setDroppedExp(main.rand.nextInt(mob.DeathEXP));

        if(!mob.ExplosionOnDeathInfo.Enabled) return;
        if(main.chanceOf(mob.ExplosionOnDeathInfo.Chance))
            e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), mob.ExplosionOnDeathInfo.Size, mob.ExplosionOnDeathInfo.CreatesFire);
    }

    @EventHandler
    public void customMobDamageEntity(EntityDamageByEntityEvent e) {
        if (mob == null) return;
        if (!e.getDamager().getType().equals(mob.BaseEntity)) return;
        if(e.getDamager().getCustomName() == null) return;
        if(!e.getEntity().getPersistentDataContainer().has(BNMobManager.MobsKey, PersistentDataType.STRING)) return;
        if(!ChatColor.stripColor(e.getDamager().getCustomName()).equals(mob.getUncoloredName())) return;

        if (e.getEntity() instanceof LivingEntity) {
            for(HitEffect entry : mob.HitEffectsInfo){
                LivingEntity victim = (LivingEntity) e.getEntity();
                if(main.chanceOf(entry.Chance)) {
                    victim.addPotionEffect(entry.getPotionEffect(), false);
                    if(BetterConfig.DEBUG)
                        main.getLogger().info(main.colorize(String.format("&aCustom Mob &6%s &cinflicted &e%s &cwith &4%s&c!", mob.getUncoloredName(), victim.getName(), entry.Effect)));
                }
            }
        }
    }
}
