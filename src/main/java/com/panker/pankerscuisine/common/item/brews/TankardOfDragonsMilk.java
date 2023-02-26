package com.panker.pankerscuisine.common.item.brews;

import com.panker.pankerscuisine.common.item.DrinkableItem;
import com.panker.pankerscuisine.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class TankardOfDragonsMilk extends DrinkableItem implements IForgeItem {
    public TankardOfDragonsMilk(Properties properties) {
        super(properties);
    }


    private int multiplier = 1;


    public void setMultiplier(int value) {
        multiplier = value;
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 80 * multiplier, 2 * multiplier));
        consumer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60 * multiplier, 6 * multiplier));
        consumer.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 25 / multiplier));
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }
}
