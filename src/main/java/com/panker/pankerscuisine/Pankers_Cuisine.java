package com.panker.pankerscuisine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import com.panker.pankerscuisine.client.ClientMod;
import com.panker.pankerscuisine.common.registry.*;
import com.panker.pankerscuisine.client.ClientSetup;
import com.panker.pankerscuisine.common.CommonSetup;
import com.panker.pankerscuisine.common.Configuration;
import com.panker.pankerscuisine.common.painting.ModPaintings;
import com.panker.pankerscuisine.villager.ModVillagers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

import javax.annotation.Nonnull;
import java.util.Locale;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Pankers_Cuisine.MOD_ID)
public class Pankers_Cuisine {

    public static final String MOD_ID = "pankerscuisine";
    public static final String BREW_ID = "brew";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();


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
        ModBlockEntities.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModEntityTypes.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::addCreative);


        MinecraftForge.EVENT_BUS.register(this);
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == ModCreativeModeTab.CUISINE_TAB) {
            event.accept(ModItems.CHEESEPIZZASLICE);
            event.accept(ModItems.MARGHERITAPIZZASLICE);
            event.accept(ModItems.PEPPERONIPIZZASLICE);
            event.accept(ModItems.PEPPERONIPIZZAWHOLE);
            event.accept(ModItems.CHEESEPIZZAWHOLE);
            event.accept(ModItems.MARGHERITAPIZZAWHOLE);
            event.accept(ModItems.CHEESEBURGER);
            event.accept(ModItems.CINNAMON_BUN_COOKED);
            event.accept(ModItems.CINNAMON_BUN_RAW);
            event.accept(ModItems.DRAGON_MILK);
            event.accept(ModItems.TANKARD_OF_DRAGONS_MILK);
            event.accept(ModItems.CUSTOM_BREW);
            event.accept(ModItems.TACO_FOOD_TRUCK_SPAWN_EGG);

        }
        if(event.getTab() == ModCreativeModeTab.INGREDIENT_TAB) {
            event.accept(ModItems.CHEESE);
            event.accept(ModItems.TOMATO);
            event.accept(ModItems.TOMATO_SEEDS);
            event.accept(ModItems.LETTUCE);
            event.accept(ModItems.LETTUCE_SEEDS);
            event.accept(ModItems.PATTY_COOKED);
            event.accept(ModItems.PATTY_RAW);
            event.accept(ModItems.GARLIC);
            event.accept(ModItems.DOUGH);
            event.accept(ModItems.TANKARD);
        }
        if(event.getTab() == ModCreativeModeTab.ORES_TAB) {
            event.accept(ModItems.PANKERITE_PICKAXE);
            event.accept(ModItems.MILKIUM_TIPPED_NETHERITE_PICKAXE);

            event.accept(ModBlocks.DEEPSLATE_MILKIUM);
            event.accept(ModBlocks.DEEPSLATE_PANKERITE);
            event.accept(ModBlocks.THICCIUM_ORE);
            event.accept(ModBlocks.END_TOADERITE_ORE);
            event.accept(ModBlocks.NEHTER_KELVINITE_ORE);
            event.accept(ModBlocks.DELICIERITE_ORE);
            event.accept(ModItems.PANKERITE_INGOT);
            event.accept(ModItems.PANKERITE_NUGGET);
            event.accept(ModItems.RAW_PANKERITE);
            event.accept(ModItems.RAW_MILKIUM);
            event.accept(ModItems.CUT_MILKIUM_CRYSTAL);
            event.accept(ModItems.MILKIUM_CRYSTAL_CLUSTER);
        }
    }
}
