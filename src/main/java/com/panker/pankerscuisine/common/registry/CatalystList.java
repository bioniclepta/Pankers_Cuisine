package com.panker.pankerscuisine.common.registry;

import com.panker.pankerscuisine.common.utility.CatalystProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class CatalystList {

    public static final Map<Item, CatalystProperties> ITEM_PROPERTIES_MAP = new HashMap<>();
    public CatalystList() {
    }

    public static void init() {
        ITEM_PROPERTIES_MAP.put(Items.IRON_INGOT, new CatalystProperties(2, 1));
        ITEM_PROPERTIES_MAP.put(Items.GOLD_INGOT, new CatalystProperties(3, 2));
    }

    public void storeItem(Item item, int multiplier, int complexity) {
        ITEM_PROPERTIES_MAP.put(item, new CatalystProperties(multiplier, complexity));
    }

    public static boolean containsKey(Item keyItem) {
        return ITEM_PROPERTIES_MAP.containsKey(keyItem);
    }

    public static CatalystProperties getProperties(Item keyItem) {
        return ITEM_PROPERTIES_MAP.get(keyItem);
    }


}

