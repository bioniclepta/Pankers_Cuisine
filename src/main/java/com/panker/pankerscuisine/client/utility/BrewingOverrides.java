package com.panker.pankerscuisine.client.utility;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.utility.BrewNBT;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public abstract class BrewingOverrides<T extends IUnbakedGeometry<T>> extends ItemOverrides {
    protected final T model;
    protected final ModelBakery bakery;
    protected final IGeometryBakingContext context;
    protected final Function<Material, TextureAtlasSprite> spriteGetter;
    protected final ModelState modelState;
    protected final ResourceLocation modelLocation;

    private final Cache<CacheKey, BakedModel> bakedModelCache =
            CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(5, TimeUnit.MINUTES).build();

    public BrewingOverrides(T model, IGeometryBakingContext context, ModelBakery bakery,
                             Function<Material, TextureAtlasSprite> spriteGetter,
                             ModelState modelState,
                             ResourceLocation modelLocation) {
        super();
        this.model = model;
        this.context = context;
        this.bakery = bakery;
        this.spriteGetter = spriteGetter;
        this.modelLocation = modelLocation;
        this.modelState = modelState;
    }

    @Nonnull
    @Override
    public BakedModel resolve(@Nonnull BakedModel originalModel, @Nonnull ItemStack stack,
                              @Nullable ClientLevel level, @Nullable LivingEntity entity, int pSeed) {
        CompoundTag data = BrewNBT.getTagSafe(stack);
        BakedModel output = originalModel;

        if (!data.isEmpty()) {
            CacheKey key = new CacheKey(originalModel, stack);
            try {
                output = bakedModelCache.get(key, () -> getBakedModel(originalModel, stack, level, entity));
            } catch (ExecutionException e) {
                Pankers_Cuisine.LOGGER.error("Error baking model!");
            }
        }
        return output;
    }

    protected abstract BakedModel getBakedModel(BakedModel originalModel, ItemStack stack,
                                                @Nullable Level world, @Nullable LivingEntity entity);

    public static class CacheKey {

        final BakedModel parent;
        final CompoundTag data;

        public CacheKey(BakedModel parent, ItemStack stack) {
            this.parent = parent;
            this.data = BrewNBT.getTagSafe(stack);
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CacheKey cacheKey = (CacheKey) o;

            if (this.parent != null ? this.parent != cacheKey.parent : cacheKey.parent != null) {
                return false;
            }
            return Objects.equals(this.data, cacheKey.data);
        }

        @Override
        public int hashCode() {
            int result = this.parent != null ? this.parent.hashCode() : 0;
            result = 31 * result + (this.data != null ? this.data.hashCode() : 0);
            return result;
        }
    }
}
