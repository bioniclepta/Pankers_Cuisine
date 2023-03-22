package com.panker.pankerscuisine.common.registry;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.block.custom.*;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Pankers_Cuisine.MOD_ID);

    // Pizza Blocks
    public static final RegistryObject<Block> PEPPERONIPIZZAWHOLE_BLOCK = BLOCKS.register("pepperoni_pizza_whole_block",
            () -> new PizzaWholeBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)
                    .requiresCorrectToolForDrops().noOcclusion(), ModItems.PEPPERONIPIZZASLICE, ModItems.PEPPERONIPIZZAWHOLE));
    public static final RegistryObject<Block> CHEESEPIZZAWHOLE_BLOCK = BLOCKS.register("cheese_pizza_whole_block",
            () -> new PizzaWholeBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)
                    .requiresCorrectToolForDrops().noOcclusion(), ModItems.CHEESEPIZZASLICE, ModItems.CHEESEPIZZAWHOLE));
    public static final RegistryObject<Block> MARGHERITAPIZZAWHOLE_BLOCK = BLOCKS.register("margherita_pizza_whole_block",
            () -> new PizzaWholeBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)
                    .requiresCorrectToolForDrops().noOcclusion(), ModItems.MARGHERITAPIZZASLICE, ModItems.MARGHERITAPIZZAWHOLE));

    public static final RegistryObject<Block> BRICK_OVEN_BLOCK = BLOCKS.register("brick_oven_block",
            () -> new BrickOvenBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f).noOcclusion()));
    public static final RegistryObject<Block> DISTILLERY_BLOCK = BLOCKS.register("distillery_block",
            () -> new DistilleryBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f).noOcclusion()));
    public static final RegistryObject<Block> FERMENTER_BLOCK = BLOCKS.register("fermenter_block",
            () -> new FermenterBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(6f).noOcclusion()));

    // Crop Blocks
    public static final RegistryObject<Block> TOMATO_CROP = BLOCKS.register("tomato_crop",
            () -> new TomatoCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> LETTUCE_CROP = BLOCKS.register("lettuce_crop",
            () -> new LettuceCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    //Ores
    public static final RegistryObject<Block> DEEPSLATE_PANKERITE = registerBlock("deepslate_pankerite",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.METAL).strength(6f, 120)
                    .requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE),
                    UniformInt.of(6, 14)));
    public static final RegistryObject<Block> DELICIERITE_ORE = registerBlock("delicierite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.METAL).strength(6f, 120)
                    .requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE),
                    UniformInt.of(6, 14)));
    public static final RegistryObject<Block> THICCIUM_ORE = registerBlock("thiccium_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.METAL).strength(6f, 120)
                    .requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE),
                    UniformInt.of(6, 14)));
    public static final RegistryObject<Block> DEEPSLATE_MILKIUM = registerBlock("deepslate_milkium",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.METAL).strength(6f, 120)
                    .requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE),
                    UniformInt.of(6, 14)));
    public static final RegistryObject<Block> NEHTER_KELVINITE_ORE = registerBlock("nether_kelvinite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.METAL).strength(6f, 120)
                    .requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE),
                    UniformInt.of(6, 14)));
    public static final RegistryObject<Block> END_TOADERITE_ORE = registerBlock("end_toaderite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.METAL).strength(6f, 120)
                    .requiresCorrectToolForDrops().sound(SoundType.STONE),
                    UniformInt.of(6, 14)));





    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
