package com.panker.pankerscuisine.common.utility;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextureAtlasSprite.class)
public interface BrewSpriteMixin {

    @Accessor
    NativeImage[] getMainImage();
}
