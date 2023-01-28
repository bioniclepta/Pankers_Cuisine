package com.panker.pankerscuisine.block;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.block.custom.LettuceCropBlock;
import com.panker.pankerscuisine.block.custom.PizzaWholeBlock;
import com.panker.pankerscuisine.block.custom.TomatoCropBlock;
import com.panker.pankerscuisine.item.ModCreativeModeTab;
import com.panker.pankerscuisine.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Pankers_Cuisine.MOD_ID);

    public static final RegistryObject<Block> PEPPERONIPIZZAWHOLE_BLOCK = BLOCKS.register("pepperoni_pizza_whole_block",
            () -> new PizzaWholeBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)
                    .requiresCorrectToolForDrops().noOcclusion(), ModItems.PEPPERONIPIZZASLICE, ModItems.PEPPERONIPIZZAWHOLE));
    public static final RegistryObject<Block> CHEESEPIZZAWHOLE_BLOCK = registerBlock("cheese_pizza_whole_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.CAKE)
                    .requiresCorrectToolForDrops().noOcclusion()), ModCreativeModeTab.PANKERS_CUISINE_TAB);
    public static final RegistryObject<Block> MARGHERITAPIZZAWHOLE_BLOCK = registerBlock("margherita_pizza_whole_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.CAKE)
                    .requiresCorrectToolForDrops().noOcclusion()), ModCreativeModeTab.PANKERS_CUISINE_TAB);
    public static final RegistryObject<Block> TOMATO_CROP = BLOCKS.register("tomato_crop",
            () -> new TomatoCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> LETTUCE_CROP = BLOCKS.register("lettuce_crop",
            () -> new LettuceCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));




    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);

        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
