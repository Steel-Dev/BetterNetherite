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

import java.util.ArrayList;
import java.util.List;

public class BNMob {
    public String key;
    public String entityName;
    public List<EntityType> entityToReplace;
    public EntityType baseEntity;
    public MountInfo mountInfo;
    public boolean angry;
    public int deathEXP;
    public float maxHP;
    public float moveSpeed;
    public int spawnChance;
    public List<World.Environment> validSpawnWorlds;
    public List<BNPotionEffect> hitEffects;
    public DeathExplosionInfo explosionOnDeathInfo;
    public List<Material> dropsToRemove;
    public List<ItemChance> drops;
    public List<ItemChance> equipment;
    BetterNetherite main = BetterNetherite.getInstance();

    public BNMob(String key,
                 EntityType baseEntity,
                 String entityName,
                 int spawnChance) {
        this.key = key;
        this.baseEntity = baseEntity;
        this.entityName = entityName;
        this.spawnChance = spawnChance;
    }

    public BNMob withEntityToReplace(EntityType entity) {
        if (this.entityToReplace == null) this.entityToReplace = new ArrayList<>();
        this.entityToReplace.add(entity);
        return this;
    }

    public BNMob withMount(MountInfo mountInfo) {
        this.mountInfo = mountInfo;
        return this;
    }

    public BNMob withAnger(boolean angry) {
        this.angry = angry;
        return this;
    }

    public BNMob withCustomDeathEXP(int exp) {
        this.deathEXP = exp;
        return this;
    }

    public BNMob withCustomMaxHP(float hp) {
        this.maxHP = hp;
        return this;
    }

    public BNMob withCustomMoveSpeed(float speed) {
        this.moveSpeed = speed;
        return this;
    }

    public BNMob withValidSpawnWorld(World.Environment world) {
        if (this.validSpawnWorlds == null) this.validSpawnWorlds = new ArrayList<>();
        this.validSpawnWorlds.add(world);
        return this;
    }

    public BNMob withHitEffect(BNPotionEffect effect) {
        if (this.hitEffects == null) this.hitEffects = new ArrayList<>();
        this.hitEffects.add(effect);
        return this;
    }

    public BNMob withDeathExplosion(boolean enabled, int chance, int size, boolean createsFire) {
        this.explosionOnDeathInfo = new DeathExplosionInfo(enabled, chance, size, createsFire);
        return this;
    }

    public BNMob withDropToRemove(Material drop) {
        if (this.dropsToRemove == null) this.dropsToRemove = new ArrayList<>();
        this.dropsToRemove.add(drop);
        return this;
    }

    public BNMob withDrop(ItemChance drop) {
        if (this.drops == null) this.drops = new ArrayList<>();
        this.drops.add(drop);
        return this;
    }

    public BNMob withMainHandItem(ItemChance item) {
        if (this.equipment == null) {
            this.equipment = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                this.equipment.add(null);
            }
        }
        this.equipment.set(0, item);
        return this;
    }

    public BNMob withOffhandItem(ItemChance item) {
        if (this.equipment == null) {
            this.equipment = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                this.equipment.add(null);
            }
        }
        this.equipment.set(1, item);
        return this;
    }

    public BNMob withHelmet(ItemChance item) {
        if (this.equipment == null) {
            this.equipment = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                this.equipment.add(null);
            }
        }
        this.equipment.set(2, item);
        return this;
    }

    public BNMob withChestplate(ItemChance item) {
        if (this.equipment == null) {
            this.equipment = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                this.equipment.add(null);
            }
        }
        this.equipment.set(3, item);
        return this;
    }

    public BNMob withLeggings(ItemChance item) {
        if (this.equipment == null) {
            this.equipment = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                this.equipment.add(null);
            }
        }
        this.equipment.set(4, item);
        return this;
    }

    public BNMob withBoots(ItemChance item) {
        if (this.equipment == null) {
            this.equipment = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                this.equipment.add(null);
            }
        }
        this.equipment.set(5, item);
        return this;
    }

    public String getColoredName() {
        return main.colorize(entityName);
    }

    public String getUncoloredName() {
        return ChatColor.stripColor(getColoredName());
    }

    public LivingEntity spawnMob(Location location, @Nullable LivingEntity spawnedEnt) {
        World world = location.getWorld();
        if (spawnedEnt != null) {
            if (!spawnedEnt.getType().equals(baseEntity))
                spawnedEnt.remove();
        }
        spawnedEnt = (LivingEntity) world.spawnEntity(location, baseEntity);
        if (baseEntity.equals(EntityType.WOLF)) {
            if (spawnedEnt instanceof Wolf)
                ((Wolf) spawnedEnt).setAngry(angry);
        }
        spawnedEnt.setCustomName(main.colorize(entityName));
        if (maxHP > 0) {
            spawnedEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHP);
            spawnedEnt.setHealth(maxHP);
        }

        if (moveSpeed > 0)
            spawnedEnt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(moveSpeed);

        if(equipment != null) {
            ItemChance mainHand = equipment.get(0);
            if (mainHand != null) {
                spawnedEnt.getEquipment().setItemInMainHand(mainHand.getItem(mainHand.damaged));
                spawnedEnt.getEquipment().setItemInMainHandDropChance(mainHand.chanceF);
            }
            ItemChance offHand = equipment.get(1);
            if (offHand != null) {
                spawnedEnt.getEquipment().setItemInOffHand(offHand.getItem(offHand.damaged));
                spawnedEnt.getEquipment().setItemInOffHandDropChance(offHand.chanceF);
            }
            ItemChance helmet = equipment.get(2);
            if (helmet != null) {
                spawnedEnt.getEquipment().setHelmet(helmet.getItem(helmet.damaged));
                spawnedEnt.getEquipment().setHelmetDropChance(helmet.chanceF);
            }
            ItemChance chestplate = equipment.get(3);
            if (chestplate != null) {
                spawnedEnt.getEquipment().setChestplate(chestplate.getItem(chestplate.damaged));
                spawnedEnt.getEquipment().setChestplateDropChance(chestplate.chanceF);
            }
            ItemChance leggings = equipment.get(4);
            if (leggings != null) {
                spawnedEnt.getEquipment().setLeggings(leggings.getItem(leggings.damaged));
                spawnedEnt.getEquipment().setLeggingsDropChance(leggings.chanceF);
            }
            ItemChance boots = equipment.get(5);
            if (boots != null) {
                spawnedEnt.getEquipment().setBoots(boots.getItem(boots.damaged));
                spawnedEnt.getEquipment().setBootsDropChance(boots.chanceF);
            }
        }

        boolean ridingMob = false;
        if (mountInfo != null) {
            if (mountInfo.riding != null) {
                if (main.chanceOf(mountInfo.chance)) {
                    LivingEntity entityToRide = mountInfo.spawnMount(location);
                    entityToRide.setPassenger(spawnedEnt);
                    ridingMob = true;
                }
            }
        }

        if (BetterConfig.DEBUG) {
            String mobName = getUncoloredName();
            if (ridingMob)
                mobName = getUncoloredName() + " Rider";
            main.getLogger().info(main.colorize(String.format("&aCustom Mob &6%s &aspawned at &e%s&a!", mobName, location)));
        }

        spawnedEnt.getPersistentDataContainer().set(BNMobManager.MobsKey, PersistentDataType.STRING, key);

        return spawnedEnt;
    }
}
