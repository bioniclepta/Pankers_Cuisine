package com.panker.pankerscuisine.block.custom;

import com.panker.pankerscuisine.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


import java.util.function.Supplier;

public class PizzaWholeBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public final Supplier<Item> sliceType;
    public final Supplier<Item> pizzaType;

    public PizzaWholeBlock(Properties properties, Supplier<Item> sliceType, Supplier<Item> pizzaType) {
        super(properties);
        this.sliceType = sliceType;
        this.pizzaType = pizzaType;
    }

    private static final VoxelShape SHAPE =
            Block.box(1.5, 0, 1.5, 14.5, 5, 14.5);

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public ItemStack getPizzaItem(BlockState state) {
        return new ItemStack(this.pizzaType.get());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(level.isClientSide) {
            if(this.slicePizza(level, pos, state, player, hand).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
        }
        return this.slicePizza(level, pos, state, player, hand);
    }

    protected InteractionResult slicePizza(Level level, BlockPos pos, BlockState state, Player player, InteractionHand hand) {
        ItemStack pizza = this.getPizzaItem(state);
        ItemStack heldStack = player.getItemInHand(hand);

        if(heldStack.is(ModTags.PIZZACUTTERS)) {

            for(var i = 0; i < 12; i++) {
                popResource(level, pos, getCloneItemStack(level, pos, state));
            }

            level.removeBlock(pos, false);
            level.playSound(null, pos, SoundEvents.SLIME_BLOCK_BREAK, SoundSource.BLOCKS, 1f, 1f);
            return InteractionResult.SUCCESS;
        } else {
            if(!player.getInventory().add(pizza)) {
                player.drop(pizza, false);
            }
            level.removeBlock(pos, false);
            level.playSound(null, pos, SoundEvents.SLIME_BLOCK_BREAK, SoundSource.BLOCKS, 1f, 1f);
            return InteractionResult.SUCCESS;
        }

    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(this.sliceType.get());
    }
}
