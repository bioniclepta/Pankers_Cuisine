package com.panker.pankerscuisine.tag;

import com.panker.pankerscuisine.Pankers_Cuisine;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ForgeTags {

    // Blocks mineable with pizza cutters
    public static final TagKey<Block> MINEABLE_WITH_PIZZACUTTER = forgeBlockTag("mineable/pizzacutter");

    // Forge Cheeses.
    public static final TagKey<Item> CHEESES = forgeItemTag("cheeses");
    public static final TagKey<Item> DOUGH = forgeItemTag("dough/dough");
    public static final TagKey<Item> CROPS_TOMATO = forgeItemTag("crops/tomato");
    public static final TagKey<Item> CROPS_LETTUCE = forgeItemTag("crops/lettuce");

    private static TagKey<Item> forgeItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", path));
    }
    private static TagKey<Block> forgeBlockTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("forge", path));
    }
}
