package com.panker.pankerscuisine.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class Configuration {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    // COMMON
    public static final String CATEGORY_SETTINGS = "settings";

    public static final String CATEGORY_FARMING = "farming";


    public static final String CATEGORY_OVERRIDES = "overrides";


    public static final String CATEGORY_OVERRIDES_STACK_SIZE = "stack_size";


    public static final String CATEGORY_WORLD = "world";


    // CLIENT
    public static final String CATEGORY_CLIENT = "client";


    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_CONFIG = COMMON_BUILDER.build();

        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}
