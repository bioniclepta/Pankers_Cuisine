package com.panker.pankerscuisine.block;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.item.ModCreativeModeTab;
import com.panker.pankerscuisine.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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

    public static final RegistryObject<Block> PEPPERONIPIZZAWHOLE_BLOCK = registerBlock("pepperoni_pizza_whole_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.CAKE)
                    .strength(1).requiresCorrectToolForDrops()), ModCreativeModeTab.PANKERS_CUISINE_TAB);
    public static final RegistryObject<Block> CHEESEPIZZAWHOLE_BLOCK = registerBlock("cheese_pizza_whole_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.CAKE)
                    .strength(1).requiresCorrectToolForDrops()), ModCreativeModeTab.PANKERS_CUISINE_TAB);
    public static final RegistryObject<Block> MARGARITAPIZZAWHOLE_BLOCK = registerBlock("margarita_pizza_whole_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.CAKE)
                    .strength(1).requiresCorrectToolForDrops()), ModCreativeModeTab.PANKERS_CUISINE_TAB);

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
