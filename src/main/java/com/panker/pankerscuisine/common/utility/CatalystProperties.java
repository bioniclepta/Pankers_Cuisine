package com.panker.pankerscuisine.common.utility;

import net.minecraft.world.item.Item;

public class CatalystProperties {
    public final int multiplier;
    public final int complexity;

    public CatalystProperties(int multiplier, int complexity) {
        this.multiplier = multiplier;
        this.complexity = complexity;
    }

    public int getMultiplier() {
        return this.multiplier;
    }

    public int getComplexity() {
        return this.complexity;
    }
}
