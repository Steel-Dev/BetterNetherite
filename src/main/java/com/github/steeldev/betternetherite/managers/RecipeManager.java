package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.config.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

import java.util.Iterator;

public class RecipeManager {

    final static BetterNetherite main = BetterNetherite.getInstance();

    public static void RegisterRecipes() {
        if (BetterConfig.ENABLE_NETHERITE_CRAFTING &&
                !BetterConfig.IMPROVED_UPGRADING)
            registerNetheriteItems();
        if (BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_ENABLED)
            registerBetterNetheriteScrapSmelting();
    }

    static void registerNetheriteItems() {
        //
        //NETHERITE SWORD
        //
        ItemStack netheriteSword = new ItemStack(Material.NETHERITE_SWORD);
        NamespacedKey netheriteSwordKey = new NamespacedKey(main, "netherite_sword");
        ShapedRecipe netheriteSwordRec = new ShapedRecipe(netheriteSwordKey, netheriteSword);
        netheriteSwordRec.shape(" N ", " N ", " S ");
        netheriteSwordRec.setIngredient('S', Material.STICK);
        netheriteSwordRec.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(netheriteSwordRec);
        //
        //NETHERITE AXE
        //
        ItemStack netheriteAxe = new ItemStack(Material.NETHERITE_AXE);
        NamespacedKey netheriteAxeKey = new NamespacedKey(main, "netherite_axe");
        ShapedRecipe netheriteAxeRec = new ShapedRecipe(netheriteAxeKey, netheriteAxe);
        netheriteAxeRec.shape("NN ", "NS ", " S ");
        netheriteAxeRec.setIngredient('S', Material.STICK);
        netheriteAxeRec.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(netheriteAxeRec);
        //
        //NETHERITE SHOVEL
        //
        ItemStack netheriteShovel = new ItemStack(Material.NETHERITE_SHOVEL);
        NamespacedKey netheriteShovelKey = new NamespacedKey(main, "netherite_shovel");
        ShapedRecipe netheriteShovelRec = new ShapedRecipe(netheriteShovelKey, netheriteShovel);
        netheriteShovelRec.shape(" N ", " S ", " S ");
        netheriteShovelRec.setIngredient('S', Material.STICK);
        netheriteShovelRec.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(netheriteShovelRec);
        //
        //NETHERITE PICKAXE
        //
        ItemStack netheritePickaxe = new ItemStack(Material.NETHERITE_PICKAXE);
        NamespacedKey netheritePickaxeKey = new NamespacedKey(main, "netherite_pickaxe");
        ShapedRecipe netheritePickaxeRec = new ShapedRecipe(netheritePickaxeKey, netheritePickaxe);
        netheritePickaxeRec.shape("NNN", " S ", " S ");
        netheritePickaxeRec.setIngredient('S', Material.STICK);
        netheritePickaxeRec.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(netheritePickaxeRec);
        //
        //NETHERITE SICKLE (HOE)
        //
        ItemStack netheriteHoe = new ItemStack(Material.NETHERITE_HOE);
        NamespacedKey netheriteHoeKey = new NamespacedKey(main, "netherite_sickle");
        ShapedRecipe netheriteHoeRec = new ShapedRecipe(netheriteHoeKey, netheriteHoe);
        netheriteHoeRec.shape("NN ", " S ", " S ");
        netheriteHoeRec.setIngredient('S', Material.STICK);
        netheriteHoeRec.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(netheriteHoeRec);
        //
        //NETHERITE HELMET
        //
        ItemStack netheriteHelmet = new ItemStack(Material.NETHERITE_HELMET);
        NamespacedKey netheriteHelmetKey = new NamespacedKey(main, "netherite_helmet");
        ShapedRecipe netheriteHelmetRec = new ShapedRecipe(netheriteHelmetKey, netheriteHelmet);
        netheriteHelmetRec.shape("NNN", "N N", "   ");
        netheriteHelmetRec.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(netheriteHelmetRec);
        //
        //NETHERITE CHESTPLATE
        //
        ItemStack netheriteChestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
        NamespacedKey netheriteChestplateKey = new NamespacedKey(main, "netherite_chestplate");
        ShapedRecipe netheriteChestplateRec = new ShapedRecipe(netheriteChestplateKey, netheriteChestplate);
        netheriteChestplateRec.shape("N N", "NNN", "NNN");
        netheriteChestplateRec.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(netheriteChestplateRec);
        //
        //NETHERITE LEGGINGS
        //
        ItemStack netheriteLeggings = new ItemStack(Material.NETHERITE_LEGGINGS);
        NamespacedKey netheriteLeggingsKey = new NamespacedKey(main, "netherite_leggings");
        ShapedRecipe netheriteLeggingsRec = new ShapedRecipe(netheriteLeggingsKey, netheriteLeggings);
        netheriteLeggingsRec.shape("NNN", "N N", "N N");
        netheriteLeggingsRec.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(netheriteLeggingsRec);
        //
        //NETHERITE BOOTS
        //
        ItemStack netheriteBoots = new ItemStack(Material.NETHERITE_BOOTS);
        NamespacedKey netheriteBootsKey = new NamespacedKey(main, "netherite_boots");
        ShapedRecipe netheriteBootsRec = new ShapedRecipe(netheriteBootsKey, netheriteBoots);
        netheriteBootsRec.shape("   ", "N N", "N N");
        netheriteBootsRec.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(netheriteBootsRec);
    }

    static void registerBetterNetheriteScrapSmelting() {
        //
        //NETHERITE SCRAP BLASTING
        //
        removeRecipe("minecraft:netherite_scrap");
        removeRecipe("minecraft:netherite_scrap_from_blasting");
        int amount = BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_AMOUNT;
        if (amount > 64) amount = 64;
        if (amount < 1) amount = 1;

        int blastEXP = BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_BLAST_FURNACE_EXP;
        int blastTime = BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_BLAST_FURNACE_TIME;

        int normEXP = BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_FURNACE_EXP;
        int normTime = BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_FURNACE_TIME;

        ItemStack netheriteScrap = new ItemStack(Material.NETHERITE_SCRAP, amount);

        NamespacedKey netheriteScrapBlastKey = new NamespacedKey(main, "netherite_scrap_from_blasting");
        BlastingRecipe netheriteScrapBlastRec = new BlastingRecipe(netheriteScrapBlastKey, netheriteScrap, Material.ANCIENT_DEBRIS, blastEXP, blastTime);
        addRecipe(netheriteScrapBlastRec);

        NamespacedKey netheriteScrapSmeltKey = new NamespacedKey(main, "netherite_scrap");
        FurnaceRecipe netheriteScrapSmeltRec = new FurnaceRecipe(netheriteScrapSmeltKey, netheriteScrap, Material.ANCIENT_DEBRIS, normEXP, normTime);
        addRecipe(netheriteScrapSmeltRec);
    }

    public static void addRecipe(Recipe recipe){
        Bukkit.addRecipe(recipe);
        if(BetterConfig.DEBUG) {
            main.getLogger().info(main.colorize(Lang.RECIPE_ADDED_MSG.replace("KEY", ((Keyed) recipe).getKey().toString())));
        }
    }

    public static void removeRecipe(String key) {
        Iterator<Recipe> it = Bukkit.recipeIterator();
        while (it.hasNext()) {
            Recipe rec = it.next();
            if (rec != null) {
                if (((Keyed) rec).getKey().toString().equals(key)) {
                    if(BetterConfig.DEBUG) {
                        main.getLogger().info(main.colorize(Lang.RECIPE_REMOVED_MSG.replace("KEY", key)));
                    }
                    it.remove();
                }
            }
        }
    }
}
