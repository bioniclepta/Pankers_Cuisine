package com.panker.pankerscuisine.block.entity;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Pankers_Cuisine.MOD_ID);

    public static final RegistryObject<BlockEntityType<BrickOvenBlockEntity>> BRICK_OVEN =
            BLOCK_ENTITIES.register("brick_oven", () ->
                    BlockEntityType.Builder.of(BrickOvenBlockEntity::new,
                            ModBlocks.BRICK_OVEN.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
