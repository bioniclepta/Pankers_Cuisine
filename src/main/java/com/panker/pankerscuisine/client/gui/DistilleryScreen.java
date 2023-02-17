package com.panker.pankerscuisine.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.panker.pankerscuisine.Pankers_Cuisine;
import com.panker.pankerscuisine.common.block.entity.container.DistilleryMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DistilleryScreen extends AbstractContainerScreen<DistilleryMenu> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Pankers_Cuisine.MOD_ID, "textures/gui/distillery_menu_gui.png");

    public DistilleryScreen(DistilleryMenu menu, Inventory inventory, Component component) {
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


        renderProgressArrow(pPoseStack, x, y);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y) {
        if(menu.isCrafting()) {
            int scaled = menu.getScaledProgress();

            if(scaled < 24000 * 2 && scaled >= 24000){scaled -= 24000;}
            else if(scaled < 24000 * 3 && scaled >= 24000 * 2){scaled -= 24000 * 2;}
            else if(scaled < 24000 * 4 && scaled >= 24000 * 3){scaled -= 24000 * 3;}
            else if(scaled < 24000 * 5 && scaled >= 24000 * 4){scaled -= 24000 * 4;}
            else if(scaled < 24000 * 6 && scaled >= 24000 * 5){scaled -= 24000 * 5;}
            else if(scaled < 24000 * 7 && scaled >= 24000 * 6){scaled -= 24000 * 6;}

            blit(pPoseStack, x + 64, y + 20 + 34 - scaled, 176, 34 - scaled, 37, scaled);
        }
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
    }
}
