package com.panker.pankerscuisine.common.block.entity.client;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.block.entity.custom.TacoFoodTruckEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TacoFoodTruckModel extends GeoModel<TacoFoodTruckEntity> {

    @Override
    public ResourceLocation getModelResource(TacoFoodTruckEntity animatable) {
        return new ResourceLocation(Pankers_Cuisine.MOD_ID, "geo/base_food_truck.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TacoFoodTruckEntity animatable) {
        return new ResourceLocation(Pankers_Cuisine.MOD_ID, "textures/entity/taco_food_truck.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TacoFoodTruckEntity animatable) {
        return new ResourceLocation(Pankers_Cuisine.MOD_ID, "animations/base_food_truck.animation.json");
    }
}
