package com.panker.pankerscuisine;

import com.mojang.logging.LogUtils;
import com.panker.pankerscuisine.block.ModBlocks;
import com.panker.pankerscuisine.block.entity.ModBlockEntityTypes;
import com.panker.pankerscuisine.item.ModItems;
import com.panker.pankerscuisine.painting.ModPaintings;
import com.panker.pankerscuisine.recipe.ModRecipeSerializers;
import com.panker.pankerscuisine.recipe.ModRecipeTypes;
import com.panker.pankerscuisine.screen.BrickOvenScreen;
import com.panker.pankerscuisine.screen.ModMenuTypes;
import com.panker.pankerscuisine.villager.ModVillagers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Pankers_Cuisine.MOD_ID)
public class Pankers_Cuisine {

    public static final String MOD_ID = "pankerscuisine";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final RecipeBookType RECIPE_TYPE_BRICK_OVEN_COOKING = RecipeBookType.create("BRICK_OVEN_COOKING");


    public Pankers_Cuisine() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModPaintings.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipeSerializers.register(modEventBus);
        ModRecipeTypes.register(modEventBus);
        ModBlockEntityTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModVillagers.registerPOIs();
        });
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypes.BRICK_OVEN_MENU.get(), BrickOvenScreen::new);
        }
    }


}
