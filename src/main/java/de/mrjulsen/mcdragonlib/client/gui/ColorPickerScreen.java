package de.mrjulsen.mcdragonlib.client.gui;

import java.util.Locale;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import de.mrjulsen.mcdragonlib.DragonLibConstants;
import de.mrjulsen.mcdragonlib.client.ColorObject;
import de.mrjulsen.mcdragonlib.client.gui.wrapper.CommonScreen;
import de.mrjulsen.mcdragonlib.utils.Utils;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ColorPickerScreen extends CommonScreen {

    public static final Component title = Utils.translate("gui.dragonlib.colorpicker.title");

    private static final int WIDTH = 250;
    private static final int HEIGHT = 185;
    private static final int SELECTION_W = 9;
    private static final int SELECTION_H = 22;
    private static final int SELECTION_Y = 234;

    private static final int COLOR_PICKER_WIDTH = 180;
      
    private int guiLeft;
    private int guiTop;
    private boolean scrollingH = false, scrollingS = false, scrollingV = false;

    private final Screen lastScreen;
    private final Consumer<ColorObject> result;
    private final int currentColor;

    // color
    private double h = 0, s = 0, v = 0;
    private EditBox hBox;
    private EditBox sBox;
    private EditBox vBox;
    
    private EditBox rBox;
    private EditBox gBox;
    private EditBox bBox;
    
    private EditBox colorIntBox;

    // fix
    private boolean rgbNoUpdate = false;

    private Component textHSV = Utils.translate("gui.dragonlib.colorpicker.hsv");
    private Component textRGB = Utils.translate("gui.dragonlib.colorpicker.rgb");
    private Component textInteger = Utils.translate("gui.dragonlib.colorpicker.hex");

    private static final ResourceLocation gui = new ResourceLocation(DragonLibConstants.DRAGONLIB_MODID, "textures/gui/color_picker.png");

    public ColorPickerScreen(Screen lastScreen, int currentColor, Consumer<ColorObject> result) {
        super(title);
        this.lastScreen = lastScreen;
        this.currentColor = currentColor;
        this.result = result;

        float[] hsv = ColorObject.fromInt(currentColor).toHSV();
        this.h = hsv[0];
        this.s = hsv[1];
        this.v = hsv[2];
    }

    @Override
    public void onClose() {
        if (lastScreen != null) {            
            this.minecraft.setScreen(this.lastScreen);
        } else {
            super.onClose();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void init() {
        super.init();
        guiLeft = this.width / 2 - WIDTH / 2;
        guiTop = this.height / 2 - (HEIGHT + 24) / 2;

        addButton(this.width / 2 - 2 - 115, guiTop + HEIGHT - 28, 115, 20, CommonComponents.GUI_DONE, (p) -> {
            this.onDone();
        }, null);

        addButton(this.width / 2 + 3, guiTop + HEIGHT - 28, 115, 20, CommonComponents.GUI_CANCEL, (p) -> {
            this.onClose();
        }, null);

        this.hBox = addEditBox(
            guiLeft + 197, guiTop + 41, 44, 16,
            String.valueOf(h * 360), true,
            (x) -> {
                h = Double.valueOf(nullCheck(x)) / 360.0D;
            },
            (box, focusLost) -> {
                if (focusLost) {
                    updateInputBoxes();
                }
            }, null
        );
        this.hBox.setFilter(this::numberFilter360);

        this.sBox = addEditBox(
            guiLeft + 197, guiTop + 67, 44, 16,
            String.valueOf(s * 100), true,
            (x) -> {
                s = Double.valueOf(nullCheck(x)) / 100.0D;
            },
            (box, focusLost) -> {
                if (focusLost) {
                    updateInputBoxes();
                }
            }, null
        );
        this.sBox.setFilter(this::numberFilter100);

        this.vBox = addEditBox(
            guiLeft + 197, guiTop + 93, 44, 16,
            String.valueOf(v * 100), true,
            (x) -> {
                v = Double.valueOf(nullCheck(x)) / 100.0D;
            },
            (box, focusLost) -> {
                if (focusLost) {
                    updateInputBoxes();
                }
            }, null
        );
        this.vBox.setFilter(this::numberFilter100);

        rgbNoUpdate = true;
        this.rBox = addEditBox(
            guiLeft + 50, guiTop + 115, 32, 16,
            "0", true,
            (x) -> {
                if (rgbNoUpdate) {
                    return;
                }
                ColorObject c = new ColorObject(Integer.valueOf(nullCheck(x)), Integer.valueOf(nullCheck(gBox.getValue())), Integer.valueOf(nullCheck(bBox.getValue())));
                float[] hsv = c.toHSV();
                h = hsv[0];
                s = hsv[1];
                v = hsv[2];
            },
            (box, focusLost) -> {
                if (focusLost) {
                    updateInputBoxes();
                }
            }, null
        );
        this.rBox.setFilter(this::numberFilter255);

        this.gBox = addEditBox(
            guiLeft + 50 + 32, guiTop + 115, 32, 16,
            "0", true,
            (x) -> {
                if (rgbNoUpdate) {
                    return;
                }
                ColorObject c = new ColorObject(Integer.valueOf(nullCheck(rBox.getValue())), Integer.valueOf(nullCheck(x)), Integer.valueOf(nullCheck(bBox.getValue())));
                float[] hsv = c.toHSV();
                h = hsv[0];
                s = hsv[1];
                v = hsv[2];
            },
            (box, focusLost) -> {
                if (focusLost) {
                    updateInputBoxes();
                }
            }, null
        );
        this.gBox.setFilter(this::numberFilter255);

        this.bBox = addEditBox(
            guiLeft + 50 + 64, guiTop + 115, 32, 16,
            "0", true,
            (x) -> {
                if (rgbNoUpdate) {
                    return;
                }
                ColorObject c = new ColorObject(Integer.valueOf(nullCheck(rBox.getValue())), Integer.valueOf(nullCheck(gBox.getValue())), Integer.valueOf(nullCheck(x)));
                float[] hsv = c.toHSV();
                h = hsv[0];
                s = hsv[1];
                v = hsv[2];
            },
            (box, focusLost) -> {
                if (focusLost) {
                    updateInputBoxes();
                }
            }, null
        );
        this.bBox.setFilter(this::numberFilter255);

        this.colorIntBox = addEditBox(
            guiLeft + 50, guiTop + 135, 48, 16,
            "0", true,
            (x) -> {
                if (rgbNoUpdate) {
                    return;
                }
                ColorObject c = ColorObject.fromInt((int)Long.parseLong(nullCheck(x), 16));
                float[] hsv = c.toHSV();
                h = hsv[0];
                s = hsv[1];
                v = hsv[2];
            },
            (box, focusLost) -> {
                if (focusLost) {
                    updateInputBoxes();
                }
            }, null
        );
        this.colorIntBox.setFilter(this::editBoxHexFilter);
        this.colorIntBox.setMaxLength(6);
        
        this.updateInputBoxes();
        rgbNoUpdate = false;
    }

    private String nullCheck(String in) {
        return in == null || in.isEmpty() || in.equals("-") ? "0" : in;
    }

    private boolean editBoxHexFilter(String input) {
        if (input.isEmpty())
            return true;

        try {
            Integer.parseInt(input, 16);
			return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected void onDone() {
        this.result.accept(ColorObject.fromHSV(h, s, v));
        this.onClose();
    }

    private boolean numberFilter(String input, int min, int max) {
        if (input.isEmpty())
            return true;

        try {
            long i = Long.parseLong(input);
            return i >= min && i <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean numberFilter100(String input) {
        return numberFilter(input, 0, 100);
    }

    private boolean numberFilter255(String input) {
        return numberFilter(input, 0, 255);
    }

    private boolean numberFilter360(String input) {
        return numberFilter(input, 0, 360);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        renderBackground(stack, 0);

        GuiUtils.blit(gui, stack, guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);

        for (int i = 0; i < COLOR_PICKER_WIDTH; i++) {
            ColorObject ch = getH(i, COLOR_PICKER_WIDTH);
            ColorObject cs = getS(h, i, COLOR_PICKER_WIDTH);
            ColorObject cv = getV(h, i, COLOR_PICKER_WIDTH);
            fill(stack, guiLeft + 9 + i, guiTop + 41, guiLeft + 9 + i + 1, guiTop + 57, ch.toInt());
            fill(stack, guiLeft + 9 + i, guiTop + 67, guiLeft + 9 + i + 1, guiTop + 83, cs.toInt());
            fill(stack, guiLeft + 9 + i, guiTop + 93, guiLeft + 9 + i + 1, guiTop + 109, cv.toInt());

        }
        
        // Preview
        fill(stack, guiLeft + 197, guiTop + 10, guiLeft + 197 + 22, guiTop + 10 + 24, ColorObject.fromHSV(h, s, v).toInt());
        fill(stack, guiLeft + 197 + 22, guiTop + 10, guiLeft + 197 + 44, guiTop + 10 + 24, currentColor);

        String title = getTitle().getString();
        this.font.draw(stack, textHSV, guiLeft + 9, guiTop + 28, 4210752);
        this.font.draw(stack, title, guiLeft + WIDTH / 2 - font.width(title) / 2, guiTop + 6, 4210752);
        this.font.draw(stack, textRGB, guiLeft + 9, guiTop + 119, 4210752);
        this.font.draw(stack, textInteger, guiLeft + 9, guiTop + 139, 4210752);

        // Draw selections
        GuiUtils.blit(gui, stack, guiLeft + 5 + (int)(h * COLOR_PICKER_WIDTH), guiTop + 38, inSliderH(mouseX, mouseY) ? SELECTION_W : 0, SELECTION_Y, SELECTION_W, SELECTION_H);
        GuiUtils.blit(gui, stack, guiLeft + 5 + (int)(s * COLOR_PICKER_WIDTH), guiTop + 64, inSliderS(mouseX, mouseY) ? SELECTION_W : 0, SELECTION_Y, SELECTION_W, SELECTION_H);
        GuiUtils.blit(gui, stack, guiLeft + 5 + (int)(v * COLOR_PICKER_WIDTH), guiTop + 90, inSliderV(mouseX, mouseY) ? SELECTION_W : 0, SELECTION_Y, SELECTION_W, SELECTION_H);

        super.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        
        if (button == 0 && inSliderH(mouseX, mouseY)){            
            scrollingH = true;
            this.setH(setMouseValue(mouseX));
        } else if (button == 0 && inSliderS(mouseX, mouseY)){            
            scrollingS = true;
            this.setS(setMouseValue(mouseX));
        } else if (button == 0 && inSliderV(mouseX, mouseY)){            
            scrollingV = true;
            this.setV(setMouseValue(mouseX));
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (this.shouldCloseOnEsc() && p_keyPressed_1_ == 256 || this.minecraft.options.keyInventory.isActiveAndMatches(InputConstants.getKey(p_keyPressed_1_, p_keyPressed_2_))) {
            this.onClose();
            return true;
        } else {
            return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
        }
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {

        if (pButton == 0) {
            this.scrollingH = false;
            this.scrollingS = false;
            this.scrollingV = false;
        }

        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {

        double fh = 0;
        if (this.scrollingH || this.scrollingS || this.scrollingV) {
            fh = setMouseValue(pMouseX);
        }

        if (this.scrollingH) {
            this.setH(fh);
            return true;
        } else if (this.scrollingS) {
            this.setS(fh);
            return true;
        } else if (this.scrollingV) {
            this.setV(fh);
            return true;
        } else {
            return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        }
    }

    private void setH(double d) {
        this.h = Mth.clamp(d, 0d, 1d);
        this.updateInputBoxes();
    }

    private void setS(double d) { 
        this.s = Mth.clamp(d, 0d, 1d);
        this.updateInputBoxes();
    }

    private void setV(double d) {
        this.v = Mth.clamp(d, 0d, 1d);
        this.updateInputBoxes();
    }

    private void updateInputBoxes() {
        rgbNoUpdate = true;
        this.hBox.setValue(Integer.toString((int)(h * 360)));
        this.sBox.setValue(Integer.toString((int)(s * 100)));
        this.vBox.setValue(Integer.toString((int)(v * 100)));

        ColorObject c = ColorObject.fromHSV(h, s, v);
        this.rBox.setValue(Integer.toString((int)c.getR()));
        this.gBox.setValue(Integer.toString((int)c.getG()));
        this.bBox.setValue(Integer.toString((int)c.getB()));
        this.colorIntBox.setValue(convertToHex(c.toInt()));
        rgbNoUpdate = false;
    }

    private static String convertToHex(int rgbValue) {
        String hexString = Integer.toHexString(rgbValue & 0xFFFFFF);
        while (hexString.length() < 6) {
            hexString = "0" + hexString;
        }
        return hexString.toUpperCase(Locale.ENGLISH);
    }

    private double setMouseValue(double mouseX) {
        return (mouseX - (double)(this.guiLeft + 9)) / (double)(COLOR_PICKER_WIDTH - 1);
    }

    public static ColorObject getH(int i, int w) {
        float hue = (float) i / w;
        return ColorObject.fromHSV(hue, 1, 1);
    }

    public static ColorObject getS(double h, int i, int w) {
        float hue = (float) i / w;
        return ColorObject.fromHSV(h, hue, 1);
    }

    public static ColorObject getV(double h, int i, int w) {
        float hue = (float) i / w;
        return ColorObject.fromHSV(h, 1, hue);
    }

    protected boolean inSliderH(double mouseX, double mouseY) {
        int x = 9;
        int y = 42;
        int w = 180;
        int h = 16;

        int x1 = guiLeft + x;
        int y1 = guiTop + y;
        int x2 = x1 + w;
        int y2 = y1 + h;

        return mouseX >= (double)x1 && mouseY >= (double)y1 && mouseX < (double)x2 && mouseY < (double)y2;
    }

    protected boolean inSliderS(double mouseX, double mouseY) {
        int x = 9;
        int y = 68;
        int w = 180;
        int h = 16;

        int x1 = guiLeft + x;
        int y1 = guiTop + y;
        int x2 = x1 + w;
        int y2 = y1 + h;

        return mouseX >= (double)x1 && mouseY >= (double)y1 && mouseX < (double)x2 && mouseY < (double)y2;
    }

    protected boolean inSliderV(double mouseX, double mouseY) {
        int x = 9;
        int y = 94;
        int w = 180;
        int h = 16;

        int x1 = guiLeft + x;
        int y1 = guiTop + y;
        int x2 = x1 + w;
        int y2 = y1 + h;

        return mouseX >= (double)x1 && mouseY >= (double)y1 && mouseX < (double)x2 && mouseY < (double)y2;
    }
}

