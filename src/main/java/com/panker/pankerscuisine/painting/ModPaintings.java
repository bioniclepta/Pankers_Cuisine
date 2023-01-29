package com.panker.pankerscuisine.painting;

import com.panker.pankerscuisine.Pankers_Cuisine;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS =
            DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Pankers_Cuisine.MOD_ID);

    public static final RegistryObject<PaintingVariant> ENZO = PAINTING_VARIANTS.register("enzo",
            () -> new PaintingVariant(80, 80));

    public static final RegistryObject<PaintingVariant> CASUS = PAINTING_VARIANTS.register("casus",
            () -> new PaintingVariant(80, 80));

    public static final RegistryObject<PaintingVariant> DRIPPIN = PAINTING_VARIANTS.register("drippin",
            () -> new PaintingVariant(80, 80));

    public static void register(IEventBus eventBus) {
        PAINTING_VARIANTS.register(eventBus);
    }
}