package com.panker.pankerscuisine.client.utility;

import com.panker.pankerscuisine.Pankers_Cuisine;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class PankersBrewSprites {
    public static Set<ResourceLocation> get() {
        Set<ResourceLocation> result = new HashSet<>();
        result.add(
                new ResourceLocation(Pankers_Cuisine.MOD_ID, "item/food_bowl/brew_contents"));
        return result;
    }
}
