package com.github.steeldev.betternetherite.listeners.world;

import com.github.steeldev.betternetherite.managers.BNMobManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.persistence.PersistentDataType;

public class BNWorldListener implements Listener {

    @EventHandler
    public void chunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof LivingEntity) {
                if (entity.getPersistentDataContainer().has(BNMobManager.customMobKey, PersistentDataType.STRING))
                    BNMobManager.addMobToSpawned((LivingEntity) entity);
            }
        }
    }

    @EventHandler
    public void chunkUnload(ChunkUnloadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof LivingEntity) {
                if (entity.getPersistentDataContainer().has(BNMobManager.customMobKey, PersistentDataType.STRING))
                    BNMobManager.removeMobFromSpawned((LivingEntity) entity);
            }
        }
    }
}
