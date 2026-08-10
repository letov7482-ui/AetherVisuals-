package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class FpsCounter {
    private static int fps = 0;
    private static long lastUpdate = 0;
    private static int frameCount = 0;

    public static void update() {
        frameCount++;
        long now = System.currentTimeMillis();
        if (now - lastUpdate >= 1000) {
            fps = frameCount;
            frameCount = 0;
            lastUpdate = now;
        }
    }

    /**
     * Стильный неоновый счётчик FPS.
     */
    public static void render(DrawContext ctx) {
        if (!VisualsConfig.get("fps_counter")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        int color;
        if (fps >= 120) color = 0xFF00FFFF;      // Cyan
        else if (fps >= 60) color = 0xFF00FF88;   // Green
        else if (fps >= 30) color = 0xFFFFFF00;   // Yellow
        else color = 0xFFFF0000;                    // Red

        String text = fps + " FPS";
        int x = client.getWindow().getScaledWidth() - 50;
        int y = 5;

        // Тень для неонового эффекта
        ctx.drawTextWithShadow(client.textRenderer, text, x + 1, y + 1, 0xFF000000);
        ctx.drawTextWithShadow(client.textRenderer, text, x, y, color);
    }
}
