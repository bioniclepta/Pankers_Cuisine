package com.panker.pankerscuisine.common.item;

import com.mojang.blaze3d.platform.InputConstants;
import com.panker.pankerscuisine.common.utility.BrewNBT;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseTacoItem extends Item {

    private final String description;
    private final MobEffectInstance instance;
    private final boolean hasGlint;
    private final int explodeBool;

    public BaseTacoItem(Properties properties) {
        super(properties);
        this.description = "";
        this.instance = new MobEffectInstance(MobEffects.GLOWING, 0, 0);
        this.hasGlint = false;
        this.explodeBool = 0;
    }

    public BaseTacoItem(Properties properties, String description, boolean hasGlint) {
        super(properties);
        this.description = description;
        this.instance = new MobEffectInstance(MobEffects.GLOWING, 0, 0);
        this.hasGlint = hasGlint;
        this.explodeBool = 0;
    }

    public BaseTacoItem(Properties properties, String description, MobEffectInstance effectInstance) {
        super(properties);
        this.description = description;
        this.instance = effectInstance;
        this.hasGlint = false;
        this.explodeBool = 0;
    }

    public BaseTacoItem(Properties properties, String description, MobEffectInstance effectInstance, boolean hasGlint, int explodeBool) {
        super(properties);
        this.description = description;
        this.instance = effectInstance;
        this.hasGlint = hasGlint;
        this.explodeBool = explodeBool;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.hasGlint;
    }

    protected void explode(Level level, LivingEntity player) {
        float f = 4.0F;
        level.explode((Entity) player, player.getX(), player.getY(0.0625D), player.getZ(), 4.0F, Level.ExplosionInteraction.TNT);
    }
    protected void charged_explode(Level level, LivingEntity player) {
        float f = 4.0F;
        level.explode((Entity) player, player.getX(), player.getY(0.0625D), player.getZ(), 8.0F, Level.ExplosionInteraction.TNT);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {
            this.affectConsumer(stack, level, consumer);
            if(this.explodeBool == 1) {
                this.explode(consumer.level, consumer);
            } else if(this.explodeBool == 2) {
                this.charged_explode(consumer.level, consumer);
            }
        }

        ItemStack containerStack = stack.getCraftingRemainingItem();

        if (stack.isEdible()) {
            super.finishUsingItem(stack, level, consumer);
        } else {
            Player player = consumer instanceof Player ? (Player) consumer : null;
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
            }
            if (player != null) {
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
        }

        if (stack.isEmpty()) {
            return containerStack;
        } else {
            if (consumer instanceof Player player && !((Player) consumer).getAbilities().instabuild) {
                if (!player.getInventory().add(containerStack)) {
                    player.drop(containerStack, false);
                }
            }
            return stack;
        }
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.addEffect(this.instance);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable(this.description).withStyle(ChatFormatting.GRAY));
        if(explodeBool == 1 || explodeBool == 2) {
            tooltip.add(Component.translatable("Probably don't eat this anywhere important").withStyle(ChatFormatting.RED));
        }
    }
}
