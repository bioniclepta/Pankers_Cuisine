package com.panker.pankerscuisine.common.block.entity.container;

import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.registry.ModBlocks;
import com.panker.pankerscuisine.common.block.entity.BrickOvenBlockEntity;
import com.panker.pankerscuisine.common.block.entity.container.BrickOvenOutputHandler;
import com.panker.pankerscuisine.common.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class BrickOvenMenu extends RecipeBookMenu<RecipeWrapper> {
    public final BrickOvenBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    public final ItemStackHandler inventory;

    public BrickOvenMenu(int id, Inventory inv, FriendlyByteBuf extractData) {
        //SimpleContainerData number has to match brickovenblockentity getCount value
        this(id, inv, inv.player.level.getBlockEntity(extractData.readBlockPos()), new SimpleContainerData(7));
    }

    public BrickOvenMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.BRICK_OVEN_MENU.get(), id);
        checkContainerSize(inv, 9);
        blockEntity = (BrickOvenBlockEntity) entity;
        this.level = inv.player.level;
        this.data = data;
        this.inventory = blockEntity.getInventory();

        // Ingredient Slots - 2 Rows x 3 Columns
        int startX = 8;
        int startY = 18;
        int inputStartX = 26;
        int inputStartY = 17;
        int borderSlotSize = 18;

        for (int row = 0; row < 2; ++row) {
            for (int column = 0; column < 3; ++column) {
                this.addSlot(new SlotItemHandler(inventory, (row * 3) + column,
                        inputStartX + (column * borderSlotSize),
                        inputStartY + (row * borderSlotSize)));
            }
        }



        this.addSlot(new SlotItemHandler(inventory, 6, 89, 63));
        this.addSlot(new BrickOvenOutputHandler(inv.player, blockEntity, inventory, 7, 118, 23));

        addPlayerInventory(inv);
        addPlayerHotBar(inv);


        this.addDataSlots(data);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, column + (row * 9) + 9, 8 + (column * 18), 84 + (row * 18) ));
            }
        }
    }

    private void addPlayerHotBar(Inventory playerInventory) {
        for(int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 22; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    //Credit goes to farmers delight team for the reference
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        int indexFuelInput = 6;
        int indexOutput = 7;
        int startPlayerInv = indexOutput + 1;
        int endPlayerInv = startPlayerInv + 36;
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == indexOutput) {
                if (!this.moveItemStackTo(itemstack1, startPlayerInv, endPlayerInv, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index > indexOutput) {
                if (itemstack1.getItem() == Items.COAL && !this.moveItemStackTo(itemstack1, indexFuelInput, indexFuelInput + 1, false)) {
                    return ItemStack.EMPTY;
                } else if (!this.moveItemStackTo(itemstack1, 0, indexFuelInput, false)) {
                    return ItemStack.EMPTY;
                } else if (!this.moveItemStackTo(itemstack1, indexFuelInput, indexOutput, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, startPlayerInv, endPlayerInv, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookProgressionScaled() {
        int i = this.data.get(0);
        int j = this.data.get(1);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isHeated() {
        return blockEntity.isHeated();
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents helper) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            helper.accountSimpleStack(inventory.getStackInSlot(i));
        }
    }

    @Override
    public void clearCraftingContent() {
        for (int i = 0; i < 6; i++) {
            this.inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public boolean recipeMatches(Recipe<? super RecipeWrapper> recipe) {
        return recipe.matches(new RecipeWrapper(inventory), level);
    }

    @Override
    public int getResultSlotIndex() {
        return 7;
    }

    @Override
    public int getGridWidth() {
        return 3;
    }

    @Override
    public int getGridHeight() {
        return 2;
    }

    @Override
    public int getSize() {
        return 7;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return Pankers_Cuisine.RECIPE_TYPE_BRICK_OVEN_COOKING;
    }

    @Override
    public boolean shouldMoveToInventory(int slot) {
        return slot < (getGridWidth() * getGridHeight());
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.BRICK_OVEN_BLOCK.get());
    }

}
