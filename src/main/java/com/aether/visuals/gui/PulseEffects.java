package com.aether.visuals.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.font.TextRenderer;

public class PulseEffects {
    private static long ticks = 0;
    public static void tick() { ticks++; }
    public static long getTicks() { return ticks; }

    public static void renderGlitchText(DrawContext ctx, TextRenderer tr, String text, int cx, int y, int color) {
        long t = ticks / 60;
        int ox = (t % 5 == 0) ? 2 : 0, oy = (t % 7 == 0) ? 1 : 0;
        ctx.drawCenteredTextWithShadow(tr, text, cx + ox, y + oy, color);
        if (t % 13 == 0) ctx.drawCenteredTextWithShadow(tr, text.substring(0, Math.min(2, text.length())), cx - 30 + ox, y - 2, 0xFFFF3860);
    }

    public static float getPulseAlpha() {
        return 0.5f + (float) Math.sin(ticks * 0.05) * 0.5f;
    }

    public static void renderNeonBorder(DrawContext ctx, int x, int y, int w, int h, int color, float alpha) {
        int a = (int)(alpha * 255) << 24;
        int c = (color & 0x00FFFFFF) | a;
        ctx.fill(x, y, x + w, y + 1, c);
        ctx.fill(x, y + h - 1, x + w, y + h, c);
        ctx.fill(x, y, x + 1, y + h, c);
        ctx.fill(x + w - 1, y, x + w, y + h, c);
    }
}
