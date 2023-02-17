package com.panker.pankerscuisine.common.block.entity;

import com.panker.pankerscuisine.common.block.entity.container.DistilleryMenu;
import com.panker.pankerscuisine.common.registry.ModBlockEntities;;
import com.panker.pankerscuisine.data.recipe.DistilleryRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DistilleryBlockEntity extends BlockEntity implements MenuProvider {
    private static final int CONTAINER_SLOTS = 2;
    private static final int OUTPUT_SLOT = 1;

    private final ItemStackHandler itemHandler = new ItemStackHandler(CONTAINER_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    //1 second is 20 ticks
    //1 day is 24000 ticks
    protected final ContainerData data;
    private int progress = 0;
    private int day1 = 24000;
    private int day2 = day1 * 2;
    private int day3 = day1 * 3;
    private int day4 = day1 * 4;
    private int day5 = day1 * 5;
    private int day6 = day1 * 6;
    private int day7 = day1 * 7;
    private int maxProgress = day7;

    public DistilleryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DISTILLERY_TILE_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> DistilleryBlockEntity.this.progress;
                    case 1 -> DistilleryBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> DistilleryBlockEntity.this.progress = value;
                    case 1 -> DistilleryBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Distillery");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new DistilleryMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("distillery.progress", this.progress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("distillery.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, DistilleryBlockEntity pEntity) {
        if(level.isClientSide()) {
            return;
        }

        if(hasRecipe(pEntity) || pEntity.itemHandler.getStackInSlot(OUTPUT_SLOT).getDamageValue() > 0) {
            pEntity.progress++;
            setChanged(level, pos, state);
            brewItem(pEntity);
            //if(pEntity.progress >= pEntity.maxProgress) {
            //    brewItem(pEntity);
            //}
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void brewItem(DistilleryBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        ItemStack output_brew = pEntity.itemHandler.getStackInSlot(OUTPUT_SLOT);
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<DistilleryRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(DistilleryRecipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(pEntity)) {
            pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.itemHandler.setStackInSlot(0, new ItemStack(Items.BUCKET, 1));


            pEntity.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(recipe.get().getResultItem().getItem(),
                    pEntity.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + 4));

            output_brew = pEntity.itemHandler.getStackInSlot(OUTPUT_SLOT);

            output_brew.setDamageValue(7);

        } else if(output_brew.isRepairable() && output_brew.getDamageValue() > 0) {
            switch (pEntity.progress) {
                case 24000: output_brew.setDamageValue(Math.max(0, output_brew.getDamageValue() - 1));
                case 48000: output_brew.setDamageValue(Math.max(0, output_brew.getDamageValue() - 1));
                case 72000: output_brew.setDamageValue(Math.max(0, output_brew.getDamageValue() - 1));
                case 96000: output_brew.setDamageValue(Math.max(0, output_brew.getDamageValue() - 1));
                case 120000: output_brew.setDamageValue(Math.max(0, output_brew.getDamageValue() - 1));
                case 144000: output_brew.setDamageValue(Math.max(0, output_brew.getDamageValue() - 1));
                case 168000: output_brew.setDamageValue(Math.max(0, output_brew.getDamageValue() - 1));
            }

        } else if(!output_brew.isRepairable() || output_brew.getDamageValue() == 0) {
            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(DistilleryBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<DistilleryRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(DistilleryRecipe.Type.INSTANCE, inventory, level);


        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem());
    }

    //CHECK NEW
    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(OUTPUT_SLOT).getItem() == stack.getItem() || inventory.getItem(OUTPUT_SLOT).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(OUTPUT_SLOT).getMaxStackSize() > inventory.getItem(OUTPUT_SLOT).getCount();
    }
}