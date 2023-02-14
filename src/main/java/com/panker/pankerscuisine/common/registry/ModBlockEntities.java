package com.panker.pankerscuisine.common.registry;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.block.entity.BrickOvenBlockEntity;
import com.panker.pankerscuisine.common.block.entity.DistilleryBlockEntity;
import com.panker.pankerscuisine.common.registry.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Pankers_Cuisine.MOD_ID);

    public static final RegistryObject<BlockEntityType<BrickOvenBlockEntity>> BRICK_OVEN_TILE_ENTITY = TILES.register("brick_oven_tile_entity",
            () -> BlockEntityType.Builder.of(BrickOvenBlockEntity::new, ModBlocks.BRICK_OVEN_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<DistilleryBlockEntity>> DISTILLERY_TILE_ENTITY = TILES.register("distillery_tile_entity",
            () -> BlockEntityType.Builder.of(DistilleryBlockEntity::new, ModBlocks.DISTILLERY_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILES.register(eventBus);
    }
}
