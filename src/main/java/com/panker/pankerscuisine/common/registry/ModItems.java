package com.panker.pankerscuisine.common.registry;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.item.custom.PizzaCutterItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Pankers_Cuisine.MOD_ID);

    //Credit to farmers delight for the implementation
    public static Item.Properties basicItem() {
        return new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB);
    }

    //For foods:
    //Drumsticks filled = nutrition / 2
    //Saturation = saturationmod * 4

    // Items
    public static final RegistryObject<Item> PIZZACUTTER = ITEMS.register("pizzacutter",
            () -> new PizzaCutterItem(Tiers.IRON, 0.5f, -2.0f,
                    new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)));

    //Block Items
    public static final RegistryObject<Item> BRICK_OVEN = ITEMS.register("brick_oven",
            () -> new BlockItem(ModBlocks.BRICK_OVEN_BLOCK.get(), basicItem()));

    // Block item references
    public static final RegistryObject<Item> PEPPERONIPIZZAWHOLE = ITEMS.register("pepperoni_pizza_whole",
            () -> new ItemNameBlockItem(ModBlocks.PEPPERONIPIZZAWHOLE_BLOCK.get(),
                    new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                            .stacksTo(1)));
    public static final RegistryObject<Item> CHEESEPIZZAWHOLE = ITEMS.register("cheese_pizza_whole",
            () -> new ItemNameBlockItem(ModBlocks.CHEESEPIZZAWHOLE_BLOCK.get(),
                    new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                            .stacksTo(1)));
    public static final RegistryObject<Item> MARGHERITAPIZZAWHOLE = ITEMS.register("margherita_pizza_whole",
            () -> new ItemNameBlockItem(ModBlocks.MARGHERITAPIZZAWHOLE_BLOCK.get(),
                    new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                            .stacksTo(1)));

    // Pizza's
    public static final RegistryObject<Item> PEPPERONIPIZZASLICE = ITEMS.register("pepperoni_pizza_slice",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(4).saturationMod(4f).build())));
    public static final RegistryObject<Item> CHEESEPIZZASLICE = ITEMS.register("cheese_pizza_slice",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(4).saturationMod(4f).build())));
    public static final RegistryObject<Item> MARGHERITAPIZZASLICE = ITEMS.register("margherita_pizza_slice",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(4).saturationMod(4f).build())));

    // Cooked Foods
    public static final RegistryObject<Item> CHEESEBURGER = ITEMS.register("cheeseburger",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(11).saturationMod(4f).build())));
    public static final RegistryObject<Item> CINNAMON_BUN_COOKED = ITEMS.register("cinnamon_bun_cooked",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(6).saturationMod(4f).build())));

    // Raw Ingredients
    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                    .food(new FoodProperties.Builder().nutrition(2).saturationMod(0.4f).build())));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.3f).build())));
    public static final RegistryObject<Item> LETTUCE = ITEMS.register("lettuce",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<Item> PATTY_RAW = ITEMS.register("patty_raw",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<Item> DOUGH = ITEMS.register("dough",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));

    //Cooked Ingredients
    public static final RegistryObject<Item> PATTY_COOKED = ITEMS.register("patty_cooked",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(2f).build())));

    // Seeds
    public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds",
            () -> new ItemNameBlockItem(ModBlocks.TOMATO_CROP.get(),
                    new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)));
    public static final RegistryObject<Item> LETTUCE_SEEDS = ITEMS.register("lettuce_seeds",
            () -> new ItemNameBlockItem(ModBlocks.LETTUCE_CROP.get(),
                    new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
