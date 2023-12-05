package de.mrjulsen.mcdragonlib.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import de.mrjulsen.mcdragonlib.client.CustomRenderTarget;
import de.mrjulsen.mcdragonlib.client.gui.widgets.ModEditBox;
import de.mrjulsen.mcdragonlib.client.gui.widgets.ResizableButton;
import de.mrjulsen.mcdragonlib.common.ITranslatableEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public final class GuiUtils {

	public static final int DEFAULT_BUTTON_HEIGHT = 20;
	
	protected static CustomRenderTarget framebuffer;
	private static ResourceLocation lastTexture;
    
    /**
	 * @see https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/foundation/gui/UIRenderHelper.java
	 */
	public static void init() {
		RenderSystem.recordRenderCall(() -> {
			Window mainWindow = Minecraft.getInstance().getWindow();
			framebuffer = CustomRenderTarget.create(mainWindow);
		});
	}

	public static CustomRenderTarget getFramebuffer() {
		return framebuffer;
	}
    
    /**
	 * @see https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/foundation/gui/UIRenderHelper.java
	 */
	public static void swapAndBlitColor(RenderTarget src, RenderTarget dst) {
		GlStateManager._glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, src.frameBufferId);
		GlStateManager._glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, dst.frameBufferId);
		GlStateManager._glBlitFrameBuffer(0, 0, src.viewWidth, src.viewHeight, 0, 0, dst.viewWidth, dst.viewHeight, GL30.GL_COLOR_BUFFER_BIT, GL20.GL_LINEAR);

		GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, dst.frameBufferId);
	}

    /**
	 * @see https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/foundation/gui/UIRenderHelper.java
	 */
    public static void startStencil(PoseStack matrixStack, float x, float y, float w, float h) {
		RenderSystem.clear(GL30.GL_STENCIL_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT, Minecraft.ON_OSX);

		GL11.glDisable(GL11.GL_STENCIL_TEST);
		RenderSystem.stencilMask(~0);
		RenderSystem.clear(GL11.GL_STENCIL_BUFFER_BIT, Minecraft.ON_OSX);
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		RenderSystem.stencilOp(GL11.GL_REPLACE, GL11.GL_KEEP, GL11.GL_KEEP);
		RenderSystem.stencilMask(0xFF);
		RenderSystem.stencilFunc(GL11.GL_NEVER, 1, 0xFF);

		matrixStack.pushPose();
		matrixStack.translate(x, y, 0);
		matrixStack.scale(w, h, 1);
		net.minecraftforge.client.gui.GuiUtils.drawGradientRect(matrixStack.last()
			.pose(), -100, 0, 0, 1, 1, 0xff000000, 0xff000000);
		matrixStack.popPose();

		GL11.glEnable(GL11.GL_STENCIL_TEST);
		RenderSystem.stencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
		RenderSystem.stencilFunc(GL11.GL_EQUAL, 1, 0xFF);
	}

    /**
	 * @see https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/foundation/gui/UIRenderHelper.java
	 */
	public static void endStencil() {
		GL11.glDisable(GL11.GL_STENCIL_TEST);
	}

    @SuppressWarnings("resource")
    public static <W extends AbstractWidget, T extends FormattedText> boolean renderTooltip(Screen screen, W widget, List<T> lines, int maxWidth, PoseStack stack, int mouseX, int mouseY) {
        if (widget.isMouseOver(mouseX, mouseY)) {
            screen.renderComponentTooltip(stack, getTooltipData(screen, lines, maxWidth), mouseX, mouseY, screen.getMinecraft().font);
			return true;
        }
		return false;
    }

	@SuppressWarnings("resource")
    public static <T extends FormattedText> boolean renderTooltip(Screen screen, GuiAreaDefinition area, List<T> lines, int maxWidth, PoseStack stack, int mouseX, int mouseY) {
        if (area.isInBounds(mouseX, mouseY)) {
            screen.renderComponentTooltip(stack, getTooltipData(screen, lines, maxWidth), mouseX, mouseY, screen.getMinecraft().font);
			return true;
        }
		return false;
    }

    @SuppressWarnings("resource")
    public static <T extends Enum<T> & ITranslatableEnum> List<FormattedText> getEnumTooltipData(String modid, Screen screen, Class<T> enumClass, int maxWidth) {
        List<FormattedText> c = new ArrayList<>();
        T enumValue = enumClass.getEnumConstants()[0];
        c.addAll(screen.getMinecraft().font.splitter.splitLines(new TranslatableComponent(enumValue.getEnumDescriptionTranslationKey(modid)), maxWidth, Style.EMPTY).stream().toList());
        c.add(new TextComponent(""));
        c.addAll(Arrays.stream(enumClass.getEnumConstants()).map((tr) -> {
            return new TextComponent(String.format("§l> %s§r§7\n%s", new TranslatableComponent(tr.getValueTranslationKey(modid)).getString(), new TranslatableComponent(tr.getValueInfoTranslationKey(modid)).getString()));
        }).map((x) -> screen.getMinecraft().font.splitter.splitLines(x, maxWidth, Style.EMPTY).stream().toList()).flatMap(List::stream).collect(Collectors.toList()));
        
        return c;
    }

    public static <T extends Enum<T> & ITranslatableEnum> List<FormattedText> getEnumTooltipData(String modid, Class<T> enumClass) {
        List<FormattedText> c = new ArrayList<>();
        T enumValue = enumClass.getEnumConstants()[0];
        c.add(new TranslatableComponent(enumValue.getEnumDescriptionTranslationKey(modid)));
        c.add(new TextComponent(""));
        c.addAll(Arrays.stream(enumClass.getEnumConstants()).map((tr) -> {
            return new TextComponent(String.format("§l> %s§r§7\n%s", new TranslatableComponent(tr.getValueTranslationKey(modid)).getString(), new TranslatableComponent(tr.getValueInfoTranslationKey(modid)).getString()));
        }).toList());
        return c;
    }

    public static <T extends FormattedText> List<FormattedText> getTooltipData(Screen screen, T component, int maxWidth) {
        return getTooltipData(screen, List.of(component), maxWidth);
    }

    public static <T extends FormattedText> List<FormattedText> getTooltipData(Screen screen, Collection<T> components, int maxWidth) {
        return components.stream().flatMap(a -> screen.getMinecraft().font.splitter.splitLines(a, maxWidth <= 0 ? screen.width : maxWidth, Style.EMPTY).stream()).toList();
    }


	public static boolean editBoxNumberFilter(String input) {
        if (input.isEmpty())
            return true;

        if (input.startsWith("-"))
            return true;

        try {
            Integer.parseInt(input);
			return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

	public static void setTexture(ResourceLocation texture) {
        RenderSystem.setShaderTexture(0, texture);
		if (lastTexture == null || !lastTexture.equals(texture)) {
			lastTexture = texture;
			
		}
	}

	public static void setShaderColor(float r, float g, float b, float a) {		
        RenderSystem.setShaderColor(r, g, b, a);
	}

	public static void blit(ResourceLocation texture, PoseStack pPoseStack, int pX, int pY, int pUOffset, int pVOffset, int pUWidth, int pVHeight) {
		setTexture(texture);
		GuiComponent.blit(pPoseStack, pX, pY, (float)pUOffset, (float)pVOffset, pUWidth, pVHeight, 256, 256);
	}

	public static void blit(ResourceLocation texture, PoseStack pPoseStack, int pX, int pY, int pWidth, int pHeight, float pUOffset, float pVOffset, int pUWidth, int pVHeight, int pTextureWidth, int pTextureHeight) {
		setTexture(texture);
		GuiComponent.blit(pPoseStack, pX, pY, pWidth, pHeight, pUOffset, pVOffset, pUWidth, pVHeight, pTextureWidth, pTextureHeight);
	}

	public static void blit(ResourceLocation texture, PoseStack pPoseStack, int pX, int pY, float pUOffset, float pVOffset, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight) {
		setTexture(texture);
		GuiComponent.blit(pPoseStack, pX, pY, pUOffset, pVOffset, pWidth, pHeight, pTextureHeight, pTextureWidth);
	}
	

	public static ResizableButton createButton(int x, int y, int width, int height, Component text, Consumer<Button> onClick) {
		return new ResizableButton(x, y, width, height, text, (btn) -> onClick.accept(btn));
	}

	public static <T extends Enum<T> & ITranslatableEnum> CycleButton<T> createCycleButton(String modid, Class<T> clazz, int x, int y, int width, int height, Component text, T initialValue, BiConsumer<CycleButton<?>, T> onValueChanged, Tooltip tooltip) {
        CycleButton<T> btn = CycleButton.<T>builder((p) -> {            
            return new TranslatableComponent(clazz.cast(p).getValueTranslationKey(modid));
        })
            .withValues(clazz.getEnumConstants()).withInitialValue(initialValue)
            .create(x, y, width, height, text, (b, v) -> onValueChanged.accept(b, v))
        ;
		return btn;
	}

    public static CycleButton<Boolean> createOnOffButton(int x, int y, int width, int height, Component text, boolean initialValue, BiConsumer<CycleButton<?>, Boolean> onValueChanged, Tooltip tooltip) {
        CycleButton<Boolean> btn = CycleButton.onOffBuilder(initialValue)
            .create(x, y, width, height, text, (b, v) -> onValueChanged.accept(b, v))
        ;
		
		return btn;
	}

    public static EditBox createEditBox(int x, int h, int width, int height, Font font, String text, boolean drawBg, Consumer<String> onValueChanged, BiConsumer<EditBox, Boolean> onFocusChanged, Tooltip tooltip) {
        ModEditBox box = new ModEditBox(font, x, h, width, height, new TextComponent(text));
        box.setOnFocusChanged(onFocusChanged);
		box.setResponder(onValueChanged);
        box.setValue(text);
        box.setBordered(drawBg);
		
		return box;
    }

    public static ForgeSlider createSlider(int x, int y, int width, int height, Component prefix, Component suffix, double min, double max, double step, double initialValue, boolean drawLabel, BiConsumer<ForgeSlider, Double> onValueChanged, Tooltip tooltip) {
        ForgeSlider slider = new ForgeSlider(x, y, width, height, prefix, suffix, min, max, initialValue, step, 1, drawLabel) {
            @Override
            protected void updateMessage() {
                if (this.drawString) {
                    this.setMessage(new TextComponent("").append(prefix).append(": ").append(this.getValueString()).append(suffix));
                } else {
                    this.setMessage(TextComponent.EMPTY);
                }        
            }

            @Override
            protected void applyValue() {
                super.applyValue();
                onValueChanged.accept(this, this.getValue());
            }
        };
        
		return slider;
    }

    public static TranslatableComponent translate(String key, Object... args) {
        return new TranslatableComponent(key, args);
    }

    public static TranslatableComponent translate(String key) {
        return new TranslatableComponent(key);
    }

    public static TextComponent text(String key) {
        return new TextComponent(key);
    }
	
}

