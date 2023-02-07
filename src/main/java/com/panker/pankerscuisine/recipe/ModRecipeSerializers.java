package com.panker.pankerscuisine.recipe;

import com.panker.pankerscuisine.Pankers_Cuisine;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Pankers_Cuisine.MOD_ID);

    public static final RegistryObject<RecipeSerializer<BrickOvenRecipe>> BRICK_OVEN_SERIALIZER =
            SERIALIZERS.register("brick_oven_cooking", BrickOvenRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
