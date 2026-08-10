package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import java.util.Random;

public class Starfield {
    private static final Star[] stars = new Star[100];
    private static boolean initialized = false;

    private static class Star {
        float x, y, size, alpha, twinkleSpeed, twinkleOffset;
    }

    private static void init() {
        Random r = new Random();
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
            stars[i].x = r.nextFloat();
            stars[i].y = r.nextFloat() * 0.5f;
            stars[i].size = 1 + r.nextFloat() * 2;
            stars[i].alpha = 0.3f + r.nextFloat() * 0.7f;
            stars[i].twinkleSpeed = 0.01f + r.nextFloat() * 0.05f;
            stars[i].twinkleOffset = r.nextFloat() * (float)Math.PI * 2;
        }
        initialized = true;
    }

    /**
     * Звёзды на небе мерцают.
     */
    public static void render(Matrix4f projectionMatrix, long ticks) {
        if (!VisualsConfig.get("starfield")) return;
        if (!initialized) init();

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || !client.world.isNight()) return;

        int w = client.getWindow().getScaledWidth();
        int h = client.getWindow().getScaledHeight();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

        for (Star star : stars) {
            float alpha = star.alpha * (0.5f + (float)Math.sin(ticks * star.twinkleSpeed + star.twinkleOffset) * 0.5f);
            int color = (int)(alpha * 255) << 24 | 0x00FFFFFF;

            float sx = star.x * w;
            float sy = star.y * h;

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

            buffer.vertex(sx, sy, 0).color(1f, 1f, 1f, alpha);
            buffer.vertex(sx + star.size, sy, 0).color(1f, 1f, 1f, alpha);
            buffer.vertex(sx + star.size, sy + star.size, 0).color(1f, 1f, 1f, alpha);
            buffer.vertex(sx, sy + star.size, 0).color(1f, 1f, 1f, alpha);

            BufferRenderer.drawWithGlobalProgram(buffer.end());
        }

        GL11.glDisable(GL11.GL_BLEND);
    }
}
