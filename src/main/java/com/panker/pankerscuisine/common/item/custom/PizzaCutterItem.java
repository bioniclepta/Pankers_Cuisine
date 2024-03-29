package com.panker.pankerscuisine.common.item.custom;

import com.google.common.collect.Sets;
import com.panker.pankerscuisine.common.tag.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class PizzaCutterItem extends DiggerItem {

    public PizzaCutterItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(attackDamage, attackSpeed, tier, ModTags.MINEABLE_WITH_PIZZACUTTER, properties);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()) {
            components.add(Component.literal("Right click on a pizza block to get pizza slices!").withStyle(ChatFormatting.AQUA));
        } else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(stack, level, components, flag);
    }



    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.world.item.enchantment.Enchantment enchantment) {
        Set<Enchantment> ALLOWED_ENCHANTMENTS = Sets.newHashSet(Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.KNOCKBACK, Enchantments.FIRE_ASPECT, Enchantments.MOB_LOOTING);
        if (ALLOWED_ENCHANTMENTS.contains(enchantment)) {
            return true;
        }
        Set<Enchantment> DENIED_ENCHANTMENTS = Sets.newHashSet(Enchantments.BLOCK_FORTUNE);
        if (DENIED_ENCHANTMENTS.contains(enchantment)) {
            return false;
        }
        return enchantment.category.canEnchant(stack.getItem());
    }
}
