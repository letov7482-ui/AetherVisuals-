package com.aether.visuals;

import com.aether.visuals.gui.VisualsScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AetherVisualsMod implements ModInitializer {
    public static final String MOD_ID = "aethervisuals";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Aether Visuals — Beauty. Smoothness. Detail. Shock.");

        KeyBinding key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.aethervisuals.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "Aether Visuals"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (key.wasPressed()) client.setScreen(new VisualsScreen());
        });

        EventHook.register();
    }
}
