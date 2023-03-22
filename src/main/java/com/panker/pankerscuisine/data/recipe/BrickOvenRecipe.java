package com.panker.pankerscuisine.data.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.panker.pankerscuisine.Pankers_Cuisine;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BrickOvenRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public static final int INPUT_SLOTS = 6;

    public BrickOvenRecipe(ResourceLocation id, ItemStack output,
                                    NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    //Farmer's delight implementation from their cooking pot
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        int i = 0;

        List<ItemStack> inputItems = new ArrayList<>();

        for (int j = 0; j < 6; j++) {
            ItemStack itemstack = pContainer.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                inputItems.add(itemstack);
            }
        }

        return i == this.recipeItems.size() && net.minecraftforge.common.util.RecipeMatcher.findMatches(inputItems, this.recipeItems) != null;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess registryAccess) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<BrickOvenRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "brick_oven_cooking";
    }


    public static class Serializer implements RecipeSerializer<BrickOvenRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Pankers_Cuisine.MOD_ID, "brick_oven_cooking");

        @Override
        public BrickOvenRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            /*
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

             */

            final NonNullList<Ingredient> inputItemsIn = readIngredients(GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients"));
            if (inputItemsIn.isEmpty()) {
                throw new JsonParseException("No ingredients for cooking recipe");
            } else if (inputItemsIn.size() > BrickOvenRecipe.INPUT_SLOTS) {
                throw new JsonParseException("Too many ingredients for cooking recipe! The max is " + BrickOvenRecipe.INPUT_SLOTS);
            } else {
                final ItemStack outputIn = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"), true);
                //final float experienceIn = GsonHelper.getAsFloat(pSerializedRecipe, "experience", 0.0F);
                //final int cookTimeIn = GsonHelper.getAsInt(pSerializedRecipe, "cookingtime", 200);
                return new BrickOvenRecipe(pRecipeId, outputIn, inputItemsIn);
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

        @Override
        public @Nullable BrickOvenRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new BrickOvenRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, BrickOvenRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.output.copy(), false);
        }
    }
}