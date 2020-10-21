package com.github.steeldev.betternetherite.misc;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.managers.BNMobManager;
import com.github.steeldev.betternetherite.util.misc.BNPotionEffect;
import com.github.steeldev.betternetherite.util.mobs.DeathExplosionInfo;
import com.github.steeldev.betternetherite.util.mobs.ItemChance;
import com.github.steeldev.betternetherite.util.mobs.MountInfo;
import jdk.internal.jline.internal.Nullable;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class BNMob {
    BetterNetherite main = BetterNetherite.getInstance();
    public String Key;
    public String EntityName;
    public List<EntityType> EntityToReplace;
    public EntityType BaseEntity;
    public MountInfo MountInfo;
    public boolean Angry;
    public int DeathEXP;
    public float MaxHP;
    public float MoveSpeed;
    public int SpawnChance;
    public List<World.Environment> ValidSpawnWorlds;
    public List<BNPotionEffect> BNPotionEffectsInfo;
    public DeathExplosionInfo ExplosionOnDeathInfo;
    public List<Material> DropsToRemove;
    public List<ItemChance> Drops;
    public List<ItemChance> Equipment;

    public BNMob(String key,
                 String entityName,
                 List<EntityType> entityToReplace,
                 EntityType baseEntity,
                 MountInfo mountInfo,
                 boolean angry,
                 int deathEXP,
                 float maxHP,
                 float moveSpeed,
                 int spawnChance,
                 List<World.Environment> validSpawnWorlds,
                 List<BNPotionEffect> BNPotionEffectsInfo,
                 DeathExplosionInfo explosionOnDeathInfo,
                 List<Material> dropsToRemove,
                 List<ItemChance> drops,
                 List<ItemChance> equipment){
        this.Key = key;
        this.EntityName = entityName;
        this.EntityToReplace = entityToReplace;
        this.BaseEntity = baseEntity;
        this.MountInfo = mountInfo;
        this.Angry = angry;
        this.DeathEXP = deathEXP;
        this.MaxHP = maxHP;
        this.MoveSpeed = moveSpeed;
        this.SpawnChance = spawnChance;
        this.ValidSpawnWorlds = validSpawnWorlds;
        this.BNPotionEffectsInfo = BNPotionEffectsInfo;
        this.ExplosionOnDeathInfo = explosionOnDeathInfo;
        this.DropsToRemove = dropsToRemove;
        this.Drops = drops;
        this.Equipment = equipment;
    }

    public String getColoredName(){
        return main.colorize(EntityName);
    }

    public String getUncoloredName(){
        return ChatColor.stripColor(getColoredName());
    }

    public LivingEntity spawnMob(Location location, @Nullable LivingEntity spawnedEnt){
        World world = location.getWorld();
        if(spawnedEnt != null) {
            if (!spawnedEnt.getType().equals(BaseEntity))
                spawnedEnt.remove();
        }
        spawnedEnt = (LivingEntity) world.spawnEntity(location, BaseEntity);
        if(BaseEntity.equals(EntityType.WOLF)) {
            if(spawnedEnt instanceof Wolf)
                ((Wolf) spawnedEnt).setAngry(Angry);
        }
        spawnedEnt.setCustomName(main.colorize(EntityName));
        spawnedEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(MaxHP);
        spawnedEnt.setHealth(MaxHP);

        spawnedEnt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(MoveSpeed);

        ItemChance mainHand = Equipment.get(0);
        if(mainHand != null) {
            spawnedEnt.getEquipment().setItemInMainHand(mainHand.getItem());
            spawnedEnt.getEquipment().setItemInMainHandDropChance(mainHand.ChanceF);
        }
        ItemChance offHand = Equipment.get(1);
        if(offHand != null) {
            spawnedEnt.getEquipment().setItemInOffHand(offHand.getItem());
            spawnedEnt.getEquipment().setItemInOffHandDropChance(offHand.ChanceF);
        }
        ItemChance helmet = Equipment.get(2);
        if(helmet != null) {
            spawnedEnt.getEquipment().setHelmet(helmet.getItem());
            spawnedEnt.getEquipment().setHelmetDropChance(helmet.ChanceF);
        }
        ItemChance chestplate = Equipment.get(3);
        if(chestplate != null) {
            spawnedEnt.getEquipment().setChestplate(chestplate.getItem());
            spawnedEnt.getEquipment().setChestplateDropChance(chestplate.ChanceF);
        }
        ItemChance leggings = Equipment.get(4);
        if(leggings != null) {
            spawnedEnt.getEquipment().setLeggings(leggings.getItem());
            spawnedEnt.getEquipment().setLeggingsDropChance(leggings.ChanceF);
        }
        ItemChance boots = Equipment.get(5);
        if(boots != null) {
            spawnedEnt.getEquipment().setBoots(boots.getItem());
            spawnedEnt.getEquipment().setBootsDropChance(boots.ChanceF);
        }

        MountInfo mobMountInfo = MountInfo;
        boolean ridingMob = false;
        if(mobMountInfo.Riding != null) {
            if (main.chanceOf(mobMountInfo.Chance)) {
                LivingEntity entityToRide = mobMountInfo.spawnMount(location);
                entityToRide.setPassenger(spawnedEnt);
                ridingMob = true;
            }
        }

        if(BetterConfig.DEBUG) {
            String mobName = getUncoloredName();
            if(ridingMob)
                mobName = getUncoloredName() + " Rider";
            main.getLogger().info(main.colorize(String.format("&aCustom BN Mob &6%s &aspawned at &e%s&a!", mobName, location)));
        }

        spawnedEnt.getPersistentDataContainer().set(BNMobManager.MobsKey, PersistentDataType.STRING, Key);

        return spawnedEnt;
    }
}
