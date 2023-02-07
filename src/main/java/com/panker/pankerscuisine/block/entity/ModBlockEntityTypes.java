package com.panker.pankerscuisine.block.entity;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Pankers_Cuisine.MOD_ID);

    public static final RegistryObject<BlockEntityType<BrickOvenBlockEntity>> BRICK_OVEN_TILE_ENTITY = TILES.register("brick_oven_tile_entity",
            () -> BlockEntityType.Builder.of(BrickOvenBlockEntity::new, ModBlocks.BRICK_OVEN_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILES.register(eventBus);
    }
}
