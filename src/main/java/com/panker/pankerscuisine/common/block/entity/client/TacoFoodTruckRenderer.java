package com.panker.pankerscuisine.common.block.entity.client;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.block.entity.custom.TacoFoodTruckEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TacoFoodTruckRenderer extends GeoEntityRenderer<TacoFoodTruckEntity> {

    public TacoFoodTruckRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TacoFoodTruckModel());
        this.shadowRadius = 0.8f;
    }

    @Override
    public ResourceLocation getTextureLocation(TacoFoodTruckEntity animatable) {
        return new ResourceLocation(Pankers_Cuisine.MOD_ID, "textures/entity/taco_food_truck.png");
    }

    @Override
    public RenderType getRenderType(TacoFoodTruckEntity animatable,
                                    ResourceLocation texture, @Nullable MultiBufferSource bufferSource,
                                    float partialTick) {

        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
