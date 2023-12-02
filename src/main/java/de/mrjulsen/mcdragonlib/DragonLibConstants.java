package de.mrjulsen.mcdragonlib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import com.google.gson.Gson;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;

public class DragonLibConstants {
    public static final String DRAGONLIB_MODID = "dragonlib";

    public static final String CONFIG_DIR = "./config";
    
    public static final int TICKS_PER_DAY = Level.TICKS_PER_DAY;
    public static final byte TPS = 1000 / MinecraftServer.MS_PER_TICK;

    public static final ResourceLocation UI = new ResourceLocation(DRAGONLIB_MODID, "textures/gui/ui.png");

    public static final int DEFAULT_UI_FONT_COLOR = 4210752;
    
    /** HERE BE DRAGONS! 🐉 */ public static final TranslatableComponent TEXT_DRAGON = new TranslatableComponent("text." + DRAGONLIB_MODID + ".dragon");
    public static final TranslatableComponent TEXT_NEXT = new TranslatableComponent("text." + DRAGONLIB_MODID + ".next");
    public static final TranslatableComponent TEXT_PREVIOUS = new TranslatableComponent("text." + DRAGONLIB_MODID + ".previous");
    public static final TranslatableComponent TEXT_GO_BACK = new TranslatableComponent("text." + DRAGONLIB_MODID + ".go_back");
    public static final TranslatableComponent TEXT_GO_FORTH = new TranslatableComponent("text." + DRAGONLIB_MODID + ".go_forth");    
    public static final TranslatableComponent TEXT_GO_UP = new TranslatableComponent("text." + DRAGONLIB_MODID + ".go_down");
    public static final TranslatableComponent TEXT_GO_DOWN = new TranslatableComponent("text." + DRAGONLIB_MODID + ".go_up");
    public static final TranslatableComponent TEXT_GO_RIGHT= new TranslatableComponent("text." + DRAGONLIB_MODID + ".go_right");
    public static final TranslatableComponent TEXT_GO_LEFT = new TranslatableComponent("text." + DRAGONLIB_MODID + ".go_left");
    public static final TranslatableComponent TEXT_GO_TO_TOP = new TranslatableComponent("text." + DRAGONLIB_MODID + ".go_to_top");
    public static final TranslatableComponent TEXT_GO_TO_BOTTOM= new TranslatableComponent("text." + DRAGONLIB_MODID + ".go_to_bottom");
    public static final TranslatableComponent TEXT_RESET_DEFAULTS = new TranslatableComponent("text." + DRAGONLIB_MODID + ".reset_defaults");
    public static final TranslatableComponent TEXT_EXPAND = new TranslatableComponent("text." + DRAGONLIB_MODID + ".expand");
    public static final TranslatableComponent TEXT_COLLAPSE = new TranslatableComponent("text." + DRAGONLIB_MODID + ".collapse");
    public static final TranslatableComponent TEXT_COUNT = new TranslatableComponent("text." + DRAGONLIB_MODID + ".count");
    public static final TranslatableComponent TEXT_TRUE = new TranslatableComponent("text." + DRAGONLIB_MODID + ".true");
    public static final TranslatableComponent TEXT_FALSE = new TranslatableComponent("text." + DRAGONLIB_MODID + ".false");
    public static final TranslatableComponent TEXT_CLOSE = new TranslatableComponent("text." + DRAGONLIB_MODID + ".close");
    public static final TranslatableComponent TEXT_SHOW = new TranslatableComponent("text." + DRAGONLIB_MODID + ".show");
    public static final TranslatableComponent TEXT_HIDE = new TranslatableComponent("text." + DRAGONLIB_MODID + ".hide");
    public static final TranslatableComponent TEXT_SEARCH = new TranslatableComponent("text." + DRAGONLIB_MODID + ".search");
    public static final TranslatableComponent TEXT_REFRESH = new TranslatableComponent("text." + DRAGONLIB_MODID + ".refresh");
    public static final TranslatableComponent TEXT_RELOAD = new TranslatableComponent("text." + DRAGONLIB_MODID + ".reload");

    public static final Random RANDOM = new Random();
    public static final Gson GSON = new Gson();
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat();
}
