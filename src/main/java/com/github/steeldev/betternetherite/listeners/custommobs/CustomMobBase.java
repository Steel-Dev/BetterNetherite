package com.github.steeldev.betternetherite.listeners.custommobs;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import com.github.steeldev.betternetherite.misc.BNMob;
import com.github.steeldev.betternetherite.util.WhyNoWorkException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

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
        if(!e.getEntityType().equals(mob.EntityToReplace)) return;

        LivingEntity spawnedEnt = (LivingEntity)e.getEntity();
        if(main.chanceOf(mob.SpawnChance)) {
            if(!spawnedEnt.getType().equals(mob.BaseEntity))
            {
                spawnedEnt.remove();
                spawnedEnt = (LivingEntity) world.spawnEntity(e.getLocation(), mob.BaseEntity);
            }
            if(mob.BaseEntity.equals(EntityType.WOLF)) {
                if(spawnedEnt instanceof Wolf)
                    ((Wolf) spawnedEnt).setAngry(mob.Angry);
            }
            spawnedEnt.setCustomName(main.colorize(mob.EntityName));
            spawnedEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(mob.MaxHP);
            spawnedEnt.setHealth(mob.MaxHP);

            spawnedEnt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(mob.MoveSpeed);

            List<ItemStack> equipment = new ArrayList<>();
            List<Float> equipmentDropChances = new ArrayList<>();

            for(String entry : mob.Equipment){
                String[] entrySplit = entry.split(";");
                equipment.add(new ItemStack(Material.valueOf(entrySplit[0])));
                equipmentDropChances.add(Float.parseFloat(entrySplit[1]));
            }

            spawnedEnt.getEquipment().setItemInMainHand(equipment.get(0));
            spawnedEnt.getEquipment().setItemInMainHandDropChance(equipmentDropChances.get(0));

            spawnedEnt.getEquipment().setItemInOffHand(equipment.get(1));
            spawnedEnt.getEquipment().setItemInOffHandDropChance(equipmentDropChances.get(1));

            spawnedEnt.getEquipment().setHelmet(equipment.get(2));
            spawnedEnt.getEquipment().setHelmetDropChance(equipmentDropChances.get(2));

            spawnedEnt.getEquipment().setChestplate(equipment.get(3));
            spawnedEnt.getEquipment().setChestplateDropChance(equipmentDropChances.get(3));

            spawnedEnt.getEquipment().setLeggings(equipment.get(4));
            spawnedEnt.getEquipment().setLeggingsDropChance(equipmentDropChances.get(4));

            spawnedEnt.getEquipment().setBoots(equipment.get(5));
            spawnedEnt.getEquipment().setBootsDropChance(equipmentDropChances.get(5));
        }
    }

    @EventHandler
    public void customMobDeath(EntityDeathEvent e){
        if(mob == null) return;
        if(!e.getEntityType().equals(mob.BaseEntity)) return;
        if(e.getEntity().getCustomName() == null) return;
        if(!e.getEntity().getCustomName().equals(ChatColor.stripColor(mob.EntityName))) return;

        for(String entry : mob.Drops){
            String[] entrySplit = entry.split(";");

            int maxAm = Integer.parseInt(entrySplit[1]);
            int finalAm = (maxAm > 1) ? main.rand.nextInt(maxAm) : 1;
            if(finalAm < 1) finalAm = 1;
            if(finalAm > 64) finalAm = 64;

            ItemStack dropItem = new ItemStack(Material.valueOf(entrySplit[0]), finalAm);
            int dropChance = Integer.parseInt(entrySplit[2]);

            if(main.chanceOf(dropChance)){
                e.getDrops().add(dropItem);
            }
        }
        e.setDroppedExp(main.rand.nextInt(mob.DeathEXP));

        String[] explosionInfo = mob.ExplosionOnDeathInfo.split(";");

        boolean explodeOnDeath = Boolean.parseBoolean(explosionInfo[0]);
        int explosionChance = Integer.parseInt(explosionInfo[1]);
        int explosionSize = Integer.parseInt(explosionInfo[2]);
        boolean explosionCreatesFire = Boolean.parseBoolean(explosionInfo[3]);

        if(!explodeOnDeath) return;
        if(main.chanceOf(explosionChance))
            e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), explosionSize, explosionCreatesFire);
    }

    @EventHandler
    public void customMobDamageEntity(EntityDamageByEntityEvent e) {
        if (mob == null) return;
        if (!e.getDamager().getType().equals(mob.BaseEntity)) return;
        if(e.getDamager().getCustomName() == null) return;
        if (!e.getDamager().getCustomName().equals(ChatColor.stripColor(mob.EntityName))) return;

        if (e.getEntity() instanceof LivingEntity) {
            for(String entry : mob.HitEffectsInfo){
                String[] entrySplit = entry.split(";");
                PotionEffectType hitEffect = PotionEffectType.getByName(entrySplit[0]);
                if(hitEffect == null)
                    throw new WhyNoWorkException("Defined Hit Effect '" + entrySplit[0] + "' is not valid!");
                int hitEffectChance = Integer.parseInt(entrySplit[1]);
                int hitEffectAmplifier = Integer.parseInt(entrySplit[2]);
                int hitEffectDuration = Integer.parseInt(entrySplit[3]);

                LivingEntity victim = (LivingEntity) e.getEntity();
                if(main.chanceOf(hitEffectChance))
                    victim.addPotionEffect(new PotionEffect(hitEffect, hitEffectDuration, hitEffectAmplifier), false);
            }
        }
    }
}
