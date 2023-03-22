package com.panker.pankerscuisine.common.block.entity.client;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.block.entity.custom.BaseFoodTruckEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class BaseFoodTruckModel extends GeoModel<BaseFoodTruckEntity> {

    @Override
    public ResourceLocation getModelResource(BaseFoodTruckEntity animatable) {
        return new ResourceLocation(Pankers_Cuisine.MOD_ID, "geo/base_food_truck.geo.json");
    }

    /*Override this method for non base trucks with the real file*/
    @Override
    public ResourceLocation getTextureResource(BaseFoodTruckEntity animatable) {
        //return new ResourceLocation(Pankers_Cuisine.MOD_ID, "textures/entity/base_food_truck.png");
        return null;
    }

    @Override
    public ResourceLocation getAnimationResource(BaseFoodTruckEntity animatable) {
        return new ResourceLocation(Pankers_Cuisine.MOD_ID, "animations/base_food_truck.animation.json");
    }
}
