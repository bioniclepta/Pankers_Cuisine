package com.panker.pankerscuisine.common.block.entity.container;

import com.panker.pankerscuisine.common.block.entity.FermenterBlockEntity;
import com.panker.pankerscuisine.common.registry.ModBlocks;
import com.panker.pankerscuisine.common.registry.ModMenuTypes;
import com.panker.pankerscuisine.common.utility.BrewSetter;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class FermenterMenu extends AbstractContainerMenu {
    public final FermenterBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private final Container container;
    private String outputItemName;

    private static final int CONTAINERSLOTS = 6;
    private static final int OUTPUT_SLOT = 4;

    public FermenterMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(CONTAINERSLOTS-1), null);
    }

    public FermenterMenu(int id, Inventory inv, BlockEntity entity, ContainerData data, @Nullable Container container) {
        super(ModMenuTypes.FERMENTER_MENU.get(), id);
        checkContainerSize(inv, CONTAINERSLOTS);
        blockEntity = (FermenterBlockEntity) entity;
        this.level = inv.player.level;
        this.container = container;
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);


        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new BucketHandler(handler, 0, 34, 34));

            this.addSlot(new FermenterSlot(handler, 1, 15, 16));
            this.addSlot(new FermenterSlot(handler, 2, 15, 34));
            this.addSlot(new FermenterSlot(handler, 3, 15, 52));

            this.addSlot(new OutputHandler(handler, 4, 86,11));
            this.addSlot(new OutputHandler(handler, 5, 140,34));
        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 22; // This is the length in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public void setClicked() {
        this.data.set(2, 1); //Tells the block entity that the button has been pressed to brew
    }
    public boolean getUnclicked() {
        int unclicked = this.data.get(2); //Tells the screen when the button should be ready to be pressed again
        boolean clicked_state;
        if(unclicked == 1) {
            clicked_state = false;
        } else {
            clicked_state = true;
        }
        return clicked_state;
    }


    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = CONTAINERSLOTS;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }



    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.FERMENTER_BLOCK.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    private void setDisplay(ItemStack stack) {
        blockEntity.setItem(OUTPUT_SLOT, stack);
        this.broadcastChanges();
    }

    private void setOutput(ItemStack stack) {
        blockEntity.itemHandler.setStackInSlot(OUTPUT_SLOT + 1, stack);
        this.broadcastChanges();
    }

    private void resetOutput() {
        ItemStack stack = blockEntity.itemHandler.getStackInSlot(OUTPUT_SLOT);

        if (!stack.isEmpty()) {
            this.setOutput(ItemStack.EMPTY);
        }
    }

    public void updateOutput() {
        ItemStack baseStack = blockEntity.itemHandler.getStackInSlot(0);

        if (baseStack.isEmpty()) {
            System.out.println("Base Stack is empty, resetting output");
            resetOutput();
            return;
        }
        NonNullList<ItemStack> ingredientsList = NonNullList.create();

        for (int i = 1; i < OUTPUT_SLOT; i++) {
            //ItemStack stack = this.container.getItem(i);
            ItemStack stack = blockEntity.itemHandler.getStackInSlot(i);

            if (!stack.isEmpty()) {
                System.out.println("setting ingredient: " + stack.getItem().toString());
                ItemStack copy = stack.copy();
                copy.setCount(1);
                ingredientsList.add(copy);
            }
        }

        if (ingredientsList.isEmpty()) {
            System.out.println("Ingredient list is empty, resetting output");
            resetOutput();
            return;
        }
        ItemStack baseCopy = baseStack.copy();
        baseCopy.setCount(1);
        BrewSetter calculator = new BrewSetter(baseCopy, ingredientsList);
        ItemStack result = calculator.getResult();

        if (result.isEmpty()) {
            System.out.println("result was empty");
            resetOutput();
            return;
        }

        updateItemName("Testing naming");

        if (StringUtils.isBlank(this.outputItemName)) {
            result.resetHoverName();
        } else if (!this.outputItemName.equals(result.getHoverName().getString())) {
            result.setHoverName(Component.literal(this.outputItemName));
        }
        System.out.println("got down to updating the output");
        setOutput(result);
    }

    public void updateItemName(String newName) {
        this.outputItemName = newName;
        //this.updateOutput();
    }

    public class FermenterSlot extends SlotItemHandler {

        public FermenterSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public void set(@NotNull ItemStack stack) {
            super.set(stack);
            FermenterMenu.this.updateOutput();
        }
    }

    public class BucketHandler extends SlotItemHandler {

        private int removeCount;

        public BucketHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public void set(@NotNull ItemStack stack) {
            super.set(stack);
            FermenterMenu.this.updateOutput();
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            if(stack.getItem() == Items.BUCKET) { return true;}
            return false;
        }

        @Override
        @Nonnull
        public ItemStack remove(int amount) {
            if (this.hasItem()) {
                this.removeCount += Math.min(amount, this.getItem().getCount());
            }

            return super.remove(amount);
        }
    }
}