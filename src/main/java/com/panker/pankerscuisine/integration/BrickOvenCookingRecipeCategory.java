package com.panker.pankerscuisine.integration;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.registry.ModBlocks;
import com.panker.pankerscuisine.data.recipe.BrickOvenRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class BrickOvenCookingRecipeCategory implements IRecipeCategory<BrickOvenRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(Pankers_Cuisine.MOD_ID, "brick_oven_cooking");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(Pankers_Cuisine.MOD_ID, "textures/gui/brick_oven_menu_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public BrickOvenCookingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.BRICK_OVEN_BLOCK.get()));
    }


    @Override
    public RecipeType<BrickOvenRecipe> getRecipeType() {
        return JEIPankersCuisinePlugin.COOKING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Brick Oven");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BrickOvenRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 86, 15).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 86, 60).addItemStack(recipe.getResultItem());
    }
}
