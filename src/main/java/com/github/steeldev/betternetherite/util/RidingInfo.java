package com.github.steeldev.betternetherite.util;

import com.github.steeldev.betternetherite.BetterNetherite;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RidingInfo {
    BetterNetherite main = BetterNetherite.getInstance();

    public EntityType Riding;
    public int Chance;
    public List<Material> ArmorTypes;
    public int ArmorChance;

    public RidingInfo(EntityType riding, int chance){
        this.Riding = riding;
        this.Chance = chance;
    }

    public RidingInfo(EntityType riding, int chance, List<Material> armorTypes, int armorChance){
        this.Riding = riding;
        this.Chance = chance;
        this.ArmorTypes = armorTypes;
        this.ArmorChance = armorChance;
    }

    public LivingEntity spawnMount(Location location){
        World world = location.getWorld();
        LivingEntity entityToRide = (LivingEntity) world.spawnEntity(location, Riding);
        if(entityToRide instanceof Tameable){
            ((Tameable)entityToRide).setTamed(true);
            if(entityToRide instanceof Horse){
                ((Horse)entityToRide).getInventory().setSaddle(new ItemStack(Material.SADDLE));
                if(ArmorTypes != null && ArmorTypes.size() > 0){
                    Material randArmor = ArmorTypes.get(main.rand.nextInt(ArmorTypes.size()));
                    if(main.chanceOf(ArmorChance))
                        ((Horse)entityToRide).getInventory().setArmor(new ItemStack(randArmor));
                }
            }
        }
        if(entityToRide instanceof Pig)
            ((Pig)entityToRide).setSaddle(true);

        return entityToRide;
    }
}
