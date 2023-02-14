package com.panker.pankerscuisine.event;

import com.panker.pankerscuisine.common.registry.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
//Credit to Milk All The Mobs for the integration
public class DragonMilkEvent {
    public static InteractionResult onEntityInteract(Player player, Level world, InteractionHand hand, Entity target, EntityHitResult hitResult) {
        if (world.isClientSide) {
            return InteractionResult.PASS;
        }

        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem().equals(Items.BUCKET)) {
            if (target instanceof EnderDragonPart) {
                player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                itemstack.shrink(1);

                if (itemstack.isEmpty()) {
                    player.setItemInHand(hand, new ItemStack(ModItems.DRAGON_MILK.get()));
                }
                else if (!player.getInventory().add(new ItemStack(ModItems.DRAGON_MILK.get()))) {
                    player.drop(new ItemStack(ModItems.DRAGON_MILK.get()), false);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}

