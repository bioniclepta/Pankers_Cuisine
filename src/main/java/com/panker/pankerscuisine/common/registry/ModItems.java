package com.panker.pankerscuisine.common.registry;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.item.BaseTacoItem;
import com.panker.pankerscuisine.common.item.brews.BrewItem;
import com.panker.pankerscuisine.common.item.brews.TankardOfDragonsMilk;
import com.panker.pankerscuisine.common.item.custom.DragonsMilkBucketItem;
import com.panker.pankerscuisine.common.item.custom.PizzaCutterItem;
import com.panker.pankerscuisine.common.item.tools.Milkium_Tipped_Netherite_Pickaxe;
import com.panker.pankerscuisine.common.item.tools.Pankerite_Pickaxe;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Pankers_Cuisine.MOD_ID);

    //Credit to farmers delight for the implementation
    public static Item.Properties basicItem() {
        return new Item.Properties();
    }
    public static Item.Properties brewItem() {
        return new Item.Properties().craftRemainder(ModItems.TANKARD.get()).stacksTo(4).defaultDurability(7);
    }

    public static final Item.Properties tacoItem(){return new Item.Properties().rarity(Rarity.UNCOMMON)
            .food(new FoodProperties.Builder().nutrition(EXTRA_LARGE_NUTRITION).saturationMod(SCHMEDIUM_SATURATION).build());}

    public static final int BRIEF_DURATION = 600;    // 30 seconds
    public static final int SHORT_DURATION = 1200;    // 1 minute
    public static final int MEDIUM_DURATION = 3600;    // 3 minutes
    public static final int LONG_DURATION = 6000;    // 5 minutes

    public static final int MINUSCULE_NUTRITION = 1;       // 0.5 Meatsticks
    public static final int SMALL_NUTRITION = 2;           // 1 Meatsticks
    public static final int MEDIUM_NUTRITION = 6;          // 3 Meatsticks (Golden carrot)
    public static final int SCHMEDIUM_NUTRITION = 8;       // 4 Meatsticks (Porkchop)
    public static final int LARGE_NUTRITION = 12;          // 6 Meatsticks
    public static final int EXTRA_LARGE_NUTRITION = 16;    // 8 Meatsticks

    //number of meatsticks is 10 * value
    public static final float MINUSCULE_SATURATION = 0.1f;      // Barely anything
    public static final float SMALL_SATURATION = 2f;            // 8 Meatsticks
    public static final float MEDIUM_SATURATION = 0.6f;         // 12.8 Meatsticks (Porkchop)
    public static final float SCHMEDIUM_SATURATION = 0.8f;      // 14.4 Meatsticks (Golden Carrot)
    public static final float LARGE_SATURATION = 1f;            // 10 Meatsticks
    public static final float EXTRA_LARGE_SATURATION = 1.4f;    // 14 Meatsticks

    //For foods:
    //Drumsticks filled = nutrition / 2
    //Saturation = saturationmod * 4

    // Items
    public static final RegistryObject<Item> PIZZACUTTER = ITEMS.register("pizzacutter",
            () -> new PizzaCutterItem(Tiers.IRON, 0.5f, -2.0f,
                    new Item.Properties()));
    public static final RegistryObject<Item> PANKERITE_PICKAXE = ITEMS.register("pankerite_pickaxe",
            () -> new Pankerite_Pickaxe(ModTiers.PANKERITE, 1, -2.8F,
                    new Item.Properties()));
    public static final RegistryObject<Item> MILKIUM_TIPPED_NETHERITE_PICKAXE = ITEMS.register("milkium_tipped_netherite_pickaxe",
            () -> new Milkium_Tipped_Netherite_Pickaxe(ModTiers.MILKIUM, 1, -2.8F,
                    new Item.Properties()));
    public static final RegistryObject<Item> TANKARD = ITEMS.register("tankard",
            () -> new Item(new Item.Properties().stacksTo(16)));

    //Ingots
    public static final RegistryObject<Item> PANKERITE_INGOT = ITEMS.register("pankerite_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PANKERITE_NUGGET = ITEMS.register("pankerite_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_PANKERITE = ITEMS.register("raw_pankerite",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_MILKIUM = ITEMS.register("raw_milkium",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CUT_MILKIUM_CRYSTAL = ITEMS.register("cut_milkium_crystal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MILKIUM_CRYSTAL_CLUSTER = ITEMS.register("milkium_crystal_cluster",
            () -> new Item(new Item.Properties()));

    //Brews
    public static final RegistryObject<Item> TANKARD_OF_DRAGONS_MILK = ITEMS.register("tankard_of_dragons_milk",
            () -> new TankardOfDragonsMilk(brewItem()));

    public static final RegistryObject<Item> DRAGON_MILK = ITEMS.register("dragon_milk",
            () -> new DragonsMilkBucketItem(new Item.Properties().stacksTo(1)));

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
                    new Item.Properties()
                            .stacksTo(1)));
    public static final RegistryObject<Item> CHEESEPIZZAWHOLE = ITEMS.register("cheese_pizza_whole",
            () -> new ItemNameBlockItem(ModBlocks.CHEESEPIZZAWHOLE_BLOCK.get(),
                    new Item.Properties()
                            .stacksTo(1)));
    public static final RegistryObject<Item> MARGHERITAPIZZAWHOLE = ITEMS.register("margherita_pizza_whole",
            () -> new ItemNameBlockItem(ModBlocks.MARGHERITAPIZZAWHOLE_BLOCK.get(),
                    new Item.Properties()
                            .stacksTo(1)));

    // Pizza's
    public static final RegistryObject<Item> PEPPERONIPIZZASLICE = ITEMS.register("pepperoni_pizza_slice",
            () -> new Item(new Item.Properties()
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(4).saturationMod(4f).build())));
    public static final RegistryObject<Item> CHEESEPIZZASLICE = ITEMS.register("cheese_pizza_slice",
            () -> new Item(new Item.Properties()
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(4).saturationMod(4f).build())));
    public static final RegistryObject<Item> MARGHERITAPIZZASLICE = ITEMS.register("margherita_pizza_slice",
            () -> new Item(new Item.Properties()
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(4).saturationMod(4f).build())));

    // Cooked Foods
    public static final RegistryObject<Item> CHEESEBURGER = ITEMS.register("cheeseburger",
            () -> new Item(new Item.Properties()
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(LARGE_NUTRITION).saturationMod(4f).build())));
    public static final RegistryObject<Item> CINNAMON_BUN_COOKED = ITEMS.register("cinnamon_bun_cooked",
            () -> new Item(new Item.Properties()
                    .stacksTo(16).food(new FoodProperties.Builder().nutrition(6).saturationMod(4f).build())));

    // Raw Ingredients
    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(2).saturationMod(0.4f).build())));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.3f).build())));
    public static final RegistryObject<Item> LETTUCE = ITEMS.register("lettuce",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<Item> PATTY_RAW = ITEMS.register("patty_raw",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<Item> DOUGH = ITEMS.register("dough",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<Item> CINNAMON_BUN_RAW = ITEMS.register("cinnamon_bun_raw",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<Item> GARLIC = ITEMS.register("garlic",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));

    //Cooked Ingredients
    public static final RegistryObject<Item> PATTY_COOKED = ITEMS.register("patty_cooked",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(3).saturationMod(2f).build())));

    //Food Truck Foods
    public static final RegistryObject<Item> MUSHROOM_MOLE_BURRITO = ITEMS.register("mushroom_mole_burrito",
            () -> new BaseTacoItem(tacoItem(),
                    "Harvested straight from the finest mycelium, this mushroom mole burrito will have you feeling satiated and hearty!",
                    new MobEffectInstance(MobEffects.ABSORPTION, 100 * 20, 1)));
    public static final RegistryObject<Item> BLAZING_HOT_BURRITO = ITEMS.register("blazing_hot_burrito",
            () -> new BaseTacoItem(tacoItem(),
                    "From the heart of the nether, this burrito will heat you up from the inside out!",
                    new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100 * 20, 1)));
    public static final RegistryObject<Item> EMERALD_TACO = ITEMS.register("emerald_taco",
            () -> new BaseTacoItem(tacoItem(),
                    "Most popular amongst villagers from the snowy tundras!",
                    new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 100 * 20, 1)));
    public static final RegistryObject<Item> CREEPER_CRUNCH_TACO = ITEMS.register("creeper_crunch_taco",
            () -> new BaseTacoItem(tacoItem(),
                    "Beans, sour cream, and the hearts of creepers!",
                    new MobEffectInstance(MobEffects.ABSORPTION, 5 * 20, 20), false, 1));
    public static final RegistryObject<Item> CHARGED_CREEPER_CRUNCH_TACO = ITEMS.register("charged_creeper_crunch_taco",
            () -> new BaseTacoItem(tacoItem(),
                    "A food truck got hit by lightning and made this delicacy!",
                    new MobEffectInstance(MobEffects.ABSORPTION, 5 * 20, 20),
                    true, 2));
    public static final RegistryObject<Item> DIAMOND_DUST_TACO = ITEMS.register("diamond_dust_taco",
            () -> new BaseTacoItem(tacoItem(),
                    "Indulge in a truly luxurious dining experience with our Diamond Dust Taco," +
                            " featuring a dazzling array of precious gems for a taste that's worth its weight in gold!",
                    new MobEffectInstance(MobEffects.DIG_SPEED, 200 * 20, 2)));
    public static final RegistryObject<Item> GOLDEN_APPLE_TACO = ITEMS.register("golden_apple_taco",
            () -> new BaseTacoItem(new Item.Properties().rarity(Rarity.UNCOMMON)
                    .food(new FoodProperties.Builder().nutrition(LARGE_NUTRITION).saturationMod(LARGE_SATURATION)
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F)
                            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1400, 0), 1.0F)
                            .alwaysEat().build()),
                    "For adventurers and builders alike!", false));
    public static final RegistryObject<Item> ENCHANTED_GOLDEN_APPLE_TACO = ITEMS.register("enchanted_golden_apple_taco",
            () -> new BaseTacoItem(new Item.Properties().rarity(Rarity.UNCOMMON)
                    .food(new FoodProperties.Builder().nutrition(LARGE_NUTRITION).saturationMod(LARGE_SATURATION)
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 300, 1), 1.0F)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 4000, 0), 1.0F)
                            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 4000, 0), 1.0F)
                            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1400, 3), 1.0F)
                            .alwaysEat().build()),
                    "Very rare item, all of these tacos were made before Mojang removed the crafting recipe in 1.9!", true));
    public static final RegistryObject<Item> REDSTONE_REFRIED_BEANS_TACO = ITEMS.register("redstone_refried_beans_taco",
            () -> new BaseTacoItem(tacoItem(),
                    "For those looking to get their redstone fix quicker",
                    new MobEffectInstance(MobEffects.DIG_SPEED, 200 * 20, 1)));
    public static final RegistryObject<Item> LAPIS_LAZULI_LETTUCE_WRAP = ITEMS.register("lapis_lazuli_lettuce_wrap",
            () -> new BaseTacoItem(tacoItem(),
                    "A more lean option for those looking to watch their weight",
                    new MobEffectInstance(MobEffects.LUCK, 80 * 20, 1)));
    public static final RegistryObject<Item> GHASTLY_GUACAMOLE_TACO = ITEMS.register("ghastly_guacamole_taco",
            () -> new BaseTacoItem(tacoItem(),
                    "Cutting onions in front of a ghast is helpful to make these",
                    new MobEffectInstance(MobEffects.REGENERATION, 120 * 20, 2)));
    public static final RegistryObject<Item> NETHERITE_NACHOS = ITEMS.register("netherite_nachos",
            () -> new BaseTacoItem(tacoItem().fireResistant(),
                    "A rare delicacy for those with strong teeth",
                    new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200 * 20, 1)));
    public static final RegistryObject<Item> SWEETBERRY_SALSA = ITEMS.register("sweetberry_salsa",
            () -> new BaseTacoItem(tacoItem(),
                    "Great when paired with any of our tacos or burritos",
                    new MobEffectInstance(MobEffects.HEAL, 200 * 20, 5)));
    public static final RegistryObject<Item> HONEYCOMB_HABANERO_TACO = ITEMS.register("honeycomb_habanero_taco",
            () -> new BaseTacoItem(tacoItem(),
                    "A spicy and sweet combination to get you going at your fastest",
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200 * 20, 2)));
    public static final RegistryObject<Item> CACTUS_CARNE_ASADA_TACO = ITEMS.register("cactus_carne_asada_taco",
            () -> new BaseTacoItem(tacoItem(),
                    "A prickly treat with a tang of sweetness",
                    new MobEffectInstance(MobEffects.SATURATION, 100 * 20, 2)));


    // Seeds
    public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds",
            () -> new ItemNameBlockItem(ModBlocks.TOMATO_CROP.get(),
                    new Item.Properties()));
    public static final RegistryObject<Item> LETTUCE_SEEDS = ITEMS.register("lettuce_seeds",
            () -> new ItemNameBlockItem(ModBlocks.LETTUCE_CROP.get(),
                    new Item.Properties()));


    //Spawn Eggs
    public static final RegistryObject<Item> TACO_FOOD_TRUCK_SPAWN_EGG = ITEMS.register("taco_food_truck_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.TACO_FOOD_TRUCK, 0xF8B602, 0xB9D982,
                    new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
