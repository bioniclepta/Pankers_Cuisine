package com.panker.pankerscuisine.common.block.entity;

import com.google.common.collect.Lists;
import com.panker.pankerscuisine.common.block.custom.BrickOvenBlock;
import com.panker.pankerscuisine.common.block.entity.container.BrickOvenItemHandler;
import com.panker.pankerscuisine.common.registry.ModBlockEntityTypes;
import com.panker.pankerscuisine.common.registry.ModItems;
import com.panker.pankerscuisine.data.recipe.BrickOvenRecipe;
import com.panker.pankerscuisine.common.registry.ModRecipeTypes;
import com.panker.pankerscuisine.common.mixin.RecipeManagerAccessor;
import com.panker.pankerscuisine.common.block.entity.container.BrickOvenMenu;
import com.panker.pankerscuisine.common.utility.ItemUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class BrickOvenBlockEntity extends SyncedBlockEntity implements MenuProvider, CampfireHeatableBlockEntity, RecipeHolder {
    public static final int FUEL_SLOT = 6;
    public static final int OUTPUT_SLOT = 7;
    public static final int INVENTORY_SIZE = OUTPUT_SLOT + 1;

    private final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> inputHandler;
    private final LazyOptional<IItemHandler> outputHandler;

    private int cookTime;
    private int cookTimeTotal;
    private ItemStack fuelContainerStack;
    private Component customName;

    protected final ContainerData brickOvenData;
    private final Object2IntOpenHashMap<ResourceLocation> usedRecipeTracker;

    private ResourceLocation lastRecipeID;
    private boolean checkNewRecipe;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public BrickOvenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.BRICK_OVEN_TILE_ENTITY.get(), pos, state);
        this.inventory = createHandler();
        this.inputHandler = LazyOptional.of(() -> new BrickOvenItemHandler(inventory, Direction.UP));
        this.outputHandler = LazyOptional.of(() -> new BrickOvenItemHandler(inventory, Direction.DOWN));
        this.fuelContainerStack = ItemStack.EMPTY;
        this.brickOvenData = createIntArray();
        this.usedRecipeTracker = new Object2IntOpenHashMap<>();
        this.checkNewRecipe = true;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BrickOvenBlockEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }
    }

    public static ItemStack getFoodFromItem(ItemStack brickOvenStack) {
        if (!brickOvenStack.is(ModItems.BRICK_OVEN.get())) {
            return ItemStack.EMPTY;
        }

        CompoundTag compound = brickOvenStack.getTagElement("BlockEntityTag");
        if (compound != null) {
            CompoundTag inventoryTag = compound.getCompound("Inventory");
            if (inventoryTag.contains("Items", 8)) {
                ItemStackHandler handler = new ItemStackHandler();
                handler.deserializeNBT(inventoryTag);
                return handler.getStackInSlot(7);
            }
        }

        return ItemStack.EMPTY;
    }


    @Override
    public Component getDisplayName() {
        return Component.literal("Brick Oven");
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("CookTime", cookTime);
        nbt.putInt("CookTimeTotal", cookTimeTotal);
        nbt.put("Fuel", fuelContainerStack.serializeNBT());
        if (customName != null) {
            nbt.putString("CustomName", Component.Serializer.toJson(customName));
        }
        nbt.put("Inventory", inventory.serializeNBT());
        CompoundTag compoundRecipes = new CompoundTag();
        usedRecipeTracker.forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
        nbt.put("RecipesUsed", compoundRecipes);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        inventory.deserializeNBT(nbt.getCompound("Inventory"));
        cookTime = nbt.getInt("CookTime");
        cookTimeTotal = nbt.getInt("CookTimeTotal");
        fuelContainerStack = ItemStack.of(nbt.getCompound("Fuel"));
        if (nbt.contains("CustomName", 7)) {
            customName = Component.Serializer.fromJson(nbt.getString("CustomName"));
        }
        CompoundTag compoundRecipes = nbt.getCompound("RecipesUsed");
        for (String key : compoundRecipes.getAllKeys()) {
            usedRecipeTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
        }
    }

    private CompoundTag writeItems(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Fuel", fuelContainerStack.serializeNBT());
        nbt.put("Inventory", inventory.serializeNBT());
        return nbt;
    }

    public CompoundTag writeFood(CompoundTag nbt) {
        if (getFood().isEmpty()) return nbt;

        ItemStackHandler drops = new ItemStackHandler(INVENTORY_SIZE);
        for (int i = 0; i < INVENTORY_SIZE; ++i) {
            drops.setStackInSlot(i, i == OUTPUT_SLOT ? inventory.getStackInSlot(i) : ItemStack.EMPTY);
        }
        if (customName != null) {
            nbt.putString("CustomName", Component.Serializer.toJson(customName));
        }
        nbt.put("Fuel", fuelContainerStack.serializeNBT());
        nbt.put("Inventory", drops.serializeNBT());
        return nbt;
    }

    public static void cookingTick(Level level, BlockPos pos, BlockState state, BrickOvenBlockEntity brickOven) {
        boolean isHeated = brickOven.isHeated(level, pos);
        boolean didInventoryChange = false;

        if (isHeated && brickOven.hasInput()) {
            Optional<BrickOvenRecipe> recipe = brickOven.getMatchingRecipe(new RecipeWrapper(brickOven.inventory));
            if (recipe.isPresent() && brickOven.canCook(recipe.get())) {
                didInventoryChange = brickOven.processCooking(recipe.get(), brickOven);
            } else {
                brickOven.cookTime = 0;
            }
        } else if (brickOven.cookTime > 0) {
            brickOven.cookTime = Mth.clamp(brickOven.cookTime - 2, 0, brickOven.cookTimeTotal);
        }

        ItemStack mealStack = brickOven.getFood();

        if (didInventoryChange) {
            brickOven.inventoryChanged();
        }
    }

    private Optional<BrickOvenRecipe> getMatchingRecipe(RecipeWrapper inventoryWrapper) {
        if (level == null) return Optional.empty();

        if (lastRecipeID != null) {
            Recipe<RecipeWrapper> recipe = ((RecipeManagerAccessor) level.getRecipeManager())
                    .getRecipeMap(ModRecipeTypes.BRICK_OVEN_COOKING.get())
                    .get(lastRecipeID);
            if (recipe instanceof BrickOvenRecipe) {
                if (recipe.matches(inventoryWrapper, level)) {
                    return Optional.of((BrickOvenRecipe) recipe);
                }
                if (recipe.getResultItem().sameItem(getFood())) {
                    return Optional.empty();
                }
            }
        }

        if (checkNewRecipe) {
            Optional<BrickOvenRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.BRICK_OVEN_COOKING.get(), inventoryWrapper, level);
            if (recipe.isPresent()) {
                lastRecipeID = recipe.get().getId();
                return recipe;
            }
        }

        checkNewRecipe = false;
        return Optional.empty();
    }

    public ItemStack getContainer() {
        if (!fuelContainerStack.isEmpty()) {
            return fuelContainerStack;
        } else {
            return getFood().getCraftingRemainingItem();
        }
    }

    private boolean hasInput() {
        for (int i = 0; i < OUTPUT_SLOT; ++i) {
            if (!inventory.getStackInSlot(i).isEmpty()) return true;
        }
        return false;
    }

    protected boolean canCook(BrickOvenRecipe recipe) {
        if (hasInput()) {
            ItemStack resultStack = recipe.getResultItem();
            if (resultStack.isEmpty()) {
                return false;
            } else {
                ItemStack storedMealStack = inventory.getStackInSlot(OUTPUT_SLOT);
                if (storedMealStack.isEmpty()) {
                    return true;
                } else if (!storedMealStack.sameItem(resultStack)) {
                    return false;
                } else if (storedMealStack.getCount() + resultStack.getCount() <= inventory.getSlotLimit(OUTPUT_SLOT)) {
                    return true;
                } else {
                    return storedMealStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private boolean processCooking(BrickOvenRecipe recipe, BrickOvenBlockEntity brickOven) {
        if (level == null) return false;

        ++cookTime;
        cookTimeTotal = recipe.getCookTime();
        if (cookTime < cookTimeTotal) {
            return false;
        }

        cookTime = 0;
        fuelContainerStack = recipe.getOutputContainer();
        ItemStack resultStack = recipe.getResultItem();
        ItemStack storedMealStack = inventory.getStackInSlot(OUTPUT_SLOT);
        if (storedMealStack.isEmpty()) {
            inventory.setStackInSlot(OUTPUT_SLOT, resultStack.copy());
        } else if (storedMealStack.sameItem(resultStack)) {
            storedMealStack.grow(resultStack.getCount());
        }
        brickOven.setRecipeUsed(recipe);

        for (int i = 0; i < OUTPUT_SLOT; ++i) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            if (slotStack.hasCraftingRemainingItem()) {
                Direction direction = getBlockState().getValue(BrickOvenBlock.FACING).getCounterClockWise();
                double x = worldPosition.getX() + 0.5 + (direction.getStepX() * 0.25);
                double y = worldPosition.getY() + 0.7;
                double z = worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.25);
                ItemUtils.spawnItemEntity(level, inventory.getStackInSlot(i).getCraftingRemainingItem(), x, y, z,
                        direction.getStepX() * 0.08F, 0.25F, direction.getStepZ() * 0.08F);
            }
            if (!slotStack.isEmpty())
                slotStack.shrink(1);
        }
        return true;
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation recipeID = recipe.getId();
            usedRecipeTracker.addTo(recipeID, 1);
        }
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player) {
        List<Recipe<?>> usedRecipes = getUsedRecipesAndPopExperience(player.level, player.position());
        player.awardRecipes(usedRecipes);
        usedRecipeTracker.clear();
    }

    public List<Recipe<?>> getUsedRecipesAndPopExperience(Level level, Vec3 pos) {
        List<Recipe<?>> list = Lists.newArrayList();

        for (Object2IntMap.Entry<ResourceLocation> entry : usedRecipeTracker.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                splitAndSpawnExperience((ServerLevel) level, pos, entry.getIntValue(), ((BrickOvenRecipe) recipe).getExperience());
            });
        }

        return list;
    }

    private static void splitAndSpawnExperience(ServerLevel level, Vec3 pos, int craftedAmount, float experience) {
        int expTotal = Mth.floor((float) craftedAmount * experience);
        float expFraction = Mth.frac((float) craftedAmount * experience);
        if (expFraction != 0.0F && Math.random() < (double) expFraction) {
            ++expTotal;
        }

        ExperienceOrb.award(level, pos, expTotal);
    }

    public boolean isHeated() {
        if (level == null) return false;
        return this.isHeated(level, worldPosition);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public ItemStack getFood() {
        return inventory.getStackInSlot(OUTPUT_SLOT);
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < INVENTORY_SIZE; ++i) {
            if (i != OUTPUT_SLOT) {
                drops.add(inventory.getStackInSlot(i));
            }
        }
        return drops;
    }

    public boolean isFuelValid(ItemStack fuelItem) {
        if (fuelItem.isEmpty()) return false;
        if (!fuelContainerStack.isEmpty()) {
            return fuelContainerStack.sameItem(fuelItem);
        } else {
            return getFood().getCraftingRemainingItem().sameItem(fuelItem);
        }
    }

    public void setCustomName(Component name) {
        customName = name;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
        return new BrickOvenMenu(id, player, this, brickOvenData);
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
            if (side == null || side.equals(Direction.UP)) {
                return inputHandler.cast();
            } else {
                return outputHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        inputHandler.invalidate();
        outputHandler.invalidate();
    }

    @Override
    public CompoundTag getUpdateTag() {
        return writeItems(new CompoundTag());
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(INVENTORY_SIZE)
        {
            @Override
            protected void onContentsChanged(int slot) {
                if (slot >= 0 && slot < OUTPUT_SLOT) {
                    checkNewRecipe = true;
                }
                inventoryChanged();
            }
        };
    }

    private ContainerData createIntArray() {
        return new ContainerData()
        {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BrickOvenBlockEntity.this.cookTime;
                    case 1 -> BrickOvenBlockEntity.this.cookTimeTotal;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BrickOvenBlockEntity.this.cookTime = value;
                    case 1 -> BrickOvenBlockEntity.this.cookTimeTotal = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }
}
