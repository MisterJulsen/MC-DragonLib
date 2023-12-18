package de.mrjulsen.mcdragonlib.client.gui.widgets;

import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;

import de.mrjulsen.mcdragonlib.client.gui.Sprite;
import de.mrjulsen.mcdragonlib.client.gui.WidgetsCollection;
import de.mrjulsen.mcdragonlib.client.gui.DynamicGuiRenderer.AreaStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IconButton extends AbstractImageButton<IconButton> {

    public static final int DEFAULT_BUTTON_WIDTH = 18;
    public static final int DEFAULT_BUTTON_HEIGHT = 18;

    private Sprite sprite;

    public IconButton(ButtonType type, AreaStyle color, Sprite sprite, WidgetsCollection collection, int pX, int pY, int w, int h, Component pMessage, Consumer<Button> onClick) {
        super(type, color, collection, pX, pY, w, h, pMessage, onClick);
        this.sprite = sprite;        

        if (color == AreaStyle.NATIVE) {
            withFontColor(getFGColor());
        }
    }

    public IconButton(ButtonType type, AreaStyle color, Sprite sprite, int pX, int pY, int w, int h, Component pMessage, Consumer<Button> onClick) {
        this(type, color, sprite, null, pX, pY, w, h, pMessage, onClick);
    }

    public IconButton(ButtonType type, AreaStyle color, Sprite sprite, WidgetsCollection collection, int pX, int pY, Component pMessage, Consumer<Button> onClick) {
        this(type, color, sprite, collection, pX, pY, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT, pMessage, onClick);
    }

    public IconButton(ButtonType type, AreaStyle color, Sprite sprite, int pX, int pY, Component pMessage, Consumer<Button> onClick) {
        this(type, color, sprite, pX, pY, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT, pMessage, onClick);
    }

    @Override
    @SuppressWarnings("resource")
    public void renderImage(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Font font = Minecraft.getInstance().font;
        int labelWidth = font.width(this.getMessage()) + 4;

        if (getAlignment() == Alignment.LEFT || labelWidth > getWidth() - (6 + sprite.getWidth())) {
            if (this.getMessage() != null) {
                int i = this.getX() + 4 + sprite.getWidth();
                int j = this.getX() + this.getWidth() - 2;
                renderScrollingString(pPoseStack, font, getMessage(), i, getY(), j, getY() + getHeight(), getFontColor());
            }
            sprite.render(pPoseStack, getX() + 2, getY() + height / 2 - sprite.getHeight() / 2);
            return;
        }

        switch (getAlignment()) {
            case RIGHT:
                if (this.getMessage() != null) {
                    font.draw(pPoseStack, getMessage(), getX() + width - 2 + 4 - labelWidth, getY() + height / 2 - font.lineHeight / 2, getFontColor());
                }
                sprite.render(pPoseStack, getX() + width - 2 - labelWidth - sprite.getWidth(), getY() + height / 2 - sprite.getHeight() / 2);
                break;
            case CENTER:
            default:
                if (this.getMessage() != null) {
                    font.draw(pPoseStack, getMessage(), getX() + width / 2 + sprite.getWidth() / 2 - labelWidth / 2 + 4, getY() + height / 2 - font.lineHeight / 2, getFontColor());
                }
                sprite.render(pPoseStack, getX() + width / 2 - sprite.getWidth() / 2 - labelWidth / 2, getY() + height / 2 - sprite.getHeight() / 2);
                break;
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }
}