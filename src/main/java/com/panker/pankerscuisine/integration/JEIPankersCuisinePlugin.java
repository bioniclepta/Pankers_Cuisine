package com.panker.pankerscuisine.integration;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.registry.ModRecipes;
import com.panker.pankerscuisine.data.recipe.BrickOvenRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIPankersCuisinePlugin implements IModPlugin {

    public static RecipeType<BrickOvenRecipe> COOKING_TYPE =
            new RecipeType<>(BrickOvenCookingRecipeCategory.UID, BrickOvenRecipe.class);
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Pankers_Cuisine.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                BrickOvenCookingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<BrickOvenRecipe> recipesBrickOven = rm.getAllRecipesFor(BrickOvenRecipe.Type.INSTANCE);
        registration.addRecipes(COOKING_TYPE, recipesBrickOven);
    }
}

