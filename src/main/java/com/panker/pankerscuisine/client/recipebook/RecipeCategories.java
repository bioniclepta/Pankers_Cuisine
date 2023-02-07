package com.panker.pankerscuisine.client.recipebook;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.registry.ModItems;
import com.panker.pankerscuisine.data.recipe.BrickOvenRecipe;
import com.panker.pankerscuisine.common.registry.ModRecipeTypes;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;

import java.util.function.Supplier;

public class RecipeCategories {

    public static final Supplier<RecipeBookCategories> COOKING_SEARCH = Suppliers.memoize(() -> RecipeBookCategories.create("COOKING_SEARCH", new ItemStack(Items.COMPASS)));
    public static final Supplier<RecipeBookCategories> COOKING_MEALS = Suppliers.memoize(() -> RecipeBookCategories.create("COOKING_MEALS", new ItemStack(ModItems.PEPPERONIPIZZASLICE.get())));
    public static final Supplier<RecipeBookCategories> COOKING_MISC = Suppliers.memoize(() -> RecipeBookCategories.create("COOKING_MISC", new ItemStack(Items.BARREL), new ItemStack(Items.BAKED_POTATO)));

    public static void init(RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(Pankers_Cuisine.RECIPE_TYPE_BRICK_OVEN_COOKING, ImmutableList.of(COOKING_SEARCH.get(), COOKING_MEALS.get(), COOKING_MISC.get()));
        event.registerAggregateCategory(COOKING_SEARCH.get(), ImmutableList.of(COOKING_MEALS.get(), COOKING_MISC.get()));
        event.registerRecipeCategoryFinder(ModRecipeTypes.BRICK_OVEN_COOKING.get(), recipe ->
        {
            if (recipe instanceof BrickOvenRecipe cookingRecipe) {
                BrickOvenRecipeBookTab tab = cookingRecipe.getRecipeBookTab();
                if (tab != null) {
                    return switch (tab) {
                        case MEALS -> COOKING_MEALS.get();
                        default -> COOKING_MISC.get();
                    };
                }
            }


            return COOKING_MISC.get();
        });
    }
}
