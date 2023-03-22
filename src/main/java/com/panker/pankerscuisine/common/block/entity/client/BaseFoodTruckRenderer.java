package com.panker.pankerscuisine.common.block.entity.client;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.block.entity.custom.BaseFoodTruckEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BaseFoodTruckRenderer extends GeoEntityRenderer<BaseFoodTruckEntity> {
    public BaseFoodTruckRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BaseFoodTruckModel());
        this.shadowRadius = 0.8f;
    }

    /*Override this method for non base trucks with the real file*/
    @Override
    public ResourceLocation getTextureLocation(BaseFoodTruckEntity animatable) {
        return new ResourceLocation(Pankers_Cuisine.MOD_ID, "textures/entity/base_food_truck.png");
    }

    @Override
    public RenderType getRenderType(BaseFoodTruckEntity animatable,
                                    ResourceLocation texture, @Nullable MultiBufferSource bufferSource,
                                    float partialTick) {

        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
