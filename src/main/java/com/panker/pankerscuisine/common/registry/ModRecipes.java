package com.panker.pankerscuisine.common.registry;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.data.recipe.BrickOvenRecipe;
import com.panker.pankerscuisine.data.recipe.DistilleryRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Pankers_Cuisine.MOD_ID);

    public static final RegistryObject<RecipeSerializer<BrickOvenRecipe>> BRICK_OVEN_SERIALIZER =
            SERIALIZERS.register("brick_oven_cooking", () -> BrickOvenRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<DistilleryRecipe>> DISTILLERY_SERIALIZER =
            SERIALIZERS.register("distilling", () -> DistilleryRecipe.Serializer.INSTANCE);


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
