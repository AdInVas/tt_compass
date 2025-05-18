package net.adinvas.tt_compass;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final String KEY_CATEGORY = "key.categories.mnsaddition";

    public static final KeyMapping TOGGLE_COMPASS = new KeyMapping("key.mnsaddition.toggle_config",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            KEY_CATEGORY);

}
