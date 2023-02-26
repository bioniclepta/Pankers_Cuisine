package com.panker.pankerscuisine.client;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.client.gui.BrickOvenScreen;
import com.panker.pankerscuisine.client.gui.DistilleryScreen;
import com.panker.pankerscuisine.client.gui.FermenterScreen;
import com.panker.pankerscuisine.client.utility.BrewLoader;
import com.panker.pankerscuisine.client.utility.PankersBrewSprites;
import com.panker.pankerscuisine.common.registry.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(ModMenuTypes.BRICK_OVEN_MENU.get(), BrickOvenScreen::new));
        event.enqueueWork(() -> MenuScreens.register(ModMenuTypes.DISTILLERY_MENU.get(), DistilleryScreen::new));
        event.enqueueWork(() -> MenuScreens.register(ModMenuTypes.FERMENTER_MENU.get(), FermenterScreen::new));

    }
}
