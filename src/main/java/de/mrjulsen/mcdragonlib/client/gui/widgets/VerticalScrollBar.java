package de.mrjulsen.mcdragonlib.client.gui.widgets;

import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;

import de.mrjulsen.mcdragonlib.client.gui.DynamicGuiRenderer.ButtonState;
import de.mrjulsen.mcdragonlib.client.gui.DynamicGuiRenderer.AreaStyle;
import de.mrjulsen.mcdragonlib.DragonLibConstants;
import de.mrjulsen.mcdragonlib.client.gui.DynamicGuiRenderer;
import de.mrjulsen.mcdragonlib.client.gui.GuiAreaDefinition;
import de.mrjulsen.mcdragonlib.client.gui.GuiUtils;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VerticalScrollBar extends DragonLibWidgetBase implements IExtendedAreaWidget { 

    public static final int DEFAULT_STEP_SIZE = 1;

    private final GuiAreaDefinition scrollArea;

    private double scrollPercentage;
    private int scroll;
    private int maxScroll = 2;
    private boolean isScrolling = false;

    private int maxRowsOnPage = 1;
    private int scrollerHeight = 15;
    private int stepSize = DEFAULT_STEP_SIZE;

    private boolean autoScrollerHeight = false;

    // Events
    public Consumer<VerticalScrollBar> onValueChanged;

    public VerticalScrollBar(int x, int y, int w, int h, GuiAreaDefinition scrollArea) {
        super(x, y, Math.max(7, w), Math.max(7, h), null);
        this.scrollArea = scrollArea;
    }

    public VerticalScrollBar(int x, int y, int h, GuiAreaDefinition scrollArea) {
        this(x, y, 14, h, scrollArea);
    }

    public VerticalScrollBar(int x, int y, int w, int h) {
        this(x, y, w, h, null);
    }

    public VerticalScrollBar(int x, int y, int h) {
        this(x, y, h, null);
    }

    public VerticalScrollBar setStepSize(int c) {
        stepSize = Math.max(DEFAULT_STEP_SIZE, c);
        return this;
    }

    public VerticalScrollBar setMaxRowsOnPage(int c) {
        maxRowsOnPage = Math.max(1, c);
        return this;
    }

    public VerticalScrollBar setAutoScrollerHeight(boolean b) {
        autoScrollerHeight = b;
        return this;
    }

    public VerticalScrollBar setScrollerHeight(int h) {
        scrollerHeight = Math.max(5, h);
        return this;
    }

    public VerticalScrollBar updateMaxScroll(int rows) {
        this.maxScroll = Math.max(rows - maxRowsOnPage, 0);
        if (autoScrollerHeight) {
            this.scrollerHeight = Math.max((int)((height - 2) / Math.max(rows / (float)maxRowsOnPage, 1.0f)), 5);
        }
        return this;
    }

    public VerticalScrollBar setOnValueChangedEvent(Consumer<VerticalScrollBar> event) {
        this.onValueChanged = event;
        return this;
    }

    public boolean getAutoScrollerHeight() {
        return this.autoScrollerHeight;
    }

    public int getScrollValue() {
        return scroll;
    }

    public int getMaxScroll() {
        return maxScroll;
    }

    public int getMaxRowsOnPage() {
        return maxRowsOnPage;
    }



    @Override
    public boolean onClick(double pMouseX, double pMouseY) {
        if (isMouseOver(pMouseX, pMouseY) && canScroll()) {
            isScrolling = true;
            scrollTo(pMouseY);
        }
        return true;
    }

    @Override
    protected void onDrag(double pMouseX, double pMouseY, double pDragX, double pDragY) {
        if (this.isScrolling) {
            scrollTo(pMouseY);
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (canScroll()) {
            scroll = Mth.clamp((int)(scroll - pDelta * stepSize), 0, maxScroll);

            int i = maxScroll;
            this.scrollPercentage = (double)this.scrollPercentage - pDelta * stepSize / (double)i;
            this.scrollPercentage = Mth.clamp(this.scrollPercentage, 0.0F, 1.0F);
            
            if (onValueChanged != null)
                onValueChanged.accept(this);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRelease(double pMouseX, double pMouseY) {
        this.isScrolling = false;
    }

    private void scrollTo(double mousePos) {
        int i = y + 1;
        int j = i + height - 2;

        this.scrollPercentage = (mousePos - (double)i - ((double)scrollerHeight / 2.0D)) / (double)(j - i - scrollerHeight);
        this.scrollPercentage = Mth.clamp(this.scrollPercentage, 0.0F, 1.0F);
        scroll = Math.max(0, (int)Math.round(scrollPercentage * maxScroll));
        
        if (onValueChanged != null)
            onValueChanged.accept(this);
    }

    public void scrollToRow(int pos) {
        scroll = Mth.clamp(pos, 0, getMaxScroll());
        
        if (onValueChanged != null)
            onValueChanged.accept(this);
    }

    public boolean canScroll() {
        return maxScroll > 0;
    }

    @Override
    public void renderWidget(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        // Render background
        DynamicGuiRenderer.renderArea(pPoseStack, x, y, width, height, AreaStyle.GRAY, ButtonState.SUNKEN);

        // Render scrollbar
        int startU = canScroll() ? 20 : 25;
        int startV = 5;

        int x1 = x + 1;
        int y1 = y + 1 + (int)(scrollPercentage * (height - scrollerHeight - 2));
        int w = width - 2;
        int h = scrollerHeight;

        GuiUtils.blit(DragonLibConstants.UI, pPoseStack, x1, y1, 2, 2, startU, startV, 2, 2, DynamicGuiRenderer.TEXTURE_WIDTH, DynamicGuiRenderer.TEXTURE_HEIGHT); // top left
        GuiUtils.blit(DragonLibConstants.UI, pPoseStack, x1, y1 + h - 2, 2, 2, startU, startV + 3, 2, 2, DynamicGuiRenderer.TEXTURE_WIDTH, DynamicGuiRenderer.TEXTURE_HEIGHT); // bottom left
        GuiUtils.blit(DragonLibConstants.UI, pPoseStack, x1 + w - 2, y1, 2, 2, startU + 3, startV, 2, 2, DynamicGuiRenderer.TEXTURE_WIDTH, DynamicGuiRenderer.TEXTURE_HEIGHT); // top right
        GuiUtils.blit(DragonLibConstants.UI, pPoseStack, x1 + w - 2, y1 + h - 2, 2, 2, startU + 3, startV + 3, 2, 2, DynamicGuiRenderer.TEXTURE_WIDTH, DynamicGuiRenderer.TEXTURE_HEIGHT); // bottom right

        GuiUtils.blit(DragonLibConstants.UI, pPoseStack, x1 + 2, y1, w - 4, 2, startU + 2, startV, 1, 2, DynamicGuiRenderer.TEXTURE_WIDTH, DynamicGuiRenderer.TEXTURE_HEIGHT); // top
        GuiUtils.blit(DragonLibConstants.UI, pPoseStack, x1 + 2, y1 + h - 2, w - 4, 2, startU + 2, startV + 3, 1, 2, DynamicGuiRenderer.TEXTURE_WIDTH, DynamicGuiRenderer.TEXTURE_HEIGHT); // bottom
        GuiUtils.blit(DragonLibConstants.UI, pPoseStack, x1, y1 + 2, 2, h - 4, startU, startV + 2, 2, 1, DynamicGuiRenderer.TEXTURE_WIDTH, DynamicGuiRenderer.TEXTURE_HEIGHT); // left
        GuiUtils.blit(DragonLibConstants.UI, pPoseStack, x1 + w - 2, y1 + 2, 2, h - 4, startU + 3, startV + 2, 2, 1, DynamicGuiRenderer.TEXTURE_WIDTH, DynamicGuiRenderer.TEXTURE_HEIGHT); // right
        
        for (int i = 0; i < h - 4; i += 2) {
            GuiUtils.blit(DragonLibConstants.UI, pPoseStack, x1 + 2, y1 + 2 + i, w - 4, i < h - 4 ? 2 : 1, startU + 2, startV + 2, 1, i < h - 4 ? 2 : 1, DynamicGuiRenderer.TEXTURE_WIDTH, DynamicGuiRenderer.TEXTURE_HEIGHT);
        }
    }

    @Override
    public boolean isInArea(double mouseX, double mouseY) {
        return scrollArea == null || isMouseOver(mouseX, mouseY) || scrollArea.isInBounds(mouseX, mouseY);
    }
}
