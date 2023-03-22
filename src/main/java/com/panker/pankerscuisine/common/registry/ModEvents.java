package com.panker.pankerscuisine.common.registry;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.block.entity.custom.BaseFoodTruckEntity;
import com.panker.pankerscuisine.common.registry.ModItems;
import com.panker.pankerscuisine.event.DragonMilkEvent;
import com.panker.pankerscuisine.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = Pankers_Cuisine.MOD_ID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event) {
            if(event.getType() == VillagerProfession.TOOLSMITH) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItems.PIZZACUTTER.get(), 1);
                int villagerLevel = 1;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 2),
                        stack,10,8,0.02F));
            }

            if(event.getType() == ModVillagers.MASTER_CHEF.get()) {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItems.CHEESEBURGER.get(), 15);
                int villagerLevel = 1;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 5),
                        stack,10,8,0.02F));
            }
        }

        @SubscribeEvent
        public static void onDragonInteract(PlayerInteractEvent.EntityInteract entityInteract) {
            if (DragonMilkEvent.onEntityInteract(entityInteract.getEntity(), entityInteract.getLevel(), entityInteract.getHand(),
                    entityInteract.getTarget(), null).equals(InteractionResult.SUCCESS)) {
                entityInteract.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Pankers_Cuisine.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent

        public static void entityAttributeEvent(EntityAttributeCreationEvent attributeCreationEvent) {
            attributeCreationEvent.put(ModEntityTypes.TACO_FOOD_TRUCK.get(), BaseFoodTruckEntity.setAttributes());
        }
    }
}
