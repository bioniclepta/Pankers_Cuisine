package com.panker.pankerscuisine.common.registry;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.block.entity.container.BrickOvenMenu;
import com.panker.pankerscuisine.common.block.entity.container.DistilleryMenu;
import com.panker.pankerscuisine.common.block.entity.container.FermenterMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Pankers_Cuisine.MOD_ID);

    public static final RegistryObject<MenuType<BrickOvenMenu>> BRICK_OVEN_MENU =
            registerMenuType(BrickOvenMenu::new, "brick_oven_menu");
    public static final RegistryObject<MenuType<DistilleryMenu>> DISTILLERY_MENU =
            registerMenuType(DistilleryMenu::new, "distillery_menu");
    public static final RegistryObject<MenuType<FermenterMenu>> FERMENTER_MENU =
            registerMenuType(FermenterMenu::new, "fermenter_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
