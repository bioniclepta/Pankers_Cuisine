package com.panker.pankerscuisine.common.block.entity;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.block.entity.container.FermenterMenu;
import com.panker.pankerscuisine.common.item.brews.BrewItem;
import com.panker.pankerscuisine.common.registry.ModBlockEntities;
import com.panker.pankerscuisine.common.registry.ModItems;
import com.panker.pankerscuisine.common.utility.BrewNBT;
import com.panker.pankerscuisine.common.utility.BrewSetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class FermenterBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible {

    private static final int[] SLOTS_FOR_UP = new int[] {0};
    private static final int[] SLOTS_FOR_DOWN = new int[] {5};
    private static final int[] SLOTS_FOR_SIDES = new int[] {0, 1, 2, 3};

    public final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 20; //24000
    private boolean craftPressed = false;
    private boolean finishedCrafting = false;

    protected NonNullList<ItemStack> items;

    public FermenterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FERMENTER_TILE_ENTITY.get(), pos, state);
        this.items = NonNullList.withSize(6, ItemStack.EMPTY);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FermenterBlockEntity.this.progress;
                    case 1 -> FermenterBlockEntity.this.maxProgress;

                    case 2 -> FermenterBlockEntity.this.craftPressed ? 1 : 0;
                    case 3 -> FermenterBlockEntity.this.finishedCrafting ? 1 : 0;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FermenterBlockEntity.this.progress = value;
                    case 1 -> FermenterBlockEntity.this.maxProgress = value;

                    case 2 -> FermenterBlockEntity.this.craftPressed = true;
                    case 3 -> FermenterBlockEntity.this.craftPressed = false;

                    case 4 -> FermenterBlockEntity.this.finishedCrafting = true;
                    case 5 -> FermenterBlockEntity.this.finishedCrafting = false;
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
        return Component.literal("Fermenter");
    }

    @Nonnull
    @Override
    protected Component getDefaultName() {
        return Component.translatable(
                "container." + Pankers_Cuisine.MOD_ID + ".fermenter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new FermenterMenu(id, inventory, this, this.data, null);
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
        ContainerHelper.saveAllItems(nbt, this.items);
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("fermenter.progress", this.progress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        ContainerHelper.loadAllItems(nbt, this.items);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("fermenter.progress");
    }

    @Nonnull
    @Override
    public int[] getSlotsForFace(@Nonnull Direction side) {

        if (side == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @Nonnull ItemStack stack,
                                           @Nullable Direction side) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @Nonnull ItemStack stack,
                                          @Nonnull Direction side) {
        return false;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FermenterBlockEntity pEntity) {
        if(level.isClientSide()) {
            return;
        }
        ItemStack in0 = pEntity.itemHandler.getStackInSlot(0);
        ItemStack in1 = pEntity.itemHandler.getStackInSlot(1);
        ItemStack in2 = pEntity.itemHandler.getStackInSlot(2);
        ItemStack in3 = pEntity.itemHandler.getStackInSlot(3);
        NonNullList<ItemStack> ingredients = NonNullList.create();
        ingredients.add(in1);
        ingredients.add(in2);
        ingredients.add(in3);


/*
        if (in0.getItem() == Items.BUCKET
            && !in1.isEmpty()
            && !in2.isEmpty()
            && !in3.isEmpty()
        ) {
            BrewSetter brewSetter = new BrewSetter(in0, ingredients);
            ItemStack output = brewSetter.getResult();
            pEntity.itemHandler.setStackInSlot(4, output);

            ItemStack newBrew = new ItemStack(ModItems.CUSTOM_BREW.get());
            BrewNBT.setBase(newBrew, in0);
            BrewNBT.setIngredientsList(newBrew, ingredients);

            pEntity.itemHandler.setStackInSlot(4, newBrew);


        }
        */

        if(!pEntity.getItem(4).isEmpty() && pEntity.craftPressed) {
            pEntity.progress++;
            setChanged(level, pos, state);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(FermenterBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        if(!pEntity.getItem(4).isEmpty()) {
            pEntity.itemHandler.extractItem(0, 1, false);
            pEntity.itemHandler.extractItem(1, 1, false);
            pEntity.itemHandler.extractItem(2, 1, false);
            pEntity.itemHandler.extractItem(3, 1, false);
            //pEntity.itemHandler.extractItem(4, 1, false);

            pEntity.finishedCrafting = true;
        }
    }

    //CHECK NEW
    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(5).getItem() == stack.getItem() || inventory.getItem(5).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(5).getMaxStackSize() > inventory.getItem(5).getCount();
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {

        for (ItemStack itemstack : this.items) {

            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }


    @Nonnull
    @Override
    public ItemStack getItem(int index) {
        return this.items.get(index);
    }

    @Nonnull
    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.items, index, count);
    }

    @Nonnull
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.items, index);
    }

    @Override
    public void setItem(int index, @Nonnull ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag =
                !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
        this.items.set(index, stack);

        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (!flag) {
            this.setChanged();
        }
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {

        if (this.level != null && this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D,
                    (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <=
                    64.0D;
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void fillStackedContents(@Nonnull StackedContents stackedContents) {

        for (ItemStack itemstack : this.items) {
            stackedContents.accountStack(itemstack);
        }
    }
}