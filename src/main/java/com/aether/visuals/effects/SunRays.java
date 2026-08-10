package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class SunRays {
    private static long ticks = 0;

    /**
     * Лучи солнца через листву.
     */
    public static void render(Matrix4f projectionMatrix, Camera camera) {
        if (!VisualsConfig.get("sun_rays")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.world.isRaining()) return;

        ticks++;
        float alpha = 0.05f + (float)Math.sin(ticks * 0.01) * 0.02f;

        // Упрощённый рендер лучей
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);

        int rays = 8;
        float cx = client.getWindow().getScaledWidth() / 2f;
        float cy = client.getWindow().getScaledHeight() / 3f;

        buffer.vertex(cx, cy, 0).color(1f, 1f, 0.8f, alpha);

        for (int i = 0; i <= rays; i++) {
            double angle = (double)i / rays * Math.PI * 2;
            float r = 200 + (float)Math.sin(ticks * 0.02 + i) * 50;
            float x = cx + (float)Math.cos(angle) * r;
            float y = cy + (float)Math.sin(angle) * r * 0.3f;
            buffer.vertex(x, y, 0).color(1f, 1f, 0.8f, 0f);
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        GL11.glDisable(GL11.GL_BLEND);
    }
                            }
