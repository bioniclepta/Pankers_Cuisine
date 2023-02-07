package com.panker.pankerscuisine.data.recipe;

import com.panker.pankerscuisine.common.registry.ModItems;
import com.panker.pankerscuisine.data.builder.BrickOvenRecipeBuilder;
import com.panker.pankerscuisine.client.recipebook.BrickOvenRecipeBookTab;
import com.panker.pankerscuisine.common.tag.ForgeTags;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class BrickOvenCookingRecipes {
    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_COOKING = 400;      // 20 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void register(Consumer<FinishedRecipe> consumer) {
        cookMeals(consumer);
    }

    private static void cookMeals(Consumer<FinishedRecipe> consumer) {
        BrickOvenRecipeBuilder.brickOvenRecipe(ModItems.PEPPERONIPIZZAWHOLE.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ForgeTags.CHEESES)
                .addIngredient(ForgeTags.DOUGH)
                .addIngredient(ForgeTags.CROPS_TOMATO)
                .unlockedByAnyIngredient(ModItems.CHEESE.get(), ModItems.DOUGH.get(), ModItems.TOMATO.get())
                .setRecipeBookTab(BrickOvenRecipeBookTab.MEALS)
                .build(consumer);
    }
}
