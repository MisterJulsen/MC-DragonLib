package de.mrjulsen.mcdragonlib.client.gui.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import com.mojang.blaze3d.vertex.PoseStack;

import de.mrjulsen.mcdragonlib.client.gui.GuiUtils;
import de.mrjulsen.mcdragonlib.client.gui.Tooltip;
import de.mrjulsen.mcdragonlib.client.gui.widgets.IExtendedAreaWidget;
import de.mrjulsen.mcdragonlib.client.gui.widgets.ItemButton;
import de.mrjulsen.mcdragonlib.client.gui.widgets.ResizableButton;
import de.mrjulsen.mcdragonlib.common.ITranslatableEnum;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public abstract class CommonScreen extends net.minecraft.client.gui.screens.Screen {

    protected List<Tooltip> tooltips = new ArrayList<>();

    protected CommonScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
        tooltips.clear();
    }

    @Override
    public void tick() {
        super.tick();
        this.renderables.stream().filter(x -> x instanceof EditBox).forEach(x -> ((EditBox)x).tick());
    }

    public void onDone() {}

    public void renderBg(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {}

    public void renderFg(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        tooltips.forEach(x -> x.render(this, pPoseStack, pMouseX, pMouseY));
        ItemButton.renderAllItemButtonTooltips(this, pPoseStack, pMouseX, pMouseY);
    }
    
    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBg(pPoseStack, pMouseX, pMouseY, pPartialTick);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderFg(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    protected Tooltip addTooltip(Tooltip tooltip) {
        this.tooltips.add(tooltip);
        return tooltip;
    }

    protected ResizableButton addButton(int x, int y, int width, int height, Component text, Consumer<Button> onClick, Tooltip tooltip) {
        ResizableButton btn = GuiUtils.createButton(x, y, width, height, text, onClick);
        return addRenderableWidget(btn, x, y, width, height, tooltip);
	}

    protected <T extends Enum<T> & ITranslatableEnum> CycleButton<T> addCycleButton(String modid, Class<T> clazz, int x, int y, int width, int height, Component text, T initialValue, BiConsumer<CycleButton<?>, T> onValueChanged, Tooltip tooltip) {
        CycleButton<T> btn = GuiUtils.createCycleButton(modid, clazz, x, y, width, height, text, initialValue, onValueChanged, tooltip);
        return addRenderableWidget(btn, x, y, width, height, tooltip);
	}

    protected CycleButton<Boolean> addOnOffButton(int x, int y, int width, int height, Component text, boolean initialValue, BiConsumer<CycleButton<?>, Boolean> onValueChanged, Tooltip tooltip) {
        CycleButton<Boolean> btn = GuiUtils.createOnOffButton(x, y, width, height, text, initialValue, onValueChanged, tooltip);
        return addRenderableWidget(btn, x, y, width, height, tooltip);
	}

    protected EditBox addEditBox(int x, int y, int width, int height, String text, boolean drawBg, Consumer<String> onValueChanged, BiConsumer<EditBox, Boolean> onFocusChanged, Tooltip tooltip) {
        EditBox box = GuiUtils.createEditBox(x, y, width, height, font, text, drawBg, onValueChanged, onFocusChanged, tooltip);
        return this.addRenderableWidget(box, x, y, width, height, tooltip);
    }

    protected ForgeSlider addSlider(int x, int y, int width, int height, Component prefix, Component suffix, double min, double max, double step, double initialValue, boolean drawLabel, BiConsumer<ForgeSlider, Double> onValueChanged, Tooltip tooltip) {
        ForgeSlider slider = GuiUtils.createSlider(x, y, width, height, prefix, suffix, min, max, step, initialValue, drawLabel, onValueChanged, tooltip);
        return this.addRenderableWidget(slider, x, y, width, height, tooltip);
    }

    protected <W extends AbstractWidget> W addRenderableWidget(W widget, int x, int y, int width, int height, Tooltip tooltip) {
        if (tooltip != null && tooltip.equals(Tooltip.empty())) {
            addTooltip(tooltip.assignedTo(widget));
        }

        widget.x = x;
        widget.y = y;
        widget.setWidth(width);
        widget.setHeight(height);
        
		return addRenderableWidget(widget);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        boolean[] b = new boolean[] { false };
        this.renderables.stream().filter(x -> x instanceof GuiEventListener g && this.getFocused() == g && g.isMouseOver(pMouseX, pMouseY)).forEach(x -> {
            if (((GuiEventListener)x).mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY) && !b[0])
                b[0] = true;
        });
        super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        return b[0];
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        boolean[] b = new boolean[] { false };
        this.renderables.stream().filter(x -> x instanceof GuiEventListener g).forEach(x -> {
            if (((((GuiEventListener)x).mouseScrolled(pMouseX, pMouseY, pDelta) && ((GuiEventListener)x).isMouseOver(pMouseX, pMouseY)) || (x instanceof IExtendedAreaWidget exw && exw.isInArea(pMouseX, pMouseY))) && !b[0]) {
                b[0] = true;
            }
        });
        super.mouseScrolled(pMouseX, pMouseY, pDelta);
        return b[0];
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        boolean[] b = new boolean[] { false };
        this.renderables.stream().filter(x -> x instanceof GuiEventListener g && this.getFocused() == g && g.isMouseOver(pMouseX, pMouseY)).forEach(x -> {
            if (((GuiEventListener)x).mouseReleased(pMouseX, pMouseY, pButton) && !b[0]) {
                b[0] = true;
            }
        });
        super.mouseReleased(pMouseX, pMouseY, pButton);
        return b[0];
    }
}
