package com.panker.pankerscuisine.client.utility;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.utility.BrewNBT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.model.CompositeModel;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;
import net.minecraftforge.client.model.geometry.StandaloneGeometryBakingContext;
import net.minecraftforge.client.model.geometry.UnbakedGeometryHelper;
import net.minecraft.client.resources.model.Material;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class BrewGeometry implements IUnbakedGeometry<BrewGeometry> {
    private final List<TextureAtlasSprite> ingredients;
    private final List<Integer> layers;
    private final List<Integer> liquids;

    public BrewGeometry() {
        this(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    private BrewGeometry(List<TextureAtlasSprite> ingredients, List<Integer> layers,
                         List<Integer> liquids) {
        this.ingredients = ingredients;
        this.layers = layers;
        this.liquids = liquids;
    }

    private BrewGeometry withStack(ItemStack stack) {
        ImmutableList.Builder<TextureAtlasSprite> builder = ImmutableList.builder();
        List<ItemStack> solids = BrewNBT.getSolids(stack);

        for (ItemStack ing : solids) {
            builder.add(Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(ing)
                    .getParticleIcon(ModelData.EMPTY));
        }
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < solids.size(); i++) {
            list.add(i);
        }
        List<Integer> liquids = BrewNBT.getLiquids(stack);
        return new BrewGeometry(builder.build(), list, liquids);
    }

    public static RenderTypeGroup getLayerRenderTypes() {
        return new RenderTypeGroup(RenderType.translucent(),
                ForgeRenderTypes.ITEM_UNSORTED_TRANSLUCENT.get());
    }

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBakery bakery,
                           Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState,
                           ItemOverrides overrides, ResourceLocation modelLocation) {
        System.out.println("Working to bake");
        TextureAtlasSprite baseSprite = spriteGetter.apply(context.getMaterial("bowl"));
        StandaloneGeometryBakingContext itemContext =
                StandaloneGeometryBakingContext.builder(context).withGui3d(false).withUseBlockLight(false)
                        .build(modelLocation);
        CompositeModel.Baked.Builder modelBuilder =
                CompositeModel.Baked.builder(itemContext, baseSprite,
                        new BakedBrewOverrides(this, context, bakery, spriteGetter,
                                modelState, modelLocation), context.getTransforms());
        List<Integer> ingredientColors = new ArrayList<>();
        this.ingredients.forEach(
                sprite -> ingredientColors.add(ColorMixer.getDominantColor(sprite)));
        List<BlockElement> unbaked = UnbakedGeometryHelper.createUnbakedItemElements(0, baseSprite);
        List<BakedQuad> quads =
                UnbakedGeometryHelper.bakeElements(unbaked, material -> baseSprite, modelState,
                        modelLocation);
        RenderTypeGroup normalRenderTypes = getLayerRenderTypes();
        modelBuilder.addQuads(normalRenderTypes, quads);
        ColoredQuadTransformer coloredQuadTransformer = new ColoredQuadTransformer();

        if (this.liquids != null) {
            List<Integer> opaqueColors = new ArrayList<>();
            this.liquids.forEach(color -> {
                if (color != null) {
                    opaqueColors.add(color);
                }
            });
            boolean isOpaque = !opaqueColors.isEmpty();
            int liquidColor = isOpaque ? ColorMixer.getMixedColor(this.liquids)
                    : ColorMixer.getMixedColor(ingredientColors);
            TextureAtlasSprite sprite = spriteGetter.apply(context.getMaterial("brew_contents"));
            unbaked = UnbakedGeometryHelper.createUnbakedItemElements(0, sprite);
            quads = UnbakedGeometryHelper.bakeElements(unbaked, material -> sprite, modelState,
                    modelLocation);
            coloredQuadTransformer.color(quads, liquidColor);
            modelBuilder.addQuads(normalRenderTypes, quads);

            if (ingredients.size() >= 3) {
                TextureAtlasSprite overflowSprite =
                        spriteGetter.apply(context.getMaterial("liquid_overflow"));
                unbaked = UnbakedGeometryHelper.createUnbakedItemElements(0, overflowSprite);
                quads = UnbakedGeometryHelper.bakeElements(unbaked, material -> overflowSprite, modelState,
                        modelLocation);
                coloredQuadTransformer.color(quads, liquidColor);
                modelBuilder.addQuads(normalRenderTypes, quads);
            }
        }

        for (int i = 0; i < this.ingredients.size(); i++) {
            TextureAtlasSprite sprite =
                    spriteGetter.apply(context.getMaterial("layer" + this.layers.get(i)));
            unbaked = UnbakedGeometryHelper.createUnbakedItemElements(0, sprite);
            quads = UnbakedGeometryHelper.bakeElements(unbaked, material -> sprite, modelState,
                    modelLocation);
            coloredQuadTransformer.color(quads, ingredientColors.get(i));
            modelBuilder.addQuads(normalRenderTypes, quads);
        }
        modelBuilder.setParticle(baseSprite);
        return modelBuilder.build();
    }

    @Override
    public Collection<Material> getMaterials(IGeometryBakingContext context,
                                             Function<ResourceLocation, UnbakedModel> modelGetter,
                                             Set<Pair<String, String>> missingTextureErrors) {
        Set<Material> textures = new HashSet<>();
        System.out.println("getting materials");
        
        textures.add(context.getMaterial("brew_contents"));
        textures.add(context.getMaterial("bucket"));
        return textures;
    }

    private static final class BakedBrewOverrides extends
            BrewingOverrides<BrewGeometry> {

        public BakedBrewOverrides(BrewGeometry model, IGeometryBakingContext context,
                                  ModelBakery bakery,
                                  Function<Material, TextureAtlasSprite> spriteGetter,
                                  ModelState modelState, ResourceLocation modelLocation) {
            super(model, context, bakery, spriteGetter, modelState, modelLocation);
        }

        @Override
        protected BakedModel getBakedModel(BakedModel originalModel, ItemStack stack,
                                           @Nullable Level world, @Nullable LivingEntity entity) {
            System.out.println("getting baked model");
            BrewGeometry unbaked = this.model.withStack(stack);
            return unbaked.bake(this.context, this.bakery, this.spriteGetter, this.modelState,
                    this, new ResourceLocation(Pankers_Cuisine.MOD_ID,
                            Pankers_Cuisine.BREW_ID));
        }
    }
}
