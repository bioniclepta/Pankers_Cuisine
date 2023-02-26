package com.panker.pankerscuisine.client;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.client.utility.BrewLoader;
import com.panker.pankerscuisine.client.utility.PankersBrewSprites;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientMod {

    @SubscribeEvent
    public void registerModels(final ModelEvent.RegisterGeometryLoaders evt) {
        evt.register(Pankers_Cuisine.BREW_ID, BrewLoader.INSTANCE);
    }

    @SubscribeEvent
    public void registerTextures(final TextureStitchEvent.Pre evt) {
        TextureAtlas map = evt.getAtlas();

        if (map.location() == InventoryMenu.BLOCK_ATLAS) {

            for (ResourceLocation resourceLocation : PankersBrewSprites.get()) {
                evt.addSprite(resourceLocation);
            }
        }
    }
}
