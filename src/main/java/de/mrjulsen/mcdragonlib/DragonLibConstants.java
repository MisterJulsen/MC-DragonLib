package de.mrjulsen.mcdragonlib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import com.google.gson.Gson;

import de.mrjulsen.mcdragonlib.client.gui.GuiUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;

public class DragonLibConstants {
    public static final String DRAGONLIB_MODID = "dragonlib";

    public static final String CONFIG_DIR = "./config";
    
    public static final int TICKS_PER_DAY = Level.TICKS_PER_DAY;
    public static final byte TPS = 1000 / MinecraftServer.MS_PER_TICK;
	/** float size of one pixel. */ public static final float PIXEL = 1.0F / 16.0F;

    public static final ResourceLocation UI = new ResourceLocation(DRAGONLIB_MODID, "textures/gui/ui.png");

    public static final int DEFAULT_UI_FONT_COLOR = 4210752;
    
    /** HERE BE DRAGONS! 🐉 */ public static final Component TEXT_DRAGON = GuiUtils.translate("text." + DRAGONLIB_MODID + ".dragon");
    public static final Component TEXT_NEXT = GuiUtils.translate("text." + DRAGONLIB_MODID + ".next");
    public static final Component TEXT_PREVIOUS = GuiUtils.translate("text." + DRAGONLIB_MODID + ".previous");
    public static final Component TEXT_GO_BACK = GuiUtils.translate("text." + DRAGONLIB_MODID + ".go_back");
    public static final Component TEXT_GO_FORTH = GuiUtils.translate("text." + DRAGONLIB_MODID + ".go_forth");    
    public static final Component TEXT_GO_UP = GuiUtils.translate("text." + DRAGONLIB_MODID + ".go_down");
    public static final Component TEXT_GO_DOWN = GuiUtils.translate("text." + DRAGONLIB_MODID + ".go_up");
    public static final Component TEXT_GO_RIGHT= GuiUtils.translate("text." + DRAGONLIB_MODID + ".go_right");
    public static final Component TEXT_GO_LEFT = GuiUtils.translate("text." + DRAGONLIB_MODID + ".go_left");
    public static final Component TEXT_GO_TO_TOP = GuiUtils.translate("text." + DRAGONLIB_MODID + ".go_to_top");
    public static final Component TEXT_GO_TO_BOTTOM = GuiUtils.translate("text." + DRAGONLIB_MODID + ".go_to_bottom");
    public static final Component TEXT_RESET_DEFAULTS = GuiUtils.translate("text." + DRAGONLIB_MODID + ".reset_defaults");
    public static final Component TEXT_EXPAND = GuiUtils.translate("text." + DRAGONLIB_MODID + ".expand");
    public static final Component TEXT_COLLAPSE = GuiUtils.translate("text." + DRAGONLIB_MODID + ".collapse");
    public static final Component TEXT_COUNT = GuiUtils.translate("text." + DRAGONLIB_MODID + ".count");
    public static final Component TEXT_TRUE = GuiUtils.translate("text." + DRAGONLIB_MODID + ".true");
    public static final Component TEXT_FALSE = GuiUtils.translate("text." + DRAGONLIB_MODID + ".false");
    public static final Component TEXT_CLOSE = GuiUtils.translate("text." + DRAGONLIB_MODID + ".close");
    public static final Component TEXT_SHOW = GuiUtils.translate("text." + DRAGONLIB_MODID + ".show");
    public static final Component TEXT_HIDE = GuiUtils.translate("text." + DRAGONLIB_MODID + ".hide");
    public static final Component TEXT_SEARCH = GuiUtils.translate("text." + DRAGONLIB_MODID + ".search");
    public static final Component TEXT_REFRESH = GuiUtils.translate("text." + DRAGONLIB_MODID + ".refresh");
    public static final Component TEXT_RELOAD = GuiUtils.translate("text." + DRAGONLIB_MODID + ".reload");

    public static final Random RANDOM = new Random();
    public static final Gson GSON = new Gson();
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat();
}
