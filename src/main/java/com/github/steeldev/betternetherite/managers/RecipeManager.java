package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.BetterConfig;
import com.github.steeldev.betternetherite.util.SmeltType;
import com.github.steeldev.monstrorvm.managers.ItemManager;
import com.github.steeldev.monstrorvm.util.items.recipe.types.CraftType;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

import java.util.*;

public class RecipeManager {

    final static BetterNetherite main = BetterNetherite.getInstance();

    public static void RegisterRecipes() {
        if (BetterConfig.ENABLE_NETHERITE_CRAFTING &&
                !BetterConfig.IMPROVED_UPGRADING)
            registerNetheriteItems();
        if (BetterConfig.IMPROVED_UPGRADING &&
                !BetterConfig.ENABLE_NETHERITE_CRAFTING)
            registerImprovedUpgradingSmithingTableItems();
        if (BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_ENABLED)
            registerBetterNetheriteScrapSmelting();
    }

    static void registerCustomItemRecipes() {
        if (BetterConfig.CUSTOM_MOB_HELLHOUND_ENABLED || BetterConfig.CUSTOM_MOB_ALPHA_HELLHOUND_ENABLED) {
            com.github.steeldev.monstrorvm.managers.RecipeManager.addSmeltingRecipe("furnace_hound_meat",
                    com.github.steeldev.monstrorvm.util.items.recipe.types.SmeltType.FURNACE,
                    new RecipeChoice.ExactChoice(ItemManager.getItem("cooked_hound_meat").getItem(false)),
                    1,
                    new RecipeChoice.ExactChoice(ItemManager.getItem("hound_meat").getItem(false)),
                    2,
                    130);
            com.github.steeldev.monstrorvm.managers.RecipeManager.addSmeltingRecipe("smoker_hound_meat",
                    com.github.steeldev.monstrorvm.util.items.recipe.types.SmeltType.SMOKER,
                    new RecipeChoice.ExactChoice(ItemManager.getItem("cooked_hound_meat").getItem(false)),
                    1,
                    new RecipeChoice.ExactChoice(ItemManager.getItem("hound_meat").getItem(false)),
                    4,
                    100);
        }

        if(BetterConfig.UPGRADE_PACK_ENABLED){
            Map<Character,Material> ingredients = new HashMap<>();
            ingredients.put('N', Material.NETHERITE_INGOT);
            ingredients.put('I', Material.IRON_INGOT);
            ingredients.put('S', Material.STICK);
            ingredients.put('D', Material.DIAMOND);
            com.github.steeldev.monstrorvm.managers.RecipeManager.addCraftingRecipe("upgrade_pack",
                    CraftType.SHAPED,
                    new RecipeChoice.ExactChoice(ItemManager.getItem("upgrade_pack").getItem(false)),
                    1,
                    Arrays.asList("NIN","DSD","NIN"),
                    ingredients);
        }
    }

    static void registerImprovedUpgradingSmithingTableItems() {
        // Wood to Stone
        addSmithingRecipe("wood_to_stone_sword_smithing",
                new ItemStack(Material.STONE_SWORD),
                Material.WOODEN_SWORD,
                new ItemStack(Material.COBBLESTONE));
        addSmithingRecipe("wood_to_stone_axe_smithing",
                new ItemStack(Material.STONE_AXE),
                Material.WOODEN_AXE,
                new ItemStack(Material.COBBLESTONE));
        addSmithingRecipe("wood_to_stone_shovel_smithing",
                new ItemStack(Material.STONE_SHOVEL),
                Material.WOODEN_SHOVEL,
                new ItemStack(Material.COBBLESTONE));
        addSmithingRecipe("wood_to_stone_hoe_smithing",
                new ItemStack(Material.STONE_HOE),
                Material.WOODEN_HOE,
                new ItemStack(Material.COBBLESTONE));
        addSmithingRecipe("wood_to_stone_pickaxe_smithing",
                new ItemStack(Material.STONE_PICKAXE),
                Material.WOODEN_PICKAXE,
                new ItemStack(Material.COBBLESTONE));

        // Stone to Iron
        addSmithingRecipe("stone_to_iron_sword_smithing",
                new ItemStack(Material.IRON_SWORD),
                Material.STONE_SWORD,
                new ItemStack(Material.IRON_INGOT));
        addSmithingRecipe("stone_to_iron_axe_smithing",
                new ItemStack(Material.IRON_AXE),
                Material.STONE_AXE,
                new ItemStack(Material.IRON_INGOT));
        addSmithingRecipe("stone_to_iron_shovel_smithing",
                new ItemStack(Material.IRON_SHOVEL),
                Material.STONE_SHOVEL,
                new ItemStack(Material.IRON_INGOT));
        addSmithingRecipe("stone_to_iron_hoe_smithing",
                new ItemStack(Material.IRON_HOE),
                Material.STONE_HOE,
                new ItemStack(Material.IRON_INGOT));
        addSmithingRecipe("stone_to_iron_pickaxe_smithing",
                new ItemStack(Material.IRON_PICKAXE),
                Material.STONE_PICKAXE,
                new ItemStack(Material.IRON_INGOT));

        // Iron to Gold
        addSmithingRecipe("iron_to_gold_sword_smithing",
                new ItemStack(Material.GOLDEN_SWORD),
                Material.IRON_SWORD,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_axe_smithing",
                new ItemStack(Material.GOLDEN_AXE),
                Material.IRON_AXE,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_shovel_smithing",
                new ItemStack(Material.GOLDEN_SHOVEL),
                Material.IRON_SHOVEL,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_hoe_smithing",
                new ItemStack(Material.GOLDEN_HOE),
                Material.IRON_HOE,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_pickaxe_smithing",
                new ItemStack(Material.GOLDEN_PICKAXE),
                Material.IRON_PICKAXE,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_helmet_smithing",
                new ItemStack(Material.GOLDEN_HELMET),
                Material.IRON_HELMET,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_chestplate_smithing",
                new ItemStack(Material.GOLDEN_CHESTPLATE),
                Material.IRON_CHESTPLATE,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_leggings_smithing",
                new ItemStack(Material.GOLDEN_LEGGINGS),
                Material.IRON_LEGGINGS,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_boots_smithing",
                new ItemStack(Material.GOLDEN_BOOTS),
                Material.IRON_BOOTS,
                new ItemStack(Material.GOLD_INGOT));

        // Iron to Diamond
        addSmithingRecipe("iron_to_diamond_sword_smithing",
                new ItemStack(Material.DIAMOND_SWORD),
                Material.IRON_SWORD,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_axe_smithing",
                new ItemStack(Material.DIAMOND_AXE),
                Material.IRON_AXE,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_shovel_smithing",
                new ItemStack(Material.DIAMOND_SHOVEL),
                Material.IRON_SHOVEL,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_hoe_smithing",
                new ItemStack(Material.DIAMOND_HOE),
                Material.IRON_HOE,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_pickaxe_smithing",
                new ItemStack(Material.DIAMOND_PICKAXE),
                Material.IRON_PICKAXE,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_helmet_smithing",
                new ItemStack(Material.DIAMOND_HELMET),
                Material.IRON_HELMET,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_chestplate_smithing",
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                Material.IRON_CHESTPLATE,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_leggings_smithing",
                new ItemStack(Material.DIAMOND_LEGGINGS),
                Material.IRON_LEGGINGS,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_boots_smithing",
                new ItemStack(Material.DIAMOND_BOOTS),
                Material.IRON_BOOTS,
                new ItemStack(Material.DIAMOND));
    }

    static void registerNetheriteItems() {
        //
        //NETHERITE SWORD
        //
        addCraftingRecipe("netherite_sword",
                true,
                Material.NETHERITE_SWORD,
                1,
                Arrays.asList(" N ", " N ", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        //
        //NETHERITE AXE
        //
        addCraftingRecipe("netherite_axe",
                true,
                Material.NETHERITE_AXE,
                1,
                Arrays.asList("NN ", "NS ", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        addCraftingRecipe("netherite_axe_flip",
                true,
                Material.NETHERITE_AXE,
                1,
                Arrays.asList(" NN", " SN", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        //
        //NETHERITE SHOVEL
        //
        addCraftingRecipe("netherite_shovel",
                true,
                Material.NETHERITE_SHOVEL,
                1,
                Arrays.asList(" N ", " S ", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        //
        //NETHERITE PICKAXE
        //
        addCraftingRecipe("netherite_pickaxe",
                true,
                Material.NETHERITE_PICKAXE,
                1,
                Arrays.asList("NNN", " S ", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        //
        //NETHERITE HOE
        //
        addCraftingRecipe("netherite_hoe",
                true,
                Material.NETHERITE_HOE,
                1,
                Arrays.asList("NN ", " S ", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        //
        //NETHERITE HELMET
        //
        addCraftingRecipe("netherite_helmet",
                true,
                Material.NETHERITE_HELMET,
                1,
                Arrays.asList("NNN", "N N", "   "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                }});
        //
        //NETHERITE CHESTPLATE
        //
        addCraftingRecipe("netherite_chestplate",
                true,
                Material.NETHERITE_CHESTPLATE,
                1,
                Arrays.asList("N N", "NNN", "NNN"),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                }});
        //
        //NETHERITE LEGGINGS
        //
        addCraftingRecipe("netherite_leggings",
                true,
                Material.NETHERITE_LEGGINGS,
                1,
                Arrays.asList("NNN", "N N", "N N"),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                }});
        //
        //NETHERITE BOOTS
        //
        addCraftingRecipe("netherite_boots",
                true,
                Material.NETHERITE_BOOTS,
                1,
                Arrays.asList("   ", "N N", "N N"),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                }});
    }

    static void registerBetterNetheriteScrapSmelting() {
        removeRecipe("minecraft:netherite_scrap");
        removeRecipe("minecraft:netherite_scrap_from_blasting");
        addSmeltingRecipe("netherite_scrap",
                SmeltType.FURNACE,
                Material.NETHERITE_SCRAP,
                BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_AMOUNT,
                Material.ANCIENT_DEBRIS,
                BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_FURNACE_EXP,
                BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_FURNACE_TIME);
        addSmeltingRecipe("netherite_scrap_from_blasting",
                SmeltType.BLASTING,
                Material.NETHERITE_SCRAP,
                BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_AMOUNT,
                Material.ANCIENT_DEBRIS,
                BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_BLAST_FURNACE_EXP,
                BetterConfig.ANCIENT_DEBRIS_BETTER_SMELTING_BLAST_FURNACE_TIME);
    }

    static void addSmithingRecipe(String key, ItemStack result, Material baseMat, ItemStack itemNeeded) {
        NamespacedKey smithingRecKey = new NamespacedKey(main, key);
        SmithingRecipe smithingRec = new SmithingRecipe(smithingRecKey, result, new RecipeChoice.MaterialChoice(baseMat), new RecipeChoice.ExactChoice(itemNeeded));
        addRecipe(smithingRec);
    }

    static void addSmeltingRecipe(String key, SmeltType type, Material result, int resultAmount, Material smelted, int EXP, int time) {
        NamespacedKey smeltingRecKey = new NamespacedKey(main, key);
        int am = resultAmount;
        if (am > 64) am = 64;
        if (am < 1) am = 1;
        ItemStack resultItem = new ItemStack(result, am);

        Recipe recToAdd;

        switch (type) {
            case FURNACE:
                recToAdd = new FurnaceRecipe(smeltingRecKey, resultItem, smelted, EXP, time);
                break;
            case BLASTING:
                recToAdd = new BlastingRecipe(smeltingRecKey, resultItem, smelted, EXP, time);
                break;
            case SMOKER:
                recToAdd = new SmokingRecipe(smeltingRecKey, resultItem, smelted, EXP, time);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        addRecipe(recToAdd);
    }

    static void addSmeltingRecipe(String key, SmeltType type, RecipeChoice result, int resultAmount, RecipeChoice smelted, int EXP, int time) {
        NamespacedKey smeltingRecKey = new NamespacedKey(main, key);
        int am = resultAmount;
        if (am > 64) am = 64;
        if (am < 1) am = 1;
        ItemStack resultItem = result.getItemStack();
        resultItem.setAmount(am);

        Recipe recToAdd;

        switch (type) {
            case FURNACE:
                recToAdd = new FurnaceRecipe(smeltingRecKey, resultItem, smelted, EXP, time);
                break;
            case BLASTING:
                recToAdd = new BlastingRecipe(smeltingRecKey, resultItem, smelted, EXP, time);
                break;
            case SMOKER:
                recToAdd = new SmokingRecipe(smeltingRecKey, resultItem, smelted, EXP, time);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        addRecipe(recToAdd);
    }

    static void addCraftingRecipe(String key, boolean shaped, Material result, int resultAmount, List<String> rows, Map<Character, Material> ingredients) {
        NamespacedKey craftingRecKey = new NamespacedKey(main, key);
        int am = resultAmount;
        if (am > 64) am = 64;
        if (am < 1) am = 1;
        ItemStack resultItem = new ItemStack(result, am);

        Recipe recToAdd;

        if (shaped) {
            recToAdd = new ShapedRecipe(craftingRecKey, resultItem);
            ((ShapedRecipe) recToAdd).shape(rows.get(0), rows.get(1), rows.get(2));
            for (Character ingKey : ingredients.keySet()) {
                ((ShapedRecipe) recToAdd).setIngredient(ingKey, ingredients.get(ingKey));
            }
        } else {
            recToAdd = new ShapelessRecipe(craftingRecKey, resultItem);
            for (Character ingKey : ingredients.keySet()) {
                ((ShapelessRecipe) recToAdd).addIngredient(ingredients.get(ingKey));
            }
        }

        addRecipe(recToAdd);
    }

    public static void addRecipe(Recipe recipe) {
        Bukkit.addRecipe(recipe);
        if (BetterConfig.DEBUG)
            main.getLogger().info(String.format("&aRecipe &e%s&a has been &2added.", ((Keyed) recipe).getKey()));
    }

    public static void removeRecipe(String key) {
        Iterator<Recipe> it = Bukkit.recipeIterator();
        while (it.hasNext()) {
            Recipe rec = it.next();
            if (rec != null) {
                if (((Keyed) rec).getKey().toString().equals(key)) {
                    if (BetterConfig.DEBUG)
                        main.getLogger().info(String.format("&aRecipe &e%s&a has been &cremoved.", key));
                    it.remove();
                }
            }
        }
    }
}
