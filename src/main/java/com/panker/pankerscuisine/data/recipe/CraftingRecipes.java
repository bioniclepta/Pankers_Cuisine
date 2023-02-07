package com.panker.pankerscuisine.data.recipe;

import com.panker.pankerscuisine.common.registry.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class CraftingRecipes {

    public static void register(Consumer<FinishedRecipe> consumer) {
        /*
        recipesVanillaAlternatives(consumer);
        recipesBlocks(consumer);

         */
        recipesTools(consumer);
        /*
        recipesMaterials(consumer);
        recipesFoodstuffs(consumer);
        recipesFoodBlocks(consumer);
        recipesCraftedMeals(consumer);
        SpecialRecipeBuilder.special(ModRecipeSerializers.FOOD_SERVING.get()).save(consumer, "food_serving");

         */
    }


    /**
     * The following recipes should ALWAYS define a custom save location.
     * If not, they fall on the minecraft namespace, overriding vanilla recipes instead of being alternatives.
     */
    /*
    private static void recipesVanillaAlternatives(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(Items.PUMPKIN_SEEDS)
                .requires(ModItems.PUMPKIN_SLICE.get())
                .unlockedBy("has_pumpkin_slice", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PUMPKIN_SLICE.get()))
                .save(consumer, new ResourceLocation(FarmersDelight.MODID, "pumpkin_seeds_from_slice"));
        ShapedRecipeBuilder.shaped(Items.SCAFFOLDING, 6)
                .pattern("b#b")
                .pattern("b b")
                .pattern("b b")
                .define('b', Items.BAMBOO)
                .define('#', ModItems.CANVAS.get())
                .unlockedBy("has_canvas", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS.get()))
                .save(consumer, new ResourceLocation(FarmersDelight.MODID, "scaffolding_from_canvas"));
        ShapedRecipeBuilder.shaped(Items.LEAD)
                .pattern("rr ")
                .pattern("rr ")
                .pattern("  r")
                .define('r', ModItems.ROPE.get())
                .unlockedBy("has_rope", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ROPE.get()))
                .save(consumer, new ResourceLocation(FarmersDelight.MODID, "lead_from_rope"));
    }

    private static void recipesBlocks(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.TATAMI.get(), 2)
                .pattern("cs")
                .pattern("sc")
                .define('c', ModItems.CANVAS.get())
                .define('s', ModItems.STRAW.get())
                .unlockedBy("has_canvas", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS.get()))
                .save(consumer);
    }
*/
    private static void recipesTools(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModItems.PIZZACUTTER.get())
                .pattern("m  ")
                .pattern(" s ")
                .pattern("  s")
                .define('m', Tags.Items.INGOTS_IRON)
                .define('s', Items.STICK)
                .unlockedBy("has_iron_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(consumer);
    }
/*
    private static void recipesMaterials(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(ModItems.STRAW.get(), 9)
                .requires(ModItems.STRAW_BALE.get())
                .unlockedBy("has_straw_bale", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.STRAW_BALE.get()))
                .save(consumer);
    }

    private static void recipesFoodstuffs(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(ModItems.WHEAT_DOUGH.get(), 3)
                .requires(Items.WATER_BUCKET)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy("has_wheat", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHEAT))
                .save(consumer, new ResourceLocation(FarmersDelight.MODID, "wheat_dough_from_water"));
    }

    private static void recipesFoodBlocks(Consumer<FinishedRecipe> consumer) {

    }

    private static void recipesCraftedMeals(Consumer<FinishedRecipe> consumer) {}

 */
}
