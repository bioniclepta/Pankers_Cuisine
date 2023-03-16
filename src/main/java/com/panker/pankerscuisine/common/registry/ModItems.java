package com.panker.pankerscuisine.common.registry;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.item.brews.BrewItem;
import com.panker.pankerscuisine.common.item.brews.TankardOfDragonsMilk;
import com.panker.pankerscuisine.common.item.custom.DragonsMilkBucketItem;
import com.panker.pankerscuisine.common.item.custom.PizzaCutterItem;
import com.panker.pankerscuisine.common.item.tools.Pankerite_Pickaxe;
import com.panker.pankerscuisine.data.ModTiers;
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
    public static Item.Properties brewItem() {
        return new Item.Properties().craftRemainder(ModItems.TANKARD.get()).stacksTo(4).defaultDurability(7).tab(Pankers_Cuisine.CREATIVE_TAB);
    }

    public static final int BRIEF_DURATION = 600;    // 30 seconds
    public static final int SHORT_DURATION = 1200;    // 1 minute
    public static final int MEDIUM_DURATION = 3600;    // 3 minutes
    public static final int LONG_DURATION = 6000;    // 5 minutes

    public static final int MINUSCULE_NUTRITION = 1;    // 0.5 Meatsticks
    public static final int SMALL_NUTRITION = 2;         // 1 Meatsticks
    public static final int MEDIUM_NUTRITION = 6;        // 3 Meatsticks (Golden carrot)
    public static final int SCHMEDIUM_NUTRITION = 8;     // 4 Meatsticks (Porkchop)
    public static final int LARGE_NUTRITION = 10;          // 5 Meatsticks

    public static final float MINUSCULE_SATURATION = 0.1f;    // Barely anything
    public static final float SMALL_SATURATION = 2f;         // 8 Meatsticks
    public static final float MEDIUM_SATURATION = 3.2f;         // 12.8 Meatsticks (Porkchop)
    public static final float SCHMEDIUM_SATURATION = 3.6f;    // 14.4 Meatsticks (Golden Carrot)
    public static final float LARGE_SATURATION = 4f;            // 16 Meatsticks

    //For foods:
    //Drumsticks filled = nutrition / 2
    //Saturation = saturationmod * 4

    // Items
    public static final RegistryObject<Item> PIZZACUTTER = ITEMS.register("pizzacutter",
            () -> new PizzaCutterItem(Tiers.IRON, 0.5f, -2.0f,
                    new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)));
    public static final RegistryObject<Item> PANKERITE_PICKAXE = ITEMS.register("pankerite_pickaxe",
            () -> new Pankerite_Pickaxe(ModTiers.PANKERITE, 1, -2.8F,
                    new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)));
    public static final RegistryObject<Item> TANKARD = ITEMS.register("tankard",
            () -> new Item(new Item.Properties().stacksTo(16).tab(Pankers_Cuisine.CREATIVE_TAB)));

    //Ingots
    public static final RegistryObject<Item> PANKERITE_INGOT = ITEMS.register("pankerite_ingot",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)));
    public static final RegistryObject<Item> PANKERITE_NUGGET = ITEMS.register("pankerite_nugget",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)));
    public static final RegistryObject<Item> RAW_PANKERITE = ITEMS.register("raw_pankerite",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)));

    //Brews
    public static final RegistryObject<Item> TANKARD_OF_DRAGONS_MILK = ITEMS.register("tankard_of_dragons_milk",
            () -> new TankardOfDragonsMilk(brewItem()));

    public static final RegistryObject<Item> DRAGON_MILK = ITEMS.register("dragon_milk",
            () -> new DragonsMilkBucketItem(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB).stacksTo(1)));

    public static final RegistryObject<Item> CUSTOM_BREW = ITEMS.register("custom_brew", BrewItem::new);

    //Block Items
    public static final RegistryObject<Item> BRICK_OVEN = ITEMS.register("brick_oven",
            () -> new BlockItem(ModBlocks.BRICK_OVEN_BLOCK.get(), basicItem()));
    public static final RegistryObject<Item> DISTILLERY = ITEMS.register("distillery",
            () -> new BlockItem(ModBlocks.DISTILLERY_BLOCK.get(), basicItem()));
    public static final RegistryObject<Item> FERMENTER = ITEMS.register("fermenter",
            () -> new BlockItem(ModBlocks.FERMENTER_BLOCK.get(), basicItem()));

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
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(LARGE_NUTRITION).saturationMod(4f).build())));
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
    public static final RegistryObject<Item> CINNAMON_BUN_RAW = ITEMS.register("cinnamon_bun_raw",
            () -> new Item(new Item.Properties().tab(Pankers_Cuisine.CREATIVE_TAB)
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<Item> GARLIC = ITEMS.register("garlic",
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
