package com.panker.pankerscuisine.common.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
@SuppressWarnings("uses unchecked or unsafe operations")
public class BaseFoodTruckEntity extends AbstractVillager implements GeoEntity {

    private AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    public static final int VILLAGER_SLOT_OFFSET = 300;
    private static final int VILLAGER_INVENTORY_SIZE = 8;
    @javax.annotation.Nullable
    private Player tradingPlayer;
    @javax.annotation.Nullable
    protected MerchantOffers offers;
    private final SimpleContainer inventory = new SimpleContainer(8);


    public BaseFoodTruckEntity(EntityType<? extends AbstractVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
    }

    public static AttributeSupplier setAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.25D).build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isAlive() && !this.isTrading()) {
            if (hand == InteractionHand.MAIN_HAND) {
                player.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (!this.getOffers().isEmpty()) {
                if (!this.level.isClientSide) {
                    this.setTradingPlayer(player);
                    this.openTradingScreen(player, this.getDisplayName(), 1);
                }

            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public MerchantOffers getOffers() {
        return super.getOffers();
    }

    public void die(DamageSource damageSource) {
        super.die(damageSource);
        this.stopTrading();
        BlockPos blockpos = this.blockPosition();
        Level level = this.level;
        EntityType<?> entityType = EntityType.VILLAGER;
        if(!level.isClientSide()) {
            Entity entity = entityType.spawn((ServerLevel) level, blockpos, MobSpawnType.TRIGGERED);
        }
        level.gameEvent(damageSource.getEntity(), GameEvent.ENTITY_PLACE, blockpos);
    }

    @Override
    protected void updateTrades() {

    }

    @Override
    protected void rewardTradeXp(MerchantOffer pOffer) {
        if (pOffer.shouldRewardExp()) {
            int i = 3 + this.random.nextInt(4);
            this.level.addFreshEntity(new ExperienceOrb(this.level, this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }
    }

    static class ItemsForEmeralds implements VillagerTrades.ItemListing {
        private final ItemStack itemStack;
        private final int emeraldCost;
        private final int numberOfItems;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsForEmeralds(Block block, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
            this(new ItemStack(block), emeraldCost, numberOfItems, maxUses, villagerXp);
        }

        public ItemsForEmeralds(Item item, int emeraldCost, int numberOfItems, int villagerXp) {
            this(new ItemStack(item), emeraldCost, numberOfItems, 12, villagerXp);
        }

        public ItemsForEmeralds(Item item, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
            this(new ItemStack(item), emeraldCost, numberOfItems, maxUses, villagerXp);
        }

        public ItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
            this(itemStack, emeraldCost, numberOfItems, maxUses, villagerXp, 0.05F);
        }

        public ItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp, float priceMultiplier) {
            this.itemStack = itemStack;
            this.emeraldCost = emeraldCost;
            this.numberOfItems = numberOfItems;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity p_219699_, RandomSource p_219700_) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost),
                    new ItemStack(this.itemStack.getItem(), this.numberOfItems), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    private PlayState predicate(AnimationState animationState) {
        if(animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("animation.truck.rolling", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        animationState.getController().setAnimation(RawAnimation.begin().then("animation.truck.deflate", Animation.LoopType.HOLD_ON_LAST_FRAME));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.MINECART_RIDING, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.VILLAGER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }


}
