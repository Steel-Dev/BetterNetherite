package com.github.steeldev.betternetherite.util.mobs;

import com.github.steeldev.betternetherite.BetterNetherite;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MountInfo {
    public EntityType riding;
    public int chance;
    public List<Material> armorTypes;
    public int armorChance;
    BetterNetherite main = BetterNetherite.getInstance();

    public MountInfo(EntityType riding, int chance) {
        this.riding = riding;
        this.chance = chance;
    }

    public MountInfo(EntityType riding, int chance, List<Material> armorTypes, int armorChance) {
        this.riding = riding;
        this.chance = chance;
        this.armorTypes = armorTypes;
        this.armorChance = armorChance;
    }

    public LivingEntity spawnMount(Location location) {
        World world = location.getWorld();
        LivingEntity entityToRide = (LivingEntity) world.spawnEntity(location, riding);
        if (entityToRide instanceof Tameable) {
            ((Tameable) entityToRide).setTamed(true);
            if (entityToRide instanceof Horse) {
                ((Horse) entityToRide).getInventory().setSaddle(new ItemStack(Material.SADDLE));
                if (armorTypes != null && armorTypes.size() > 0) {
                    Material randArmor = armorTypes.get(main.rand.nextInt(armorTypes.size()));
                    if (main.chanceOf(armorChance))
                        ((Horse) entityToRide).getInventory().setArmor(new ItemStack(randArmor));
                }
            }
        }
        if (entityToRide instanceof Pig)
            ((Pig) entityToRide).setSaddle(true);

        return entityToRide;
    }
}
