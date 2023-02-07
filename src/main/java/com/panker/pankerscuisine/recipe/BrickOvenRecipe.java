package com.panker.pankerscuisine.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.item.ModItems;
import com.panker.pankerscuisine.recipebook.BrickOvenRecipeBookTab;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

//Credit to farmers delight for recipe implementations

@SuppressWarnings("ClassCanBeRecord")
public class BrickOvenRecipe implements Recipe<RecipeWrapper> {

    public static final int INPUT_SLOTS = 6;

    private final ResourceLocation id;
    private final String group;
    private final BrickOvenRecipeBookTab tab;
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ItemStack container;
    private final float experience;
    private final int cookTime;

    public BrickOvenRecipe(ResourceLocation id, String group, BrickOvenRecipeBookTab tab, NonNullList<Ingredient> inputItems,
                           ItemStack output, ItemStack container, float experience, int cookTime) {
        this.id = id;
        this.group = group;
        this.tab = tab;
        this.inputItems = inputItems;
        this.output = output;
        this.experience = experience;
        this.cookTime = cookTime;

        if (!container.isEmpty()) {
            this.container = container;
        } else if (!output.getCraftingRemainingItem().isEmpty()) {
            this.container = output.getCraftingRemainingItem();
        } else {
            this.container = ItemStack.EMPTY;
        }
    }

    public float getExperience() {
        return this.experience;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public ItemStack getResultItem() {
        return output;
    }

    public ItemStack getOutputContainer() {
        return this.container;
    }

    @Nullable
    public BrickOvenRecipeBookTab getRecipeBookTab() {
        return this.tab;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for (int j = 0; j < INPUT_SLOTS; ++j) {
            ItemStack itemstack = inv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                inputs.add(itemstack);
            }
        }
        return i == this.inputItems.size() && net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs, this.inputItems) != null;
    }

    @Override
    public ItemStack assemble(RecipeWrapper pContainer) {
        return this.output.copy();
    }

    public int getCookTime() {
        return this.cookTime;
    }



    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= this.inputItems.size();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.BRICK_OVEN_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.BRICK_OVEN_COOKING.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModItems.BRICK_OVEN.get());
    }


    public static class Serializer implements RecipeSerializer<BrickOvenRecipe> {
        public Serializer() {
        }

        @Override
        public BrickOvenRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String groupIn = GsonHelper.getAsString(json, "group", "");
            final NonNullList<Ingredient> inputItemsIn = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (inputItemsIn.isEmpty()) {
                throw new JsonParseException("No ingredients for cooking recipe");
            } else if (inputItemsIn.size() > BrickOvenRecipe.INPUT_SLOTS) {
                throw new JsonParseException("Too many ingredients for cooking recipe! The max is " + BrickOvenRecipe.INPUT_SLOTS);
            } else {
                final String tabKeyIn = GsonHelper.getAsString(json, "recipe_book_tab", null);
                final BrickOvenRecipeBookTab tabIn = BrickOvenRecipeBookTab.findByName(tabKeyIn);
                if (tabKeyIn != null && tabIn == null) {
                    Pankers_Cuisine.LOGGER.warn("Optional field 'recipe_book_tab' does not match any valid tab. If defined, must be one of the following: " + EnumSet.allOf(BrickOvenRecipeBookTab.class));
                }
                final ItemStack outputIn = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);
                ItemStack container = GsonHelper.isValidNode(json, "container") ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "container"), true) : ItemStack.EMPTY;
                final float experienceIn = GsonHelper.getAsFloat(json, "experience", 0.0F);
                final int cookTimeIn = GsonHelper.getAsInt(json, "cookingtime", 200);
                return new BrickOvenRecipe(recipeId, groupIn, tabIn, inputItemsIn, outputIn, container, experienceIn, cookTimeIn);
            }
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for (int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        @Nullable
        @Override
        public BrickOvenRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf();
            BrickOvenRecipeBookTab tabIn = BrickOvenRecipeBookTab.findByName(buffer.readUtf());
            int i = buffer.readVarInt();
            NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(i, Ingredient.EMPTY);

            for (int j = 0; j < inputItemsIn.size(); ++j) {
                inputItemsIn.set(j, Ingredient.fromNetwork(buffer));
            }

            ItemStack outputIn = buffer.readItem();
            ItemStack container = buffer.readItem();
            float experienceIn = buffer.readFloat();
            int cookTimeIn = buffer.readVarInt();
            return new BrickOvenRecipe(recipeId, groupIn, tabIn, inputItemsIn, outputIn, container, experienceIn, cookTimeIn);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BrickOvenRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeUtf(recipe.tab != null ? recipe.tab.toString() : "");
            buffer.writeVarInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.inputItems) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.output);
            buffer.writeItem(recipe.container);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.cookTime);
        }
    }
}
