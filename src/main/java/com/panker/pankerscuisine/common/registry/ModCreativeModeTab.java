package com.panker.pankerscuisine.common.registry;

import com.panker.pankerscuisine.Pankers_Cuisine;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Pankers_Cuisine.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTab {
    public static CreativeModeTab CUISINE_TAB;
    public static CreativeModeTab INGREDIENT_TAB;
    public static CreativeModeTab ORES_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        CUISINE_TAB = event.registerCreativeModeTab(new ResourceLocation(Pankers_Cuisine.MOD_ID, "cuisine_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.CHEESEPIZZASLICE.get())).title(Component.literal("Cuisines")).build());
        INGREDIENT_TAB = event.registerCreativeModeTab(new ResourceLocation(Pankers_Cuisine.MOD_ID, "ingredient_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.CHEESE.get())).title(Component.literal("Ingredients")).build());
        ORES_TAB = event.registerCreativeModeTab(new ResourceLocation(Pankers_Cuisine.MOD_ID, "ores_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.PANKERITE_INGOT.get())).title(Component.literal("Ores and Tools")).build());
    }
}
