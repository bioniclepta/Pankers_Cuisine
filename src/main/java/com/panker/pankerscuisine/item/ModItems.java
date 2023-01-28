package com.panker.pankerscuisine.item;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.block.ModBlocks;
import com.panker.pankerscuisine.item.custom.PizzaCutterItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Pankers_Cuisine.MOD_ID);

    // Items
    public static final RegistryObject<Item> PIZZACUTTER = ITEMS.register("pizzacutter",
            () -> new PizzaCutterItem(Tiers.IRON, 0.5f, -2.0f,
                    new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));

    // Block item references
    public static final RegistryObject<Item> PEPPERONIPIZZAWHOLE = ITEMS.register("pepperoni_pizza_whole",
            () -> new ItemNameBlockItem(ModBlocks.PEPPERONIPIZZAWHOLE_BLOCK.get(),
                    new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)
                            .stacksTo(1)));
    public static final RegistryObject<Item> CHEESEPIZZAWHOLE = ITEMS.register("cheese_pizza_whole",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));
    public static final RegistryObject<Item> MARGHERITAPIZZAWHOLE = ITEMS.register("margherita_pizza_whole",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));

    // Pizza's
    public static final RegistryObject<Item> PEPPERONIPIZZASLICE = ITEMS.register("pepperoni_pizza_slice",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(4).saturationMod(4f).build())));
    public static final RegistryObject<Item> CHEESEPIZZASLICE = ITEMS.register("cheese_pizza_slice",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(4).saturationMod(4f).build())));
    public static final RegistryObject<Item> MARGHERITAPIZZASLICE = ITEMS.register("margherita_pizza_slice",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(4).saturationMod(4f).build())));

    // Burgers
    public static final RegistryObject<Item> CHEESEBURGER = ITEMS.register("cheeseburger",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(11).saturationMod(4f).build())));

    // Raw Ingredients
    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)
                    .food(new FoodProperties.Builder().nutrition(2).saturationMod(0.4f).build())));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.3f).build())));
    public static final RegistryObject<Item> LETTUCE = ITEMS.register("lettuce",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));

    // Seeds
    public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds",
            () -> new ItemNameBlockItem(ModBlocks.TOMATO_CROP.get(),
                    new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));
    public static final RegistryObject<Item> LETTUCE_SEEDS = ITEMS.register("lettuce_seeds",
            () -> new ItemNameBlockItem(ModBlocks.LETTUCE_CROP.get(),
                    new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
