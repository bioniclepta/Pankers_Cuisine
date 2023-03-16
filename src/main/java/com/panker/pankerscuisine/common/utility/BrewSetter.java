package com.panker.pankerscuisine.common.utility;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.registry.CatalystList;
import com.panker.pankerscuisine.common.registry.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BrewSetter {

    private final NonNullList<ItemStack> ingredients;
    private final NonNullList<ItemStack> solids;
    private final List<ItemStack> processed = new ArrayList<>();
    private final ItemStack base;
    private final List<Integer> liquidColors;

    private int food;
    private float saturation;
    private int complexity;
    private int multiplier;
    private int complexity_mult;

    public BrewSetter(ItemStack baseIn, NonNullList<ItemStack> ingredientsIn) {
        this.base = baseIn;
        this.ingredients = ingredientsIn;
        this.solids = NonNullList.create();
        this.liquidColors = new ArrayList<>();
    }

    public ItemStack getResult() {
        List<ItemStack> process = new ArrayList<>(this.ingredients);
        this.processed.clear();
        int maxFood;


        for (ItemStack stack : process) {
            if (!stack.isEmpty()) {
                if (!this.processStack(stack)) {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (this.saturation <= 0 || this.food <= 0) {
            return ItemStack.EMPTY;
        }
        //Calculations to be changed
        /*********************************************************************/
        this.saturation /= this.food;
        int count = 1;

        this.food = (int) Math.ceil((this.food / (double) count) + (double) multiplier);
        int quality = Mth.clamp(this.complexity * (this.complexity_mult / 2), 1, 10);
        this.saturation *= (1.0F * multiplier) + ((quality - 1) * 0.3F);
        /***********************************************************************/

        ItemStack result = new ItemStack(ModItems.CUSTOM_BREW.get());


        BrewNBT.setSize(result, this.getSize());
        BrewNBT.setIngredientsList(result, this.ingredients);
        BrewNBT.setFoodAmount(result, this.food);
        BrewNBT.setSaturation(result, this.saturation);
        BrewNBT.setQuality(result, quality);
        BrewNBT.setBase(result, this.base);
        BrewNBT.setSolids(result, this.solids);
        BrewNBT.setSolidsSize(result, this.solids.size());

        result.setCount(count);
        return result;
    }

    public boolean processStack(ItemStack stack) {
        FoodProperties food = stack.getFoodProperties(null);
        Item stackItem = stack.getItem();

        int foodAmount = 0;
        float saturationAmount = 0;
        boolean valid = true;

        if (CatalystList.containsKey(stackItem)) {
            CatalystProperties properties = CatalystList.getProperties(stackItem);
            this.multiplier += properties.getMultiplier();
            this.complexity_mult += properties.getComplexity();
            // do something with the properties
        } else if (food != null) {
            foodAmount = food.getNutrition();
            saturationAmount = food.getSaturationModifier();
            this.solids.add(stack);
        } else {
            valid = false;
        }

        if (!valid) {
            return false;
        }
        this.food += foodAmount;
        this.saturation += saturationAmount * foodAmount;
        boolean unique = true;

        for (ItemStack existing : this.processed) {
            if (!existing.isEmpty() && ItemStack.matches(existing, stack)) {
                unique = false;
                break;
            }
        }

        if (unique) {
            this.complexity++;
        }
        //Add a complexity modifier from non edible catalysts
        this.processed.add(stack);
        return true;
    }

    public int getSize() {
        return this.ingredients.size();
    }
}
