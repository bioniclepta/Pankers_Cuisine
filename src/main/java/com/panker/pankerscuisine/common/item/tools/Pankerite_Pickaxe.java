package com.panker.pankerscuisine.common.item.tools;

import com.panker.pankerscuisine.common.tag.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Pankerite_Pickaxe extends PickaxeItem {

    public Pankerite_Pickaxe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    //Destroys deepslate blocks faster
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState blockState) {
        if(blockState.is(ModTags.PANKERITE_PICKAXE_FAST_MINE)) {
            return super.getDestroySpeed(stack, blockState) * 2;

        }
        return super.getDestroySpeed(stack, blockState);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("Mines Deepslate Faster")
                .withStyle(ChatFormatting.GRAY));
    }
}
