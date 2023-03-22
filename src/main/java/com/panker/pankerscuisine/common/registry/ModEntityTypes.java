package com.panker.pankerscuisine.common.registry;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.block.entity.custom.BaseFoodTruckEntity;
import com.panker.pankerscuisine.common.block.entity.custom.TacoFoodTruckEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Pankers_Cuisine.MOD_ID);


    public static final RegistryObject<EntityType<TacoFoodTruckEntity>> TACO_FOOD_TRUCK =
            ENTITY_TYPES.register("taco_food_truck",
                    () -> EntityType.Builder.of(TacoFoodTruckEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 2.5f)
                    .build( new ResourceLocation(Pankers_Cuisine.MOD_ID, "taco_food_truck").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
