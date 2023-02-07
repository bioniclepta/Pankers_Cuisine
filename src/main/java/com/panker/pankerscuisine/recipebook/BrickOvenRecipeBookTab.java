package com.panker.pankerscuisine.recipebook;
//Credit to farmers delight for the code
public enum BrickOvenRecipeBookTab {
    MEALS("meals"),
    DRINKS("drinks"),
    MISC("misc");

    public final String name;

    BrickOvenRecipeBookTab(String name) {
        this.name = name;
    }

    public static BrickOvenRecipeBookTab findByName(String name) {
        for (BrickOvenRecipeBookTab value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
