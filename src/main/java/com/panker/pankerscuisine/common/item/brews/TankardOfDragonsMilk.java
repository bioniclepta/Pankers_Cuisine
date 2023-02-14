package com.panker.pankerscuisine.common.item.brews;

import com.panker.pankerscuisine.common.item.DrinkableItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class TankardOfDragonsMilk extends DrinkableItem {
    public TankardOfDragonsMilk(Properties properties) {
        super(properties);
    }

    public int multiplier = 2;

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 80, 2 * multiplier));
        consumer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 6 * multiplier));
        consumer.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 25 / multiplier));
    }
}
