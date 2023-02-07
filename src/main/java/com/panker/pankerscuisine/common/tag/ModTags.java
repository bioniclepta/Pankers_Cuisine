package com.panker.pankerscuisine.common.tag;

import com.panker.pankerscuisine.Pankers_Cuisine;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    // Blocks mineable with pizza cutters
    public static final TagKey<Block> MINEABLE_WITH_PIZZACUTTER = modBlockTag("mineable/pizzacutter");

    // Pizza Cutter items for game logic.
    public static final TagKey<Item> PIZZACUTTERS = modItemTag("tools/pizzacutters");

    // Pizza blocks drop pizza slices
    public static final TagKey<Block> DROPS_PIZZA_SLICE = modBlockTag("drops_pizza_slice");

    // Blocks that can heat up cooking workstations.
    public static final TagKey<Block> HEAT_SOURCES = modBlockTag("heat_sources");

    // Blocks that can transfer heat to cooking workstations, if directly above a heat source.
    public static final TagKey<Block> HEAT_CONDUCTORS = modBlockTag("heat_conductors");

    private static TagKey<Item> modItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Pankers_Cuisine.MOD_ID + ":" + path));
    }
    private static TagKey<Block> modBlockTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Pankers_Cuisine.MOD_ID + ":" + path));
    }
}
