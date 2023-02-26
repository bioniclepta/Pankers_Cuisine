package com.panker.pankerscuisine.common.utility;

import com.mojang.datafixers.util.Pair;
import com.panker.pankerscuisine.Pankers_Cuisine;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public interface IBrewIngredient {
    ResourceLocation INGREDIENT_ID = new ResourceLocation(Pankers_Cuisine.MOD_ID,
            "ingredient");

    default int getFoodAmount() {
        return 0;
    }

    default float getSaturation() {
        return 0.0F;
    }

    default List<Pair<MobEffectInstance, Float>> getEffects() {
        return Collections.emptyList();
    }

    default void onEaten(Player player) {

    }

    default boolean isLiquid() {
        return false;
    }

    @Nullable
    default Integer getLiquidColor() {
        return null;
    }

    default boolean isValid() {
        return true;
    }
}
