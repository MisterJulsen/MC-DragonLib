package de.mrjulsen.mcdragonlib.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import de.mrjulsen.mcdragonlib.client.gui.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ResizableButton extends Button {

    public static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");

    public ResizableButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, OnPress pOnPress) {
        this(pX, pY, Mth.clamp(pWidth, 0, 394), Mth.clamp(pHeight, 0, 34), pMessage, pOnPress, DEFAULT_NARRATION);
    }

    public ResizableButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, OnPress pOnPress, CreateNarration pCreateNarration) {
        super(pX, pY, Mth.clamp(pWidth, 0, 394), Mth.clamp(pHeight, 0, 34), pMessage, pOnPress, pCreateNarration);
    }

    @Override
    public void renderWidget(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        int i = this.getTextureY();

        GuiUtils.blit(WIDGETS_LOCATION, pPoseStack, this.getX(), this.getY(), 0, 46 + i * 20, this.width / 2, this.height / 2);
        GuiUtils.blit(WIDGETS_LOCATION, pPoseStack, this.getX() + this.width / 2, this.getY(), 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height / 2);       
        GuiUtils.blit(WIDGETS_LOCATION, pPoseStack, this.getX(), this.getY() + this.height / 2, 0, 46 + (i + 1) * 20 - this.height / 2, this.width / 2, this.height / 2);
        GuiUtils.blit(WIDGETS_LOCATION, pPoseStack, this.getX() + this.width / 2, this.getY() + this.height / 2, 200 - this.width / 2, 46 + (i + 1) * 20 - this.height / 2, this.width / 2, this.height / 2);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int c = getFGColor();
        this.renderString(pPoseStack, minecraft.font, c | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    private int getTextureY() {
        int i = 1;
        if (!this.active) {
           i = 0;
        } else if (this.isHoveredOrFocused()) {
           i = 2;
        }
  
        return 46 + i * 20;
     }
    
}
