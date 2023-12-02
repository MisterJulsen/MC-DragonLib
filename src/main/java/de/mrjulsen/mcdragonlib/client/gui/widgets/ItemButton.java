package de.mrjulsen.mcdragonlib.client.gui.widgets;

import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;

import de.mrjulsen.mcdragonlib.client.gui.GuiUtils;
import de.mrjulsen.mcdragonlib.client.gui.WidgetsCollection;
import de.mrjulsen.mcdragonlib.client.gui.DynamicGuiRenderer.AreaStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ItemButton extends AbstractImageButton<ItemButton> {

    public static final int DEFAULT_BUTTON_WIDTH = 18;
    public static final int DEFAULT_BUTTON_HEIGHT = 18;

    private ItemStack item;

    public ItemButton(ButtonType type, AreaStyle color, ItemStack item, WidgetsCollection collection, int pX, int pY, int w, int h, Consumer<Button> onClick) {
        super(type, color, collection, pX, pY, w, h, item.getHoverName(), onClick);
        withItem(item);

        if (color == AreaStyle.NATIVE) {
            withFontColor(getFGColor());
        }
    }

    public ItemButton(ButtonType type, AreaStyle color, ItemStack item, int pX, int pY, int w, int h, Consumer<Button> onClick) {
        this(type, color, item, null, pX, pY, w, h, onClick);
    }

    public ItemButton(ButtonType type, AreaStyle color, ItemStack item, WidgetsCollection collection, int pX, int pY, Consumer<Button> onClick) {
        this(type, color, item, collection, pX, pY, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT, onClick);
    }

    public ItemButton(ButtonType type, AreaStyle color, ItemStack item, int pX, int pY, Consumer<Button> onClick) {
        this(type, color, item, pX, pY, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT, onClick);
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemButton withItem(ItemStack stack) {
        this.item = stack;
        return this;
    }

    @Override
    @SuppressWarnings("resource")
    public void renderImage(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        Font font = Minecraft.getInstance().font;
        int labelWidth = 0;
        if (this.getMessage() != null) {
            labelWidth = font.width(this.getMessage()) + 4;
            font.draw(pPoseStack, getMessage(), x + width / 2 + 8 - labelWidth / 2 + 4, y + height / 2 - font.lineHeight / 2, getFontColor());
        }
        
        this.renderBg(pPoseStack, Minecraft.getInstance(), pMouseX, pMouseY);
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(item, x + width / 2 - 8 - labelWidth / 2, y + height / 2 - 8);
    }

    public void renderTooltip(Screen parent, PoseStack pPoseStack, int pMouseX, int pMouseY) {
        if (isMouseOver(pMouseX, pMouseY)) {
            GuiUtils.renderTooltip(parent, this, parent.getTooltipFromItem(getItem()), -1, pPoseStack, pMouseX, pMouseY);
        }
    }

    public static void renderAllItemButtonTooltips(Screen parent, PoseStack pPoseStack, int pMouseX, int pMouseY) {
        parent.renderables.stream().filter(x -> x instanceof ItemButton b && b.isMouseOver(pMouseX, pMouseY)).forEach(x -> ((ItemButton)x).renderTooltip(parent, pPoseStack, pMouseX, pMouseY));
    }
}

