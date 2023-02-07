package com.panker.pankerscuisine.data.recipe;

import com.panker.pankerscuisine.data.recipe.BrickOvenCookingRecipes;
import com.panker.pankerscuisine.data.recipe.CraftingRecipes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Recipes extends RecipeProvider
{
    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        CraftingRecipes.register(consumer);
        //SmeltingRecipes.register(consumer);
        BrickOvenCookingRecipes.register(consumer);
        //CuttingRecipes.register(consumer);
    }
}
