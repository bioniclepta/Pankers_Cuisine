package com.panker.pankerscuisine.client;

import com.panker.pankerscuisine.client.gui.BrickOvenScreen;
import com.panker.pankerscuisine.client.gui.DistilleryScreen;
import com.panker.pankerscuisine.client.gui.FermenterScreen;
import com.panker.pankerscuisine.common.block.entity.client.BaseFoodTruckRenderer;
import com.panker.pankerscuisine.common.block.entity.client.TacoFoodTruckRenderer;
import com.panker.pankerscuisine.common.registry.ModEntityTypes;
import com.panker.pankerscuisine.common.registry.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(ModMenuTypes.BRICK_OVEN_MENU.get(), BrickOvenScreen::new));
        event.enqueueWork(() -> MenuScreens.register(ModMenuTypes.DISTILLERY_MENU.get(), DistilleryScreen::new));
        event.enqueueWork(() -> MenuScreens.register(ModMenuTypes.FERMENTER_MENU.get(), FermenterScreen::new));

        EntityRenderers.register(ModEntityTypes.TACO_FOOD_TRUCK.get(), TacoFoodTruckRenderer::new);
    }
}
