package com.panker.pankerscuisine.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.block.entity.container.BrickOvenMenu;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BrickOvenScreen extends AbstractContainerScreen<BrickOvenMenu> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Pankers_Cuisine.MOD_ID, "textures/gui/brick_oven_menu_gui.png");
    private static final Rectangle HEAT_ICON = new Rectangle(90, 46, 16, 14);
    private static final Rectangle PROGRESS_ARROW = new Rectangle(85, 26, 0, 17);

    public BrickOvenScreen(BrickOvenMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        /*
        // Render heat icon
        if (this.menu.isHeated()) {
            this.blit(pPoseStack, this.leftPos + HEAT_ICON.x, this.topPos + HEAT_ICON.y, 176, 0, HEAT_ICON.width, HEAT_ICON.height);
        }

         */

        renderProgressArrow(pPoseStack, x, y);
    }

    /*
    private void renderHeatIndicatorTooltip(PoseStack ms, int mouseX, int mouseY) {
        if (this.isHovering(HEAT_ICON.x, HEAT_ICON.y, HEAT_ICON.width, HEAT_ICON.height, mouseX, mouseY)) {
            List<Component> tooltip = new ArrayList<>();
            String key = "Brick Oven " + (this.menu.isHeated() ? "Heated" : "Not Heated");
            tooltip.add(Component.literal(key));
            this.renderComponentTooltip(ms, tooltip, mouseX, mouseY);
        }
    }

     */

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y) {
        int scaledProgress = this.menu.getScaledProgress();
        if(menu.isCrafting()) {
            blit(pPoseStack, x + 85, y + 26, 176, 14, menu.getScaledProgress() + 1, 17);}
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseY);
        this.font.draw(pPoseStack, this.playerInventoryTitle, 8.0f, (float) (this.imageHeight - 96 + 2), 4210752);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);

        //this.renderHeatIndicatorTooltip(pPoseStack, mouseX, mouseY);
    }
}
