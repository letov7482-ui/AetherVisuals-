package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import org.lwjgl.opengl.GL11;

public class FogEffect {
    /**
     * Лёгкий туман в низинах (включается/выключается).
     */
    public static void apply() {
        if (!VisualsConfig.get("fog_effect")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // Уменьшаем дальность тумана для атмосферы
        float y = (float)client.player.getY();
        if (y < 64) {
            GL11.glFogf(GL11.GL_FOG_START, 20f);
            GL11.glFogf(GL11.GL_FOG_END, 80f);
            GL11.glFogfv(GL11.GL_FOG_COLOR, new float[]{0.7f, 0.7f, 0.8f, 0.3f});
            GL11.glEnable(GL11.GL_FOG);
        } else {
            GL11.glDisable(GL11.GL_FOG);
        }
    }
}
