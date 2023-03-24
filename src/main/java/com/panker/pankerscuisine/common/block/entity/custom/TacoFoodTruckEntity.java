package com.panker.pankerscuisine.common.block.entity.custom;

import com.google.common.collect.ImmutableMap;
import com.panker.pankerscuisine.common.registry.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

public class TacoFoodTruckEntity extends BaseFoodTruckEntity{

    private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> itemListings) {
        return new Int2ObjectOpenHashMap<>(itemListings);
    }

    public TacoFoodTruckEntity(EntityType<? extends AbstractVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void updateTrades() {
        VillagerTrades.ItemListing[] avillagertrades$itemlisting = TACO_TRUCK_TRADES.get(1);
        VillagerTrades.ItemListing[] avillagertrades$itemlisting1 = TACO_TRUCK_TRADES.get(2);
        if (avillagertrades$itemlisting != null && avillagertrades$itemlisting1 != null) {
            if (this.level.enabledFeatures().contains(FeatureFlags.UPDATE_1_20)) {
                VillagerTrades.ItemListing[] avillagertrades$itemlisting2 = VillagerTrades.WANDERING_TRADER_TRADES_1_20.get(1);
                if (avillagertrades$itemlisting2 != null) {
                    avillagertrades$itemlisting = ArrayUtils.addAll((VillagerTrades.ItemListing[])avillagertrades$itemlisting, (VillagerTrades.ItemListing[])avillagertrades$itemlisting2);
                }
            }

            MerchantOffers merchantoffers = this.getOffers();
            this.addOffersFromItemListings(merchantoffers, avillagertrades$itemlisting, 5);
            int i = this.random.nextInt(avillagertrades$itemlisting1.length);
            VillagerTrades.ItemListing villagertrades$itemlisting = avillagertrades$itemlisting1[i];
            MerchantOffer merchantoffer = villagertrades$itemlisting.getOffer(this, this.random);
            if (merchantoffer != null) {
                merchantoffers.add(merchantoffer);
            }

        }
    }

    public static final Int2ObjectMap<VillagerTrades.ItemListing[]> TACO_TRUCK_TRADES = toIntMap(ImmutableMap.of(
            1, new VillagerTrades.ItemListing[]{
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.MUSHROOM_MOLE_BURRITO.get(), 15, 1, 5, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.BLAZING_HOT_BURRITO.get(), 13, 1, 4, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.CREEPER_CRUNCH_TACO.get(), 8, 1, 12, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.DIAMOND_DUST_TACO.get(), 24, 1, 2, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.GOLDEN_APPLE_TACO.get(), 24, 1, 4, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.REDSTONE_REFRIED_BEANS_TACO.get(), 20, 1, 4, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.GHASTLY_GUACAMOLE_TACO.get(), 16, 1, 4, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.SWEETBERRY_SALSA.get(), 8, 1, 12, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.NETHERITE_NACHOS.get(), 16, 1, 2, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.HONEYCOMB_HABANERO_TACO.get(), 8, 1, 8, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.CACTUS_CARNE_ASADA_TACO.get(), 8, 1, 12, 1),
            },
            2, new VillagerTrades.ItemListing[]{
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.CHARGED_CREEPER_CRUNCH_TACO.get(), 40, 1, 1, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.EMERALD_TACO.get(), 32, 1, 1, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.ENCHANTED_GOLDEN_APPLE_TACO.get(), 50, 1, 1, 1),
                    new BaseFoodTruckEntity.ItemsForEmeralds(ModItems.LAPIS_LAZULI_LETTUCE_WRAP.get(), 25, 1, 1, 1)
            }));


}
