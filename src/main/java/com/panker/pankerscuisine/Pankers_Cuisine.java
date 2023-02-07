package com.panker.pankerscuisine;

import com.mojang.logging.LogUtils;
import com.panker.pankerscuisine.common.registry.ModBlocks;
import com.panker.pankerscuisine.common.registry.ModBlockEntityTypes;
import com.panker.pankerscuisine.client.ClientSetup;
import com.panker.pankerscuisine.common.CommonSetup;
import com.panker.pankerscuisine.common.Configuration;
import com.panker.pankerscuisine.common.registry.ModItems;
import com.panker.pankerscuisine.common.painting.ModPaintings;
import com.panker.pankerscuisine.common.registry.ModRecipeSerializers;
import com.panker.pankerscuisine.common.registry.ModRecipeTypes;
import com.panker.pankerscuisine.common.registry.ModMenuTypes;
import com.panker.pankerscuisine.villager.ModVillagers;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Pankers_Cuisine.MOD_ID)
public class Pankers_Cuisine {

    public static final String MOD_ID = "pankerscuisine";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final RecipeBookType RECIPE_TYPE_BRICK_OVEN_COOKING = RecipeBookType.create("BRICK_OVEN_COOKING");

    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(Pankers_Cuisine.MOD_ID)
    {
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.CHEESEBURGER.get());
        }
    };


    public Pankers_Cuisine() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(CommonSetup::init);
        modEventBus.addListener(ClientSetup::init);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Configuration.CLIENT_CONFIG);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModPaintings.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipeSerializers.register(modEventBus);
        ModRecipeTypes.register(modEventBus);
        ModBlockEntityTypes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
