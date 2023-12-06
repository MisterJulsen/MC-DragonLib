package de.mrjulsen.mcdragonlib.client.gui;

import java.util.Collection;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import de.mrjulsen.mcdragonlib.common.ITranslatableEnum;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.FormattedText;

public class Tooltip {
    public static final int WIDTH_UNDEFINED = -1;

    private final List<FormattedText> lines;
    private int maxWidth = WIDTH_UNDEFINED;
    private GuiAreaDefinition assignedArea;
    private AbstractWidget assignedWidget;
    private boolean visible = true;

    private Tooltip(List<FormattedText> lines) {
        this.lines = lines;
    }

    public static Tooltip empty() {
        return Tooltip.of((FormattedText)null);
    }

    public static Tooltip of(String text) {
        return new Tooltip(text == null ? null : List.of(GuiUtils.text(text)));
    }

    public static Tooltip of(Collection<String> text) {
        return new Tooltip(text.stream().map(x -> (FormattedText)GuiUtils.text(x)).toList());
    }

    public static Tooltip of(FormattedText FormattedText) {
        return new Tooltip(FormattedText == null ? null : List.of(FormattedText));
    }

    public static Tooltip of(List<FormattedText> FormattedTexts) {
        return new Tooltip(FormattedTexts);
    }

    public static <E extends Enum<E> & ITranslatableEnum> Tooltip of(String modid, Class<E> enumClass) {
        return new Tooltip(GuiUtils.getEnumTooltipData(modid, enumClass));
    }

    public Tooltip withMaxWidth(int maxWidth) {
        return this;
    }

    public Tooltip assignedTo(AbstractWidget widget) {
        assignedWidget = widget;
        return this;
    }

    public Tooltip assignedTo(GuiAreaDefinition area) {
        assignedArea = area;
        return this;
    }

    public Tooltip withVisibility(boolean b) {
        visible = b;
        return this;
    }



    public List<FormattedText> getLines() {
        return lines;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void render(Screen screen, PoseStack poseStack, int mouseX, int mouseY) {
        if (lines.size() <= 0) {
            return;
        }

        if (assignedWidget != null) {
            GuiUtils.renderTooltip(screen, assignedWidget, getLines(), getMaxWidth() > 0 ? getMaxWidth() : screen.width, poseStack, mouseX, mouseY);            
        } else if (assignedArea != null) {
            GuiUtils.renderTooltip(screen, assignedArea, getLines(), getMaxWidth() > 0 ? getMaxWidth() : screen.width, poseStack, mouseX, mouseY);  
        } else {
            GuiUtils.renderTooltip(screen, GuiAreaDefinition.of(screen), getLines(), getMaxWidth() > 0 ? getMaxWidth() : screen.width, poseStack, mouseX, mouseY); 
        }        
    }
}
