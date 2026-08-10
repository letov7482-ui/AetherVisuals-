package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class MoonGlow {
    public static void render(Matrix4f projectionMatrix) {
        if (!VisualsConfig.get("moon_glow")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.world.isDay()) return;

        long time = client.world.getTimeOfDay() % 24000;
        float moonPhase = (time - 18000) / 6000f;
        if (moonPhase < 0 || moonPhase > 1) return;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);

        float cx = client.getWindow().getScaledWidth() * 0.85f;
        float cy = client.getWindow().getScaledHeight() * 0.15f;
        float alpha = 0.1f * moonPhase;
        int segments = 16;
        float radius = 80;

        buffer.vertex(cx, cy, 0).color(0.8f, 0.9f, 1f, alpha);
        for (int i = 0; i <= segments; i++) {
            double angle = (double)i / segments * Math.PI * 2;
            buffer.vertex(cx + (float)Math.cos(angle) * radius, cy + (float)Math.sin(angle) * radius, 0).color(0.8f, 0.9f, 1f, 0f);
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        GL11.glDisable(GL11.GL_BLEND);
    }
}
