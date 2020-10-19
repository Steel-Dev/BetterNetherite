package com.github.steeldev.betternetherite.misc;

import org.bukkit.World;
import org.bukkit.entity.EntityType;

import java.util.List;

public class BNMob {
    public String Key;
    public String EntityName;
    public EntityType EntityToReplace;
    public EntityType BaseEntity;
    public boolean Angry;
    public int DeathEXP;
    public float MaxHP;
    public float MoveSpeed;
    public int SpawnChance;
    public List<World.Environment> ValidSpawnWorlds;
    public List<String> HitEffectsInfo;
    public String ExplosionOnDeathInfo;
    public List<String> Drops;
    public List<String> Equipment;

    public BNMob(String key,
                 String entityName,
                 EntityType entityToReplace,
                 EntityType baseEntity,
                 boolean angry,
                 int deathEXP,
                 float maxHP,
                 float moveSpeed,
                 int spawnChance,
                 List<World.Environment> validSpawnWorlds,
                 List<String> hitEffectsInfo,
                 String explosionOnDeathInfo,
                 List<String> drops,
                 List<String> equipment){
        this.Key = key;
        this.EntityName = entityName;
        this.EntityToReplace = entityToReplace;
        this.BaseEntity = baseEntity;
        this.Angry = angry;
        this.DeathEXP = deathEXP;
        this.MaxHP = maxHP;
        this.MoveSpeed = moveSpeed;
        this.SpawnChance = spawnChance;
        this.ValidSpawnWorlds = validSpawnWorlds;
        this.HitEffectsInfo = hitEffectsInfo;
        this.ExplosionOnDeathInfo = explosionOnDeathInfo;
        this.Drops = drops;
        this.Equipment = equipment;
    }
}
