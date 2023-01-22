package com.panker.pankerscuisine.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab PANKERS_CUISINE_TAB = new CreativeModeTab("pankerscuisinetab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.PEPPERONIPIZZASLICE.get());
        }
    };
}
