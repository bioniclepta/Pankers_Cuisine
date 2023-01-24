package com.panker.pankerscuisine.item;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Pankers_Cuisine.MOD_ID);

    public static final RegistryObject<Item> PEPPERONIPIZZAWHOLE = ITEMS.register("pepperoni_pizza_whole",
            () -> new ItemNameBlockItem(ModBlocks.PEPPERONIPIZZAWHOLE_BLOCK.get(),
                    new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB).stacksTo(1)));
    public static final RegistryObject<Item> PEPPERONIPIZZASLICE = ITEMS.register("pepperoni_pizza_slice",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));
    public static final RegistryObject<Item> CHEESEPIZZAWHOLE = ITEMS.register("cheese_pizza_whole",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));
    public static final RegistryObject<Item> CHEESEPIZZASLICE = ITEMS.register("cheese_pizza_slice",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));
    public static final RegistryObject<Item> MARGHERITAPIZZAWHOLE = ITEMS.register("margherita_pizza_whole",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));
    public static final RegistryObject<Item> MARGHERITAPIZZASLICE = ITEMS.register("margherita_pizza_slice",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.PANKERS_CUISINE_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
