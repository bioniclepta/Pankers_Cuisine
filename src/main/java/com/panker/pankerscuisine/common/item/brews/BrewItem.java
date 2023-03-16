package com.panker.pankerscuisine.common.item.brews;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;

import java.awt.*;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.item.DrinkableItem;
import com.panker.pankerscuisine.common.utility.BrewNBT;
import com.panker.pankerscuisine.common.utility.BrewSpriteMixin;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.BufferUtils;

public class BrewItem extends DrinkableItem {

    public BrewItem() {
        super();
    }

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(".#");

    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable Level level,
                                java.util.List<Component> tooltip, TooltipFlag flagIn) {
        ItemStack base = BrewNBT.getBase(stack);
        int quality = BrewNBT.getQuality(stack);
        tooltip.add(Component.translatable("Quality " + quality)
                .withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.translatable(base.getDescriptionId()).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.empty());
        long windowId = Minecraft.getInstance().getWindow().getWindow();

        if (InputConstants.isKeyDown(windowId, 340) || InputConstants.isKeyDown(windowId, 344)) {
            NonNullList<ItemStack> ingredients = BrewNBT.getIngredientsList(stack);
            tooltip.add(Component.translatable("Ingredients")
                    .withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.UNDERLINE));

            for (ItemStack ing : ingredients) {

                if (!ing.isEmpty()) {
                    tooltip.add(Component.translatable(ing.getDescriptionId())
                            .withStyle(ChatFormatting.GRAY));
                }
            }
        } else {
            tooltip.add(Component.translatable("Shift To See Ingredients ")
                    .withStyle(ChatFormatting.GOLD));
        }
    }
}
