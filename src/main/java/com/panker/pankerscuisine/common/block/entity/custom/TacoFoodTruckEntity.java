package com.panker.pankerscuisine.common.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class TacoFoodTruckEntity extends BaseFoodTruckEntity{
    public TacoFoodTruckEntity(EntityType<? extends AbstractVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public void die(DamageSource damageSource) {
        super.die(damageSource);
        this.stopTrading();
        BlockPos blockpos = this.blockPosition();
        Level level = this.level;
        EntityType<?> entitytype = EntityType.VILLAGER;
        if(!level.isClientSide()) {
            Entity entity = entitytype.spawn((ServerLevel) level, blockpos, MobSpawnType.TRIGGERED);
        }
        level.gameEvent(damageSource.getEntity(), GameEvent.ENTITY_PLACE, blockpos);
    }


}
