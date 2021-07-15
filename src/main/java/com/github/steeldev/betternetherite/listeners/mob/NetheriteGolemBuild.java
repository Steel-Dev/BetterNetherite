package com.github.steeldev.betternetherite.listeners.mob;

import com.github.steeldev.monstrorvm.api.mobs.MVMob;
import com.github.steeldev.monstrorvm.api.mobs.MobManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import static com.github.steeldev.betternetherite.util.Util.main;

public class NetheriteGolemBuild implements Listener {
    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        if (!main.config.CUSTOM_MOB_NETHERITE_GOLEM_ENABLED) return;
        Block block = event.getBlock();
        if (!block.getType().equals(Material.CARVED_PUMPKIN)) return;
        boolean pass = false;

        if (block.getRelative(0, -1, 0).getType().equals(Material.NETHERITE_BLOCK)) {
            if (block.getRelative(0, -2, 0).getType().equals(Material.NETHERITE_BLOCK)) {
                if (event.getPlayer().getFacing().equals(BlockFace.EAST) ||
                        event.getPlayer().getFacing().equals(BlockFace.WEST)) {
                    if (block.getRelative(0, -1, 1).getType().equals(Material.NETHERITE_BLOCK))
                        if (block.getRelative(0, -1, -1).getType().equals(Material.NETHERITE_BLOCK))
                            pass = true;
                } else if (event.getPlayer().getFacing().equals(BlockFace.SOUTH) ||
                        event.getPlayer().getFacing().equals(BlockFace.NORTH)) {
                    if (block.getRelative(1, -1, 0).getType().equals(Material.NETHERITE_BLOCK))
                        if (block.getRelative(-1, -1, 0).getType().equals(Material.NETHERITE_BLOCK))
                            pass = true;
                }
            }
        }

        if (pass) {
            block.setType(Material.AIR);
            block.getRelative(0, -1, 0).setType(Material.AIR);
            block.getRelative(0, -2, 0).setType(Material.AIR);

            if (event.getPlayer().getFacing().equals(BlockFace.EAST) ||
                    event.getPlayer().getFacing().equals(BlockFace.WEST)) {
                block.getRelative(0, -1, 1).setType(Material.AIR);
                block.getRelative(0, -1, -1).setType(Material.AIR);
            } else if (event.getPlayer().getFacing().equals(BlockFace.SOUTH) ||
                    event.getPlayer().getFacing().equals(BlockFace.NORTH)) {
                block.getRelative(1, -1, 0).setType(Material.AIR);
                block.getRelative(-1, -1, 0).setType(Material.AIR);
            }

            MVMob golem = MobManager.getMob("netherite_golem");
            golem.spawnMob(block.getRelative(0, -2, 0).getLocation().add(0.5, 0.5, 0.5));
        }
    }
}
