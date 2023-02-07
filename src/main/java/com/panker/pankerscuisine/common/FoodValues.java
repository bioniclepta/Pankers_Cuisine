package com.panker.pankerscuisine.common;


import net.minecraft.world.food.FoodProperties;

//I really like farmers delights implementation of this level of abstraction for just the food items
public class FoodValues {

    public static final int BRIEF_DURATION = 600;    // 30 seconds
    public static final int SHORT_DURATION = 1200;    // 1 minute
    public static final int MEDIUM_DURATION = 3600;    // 3 minutes
    public static final int LONG_DURATION = 6000;    // 5 minutes

    // Raw Crops
    public static final FoodProperties CABBAGE = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.4f).build();
    public static final FoodProperties TOMATO = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.3f).build();
    public static final FoodProperties ONION = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.4f).build();

}
