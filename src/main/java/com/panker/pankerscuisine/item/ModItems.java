package com.panker.pankerscuisine.item;

import com.panker.pankerscuisine.Pankers_Cuisine;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Pankers_Cuisine.MOD_ID);

    public static final RegistryObject<Item> PEPPERONIPIZZAWHOLE = ITEMS.register("pepperoni_pizza_whole",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));
    public static final RegistryObject<Item> PEPPERONIPIZZASLICE = ITEMS.register("pepperoni_pizza_slice",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));
    public static final RegistryObject<Item> CHEESEPIZZAWHOLE = ITEMS.register("cheese_pizza_whole",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));
    public static final RegistryObject<Item> CHEESEPIZZASLICE = ITEMS.register("cheese_pizza_slice",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));
    public static final RegistryObject<Item> MARGARITAPIZZAWHOLE = ITEMS.register("margarita_pizza_whole",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));
    public static final RegistryObject<Item> MARGARITAPIZZASLICE = ITEMS.register("margarita_pizza_slice",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
