package com.panker.pankerscuisine.common.item.brews;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.datafixers.util.Pair;

import java.awt.*;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import com.panker.pankerscuisine.common.item.DrinkableItem;
import com.panker.pankerscuisine.common.utility.BrewNBT;
import com.panker.pankerscuisine.common.utility.BrewSpriteMixin;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
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
import org.jetbrains.annotations.Nullable;
import org.lwjgl.BufferUtils;

public class BrewItem extends DrinkableItem {

    public BrewItem() {
        super();
    }

    @Nonnull
    @Override
    public Component getName(@Nonnull ItemStack stack) {
        StringBuilder fullName = new StringBuilder();
        NonNullList<ItemStack> ingredients = BrewNBT.getIngredientsList(stack);

        if (!ingredients.isEmpty()) {
            Map<Item, Long> countMap = ingredients.stream()
                    .collect(Collectors.groupingBy(ItemStack::getItem, Collectors.counting()));
            List<String> names = new ArrayList<>();
            countMap.forEach((item, count) -> {
                StringBuilder builder = new StringBuilder();

                if (count > 1) {
                    MutableComponent trans =
                            Component.translatable("tooltip.pankerscuisine.count." + count);
                    builder.append(trans.getString());
                    builder.append(" ");
                }
                builder.append(Component.translatable(item.getDescriptionId()).getString());
                names.add(builder.toString());
            });
            fullName.append(
                    Component.translatable("tooltip.pankerscuisine.list." + names.size(), names.toArray())
                            .getString());
        }
        fullName.append(" ");
        fullName.append(Component.translatable(this.getDescriptionId(stack)).getString());

        return Component.literal(fullName.toString());
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
                    .withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.UNDERLINE));

            for (ItemStack ing : ingredients) {

                if (!ing.isEmpty()) {
                    tooltip.add(Component.translatable(ing.getDescriptionId())
                            .withStyle(ChatFormatting.GRAY));
                }
            }
            tooltip.add(Component.translatable("tooltip.pankerscuisine.ingredients")
                    .withStyle(ChatFormatting.GRAY));
        }
    }
/*
    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        //Store a list of all ingredients and their effects in another class, then reference that class and build the effects
        consumer.addEffect(getIngredientEffects(this.ingredient1));
        consumer.addEffect(getIngredientEffects(this.ingredient2));
        consumer.addEffect(getIngredientEffects(this.ingredient3));
    }



    @Override
    public Component getHighlightTip(ItemStack item, Component displayName) {
        return generateDisplayName(ingredient1, ingredient2, ingredient3);
    }
 */
    private String generateItemId(ItemStack ingredient1, ItemStack ingredient2, ItemStack ingredient3) {
        // Generate a unique identifier based on the ingredients
        String[] ingredientNames = {
                ingredient1.getDisplayName().toString(),
                ingredient2.getDisplayName().toString(),
                ingredient3.getDisplayName().toString()
        };

        Arrays.sort(ingredientNames);
        return String.join("_", ingredientNames).toLowerCase();
    }

    /*
    public MobEffectInstance getIngredientEffects(ItemStack ingredient) {
        //Get the mobeffects from here, can also use the multiplier of the durability of the item to pass in multipliers
    }

     */

    private Component generateDisplayName(ItemStack ingredient1, ItemStack ingredient2, ItemStack ingredient3) {
        // Generate a display name for the item based on the ingredients
        String[] first = (ingredient1.getDisplayName().toString()).split(" ");
        String first_first_word = first[0];
        String[] second = (ingredient2.getDisplayName().toString()).split(" ");
        String second_first_word = second[0];
        String[] third = (ingredient3.getDisplayName().toString()).split(" ");
        String third_first_word = third[0];

        //Sets the name to alphabetical order
        String[] brew_ingredients = {first_first_word, second_first_word, third_first_word};
        Arrays.sort(brew_ingredients);
        String alphabetized_brew = String.join("", brew_ingredients);
        return Component.literal(alphabetized_brew + " Brew");
    }

    private ResourceLocation generateTexture(ItemStack ingredient1, ItemStack ingredient2, ItemStack ingredient3, String outputName) {
        // Generate a texture for the item based on the ingredients
        int width = 16;
        int height = 16;
        int[] pixels = new int[width * height];
        Minecraft minecraft = Minecraft.getInstance();
        TextureManager textureManager = minecraft.getTextureManager();
        ItemRenderer itemRenderer = minecraft.getItemRenderer();

        // Get the item texture
        TextureAtlasSprite in1_sprite = itemRenderer.getModel(ingredient1, null, null, 1).getParticleIcon();
        TextureAtlasSprite in2_sprite = itemRenderer.getModel(ingredient1, null, null, 1).getParticleIcon();
        TextureAtlasSprite in3_sprite = itemRenderer.getModel(ingredient1, null, null, 1).getParticleIcon();
        // Calculate the dominant color of each input item's texture
        int[] colors = {calculateDominantColor(in1_sprite), calculateDominantColor(in2_sprite), calculateDominantColor(in3_sprite)};

        IntBuffer buffer = BufferUtils.createIntBuffer(width * height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                float position = (float) x / (float) width; // calculate the position in the gradient
                int color = interpolateColors(position, colors); // get the interpolated color for this position
                buffer.put(color);
            }
        }

        buffer.rewind();

        // Create a ResourceLocation for the custom texture
        ResourceLocation textureLocation = new ResourceLocation("my_mod", "textures/items/custom_item.png");

        // Create a new DynamicTexture using the buffer and the texture size (16x16)
        DynamicTexture texture = new DynamicTexture(16, 16, false);

        // Upload the texture data to OpenGL
        texture.upload();

        // Bind the texture to the ResourceLocation
        textureManager.bindForSetup(textureLocation);

        // Set the texture data using the buffer
        TextureUtil.initTexture(buffer, 16, 16);


        // Unbind the texture
        return textureLocation;
    }

    private int calculateDominantColor(TextureAtlasSprite sprite) {
        int iconWidth = sprite.getWidth();
        int iconHeight = sprite.getHeight();
        TreeMap<Integer, Integer> counts = new TreeMap<>();
        for (int v = 0; v < iconWidth; v++) {
            for (int u = 0; u < iconHeight; u++) {
                int rgba = ((BrewSpriteMixin) sprite).getMainImage()[0].getPixelRGBA(v, u);
                int alpha = rgba >> 24 & 0xFF;

                if (alpha > 0) {
                    counts.merge(rgba, 1, (color, count) -> count + 1);
                }
            }
        }
        int dominantColor = 0;
        int dominantSum = 0;

        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > dominantSum) {
                dominantSum = entry.getValue();
                dominantColor = entry.getKey();
            }
        }
        Color color = new Color(dominantColor, true);
        // No idea why the r and b values are reversed, but they are
        return new Color(color.getBlue(), color.getGreen(), color.getRed()).brighter().getRGB();
    }

    private static int interpolateColors(float position, int[] colors) {
        if (position <= 0.0f) {
            return colors[0];
        } else if (position >= 1.0f) {
            return colors[colors.length - 1];
        } else {
            float segmentSize = 1.0f / (colors.length - 1);
            int segmentIndex = (int) (position / segmentSize);
            float segmentPosition = (position - segmentIndex * segmentSize) / segmentSize;
            int startColor = colors[segmentIndex];
            int endColor = colors[segmentIndex + 1];
            return interpolateColor(startColor, endColor, segmentPosition);
        }
    }

    private static int interpolateColor(int startColor, int endColor, float position) {
        int startR = (startColor >> 16) & 0xFF;
        int startG = (startColor >> 8) & 0xFF;
        int startB = startColor & 0xFF;
        int endR = (endColor >> 16) & 0xFF;
        int endG = (endColor >> 8) & 0xFF;
        int endB = endColor & 0xFF;
        int r = (int) (startR + (endR - startR) * position);
        int g = (int) (startG + (endG - startG) * position);
        int b = (int) (startB + (endB - startB) * position);
        return (r << 16) | (g << 8) | b;
    }


}
