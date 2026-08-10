package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class ComboCounter {
    private static int combo = 0;
    private static long lastHit = 0;
    private static final long COMBO_TIMEOUT = 3000; // 3 секунды
    private static float displayAlpha = 0f;
    private static float displayScale = 1f;

    /**
     * Увеличивает комбо при ударе.
     */
    public static void onHit() {
        if (!VisualsConfig.get("combo_counter")) return;

        long now = System.currentTimeMillis();
        if (now - lastHit > COMBO_TIMEOUT) combo = 0;
        combo++;
        lastHit = now;
        displayAlpha = 1f;
        displayScale = 1.5f;
    }

    /**
     * Сбрасывает комбо при получении урона.
     */
    public static void onDamaged() {
        combo = 0;
        displayAlpha = 0f;
    }

    /**
     * Рендерит счётчик комбо на экране.
     */
    public static void render(DrawContext ctx) {
        if (!VisualsConfig.get("combo_counter")) return;
        if (combo < 2) return;

        // Плавное затухание
        displayAlpha += (0f - displayAlpha) * 0.05f;
        displayScale += (1f - displayScale) * 0.1f;
        if (displayAlpha < 0.01f) return;

        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer tr = client.textRenderer;

        String text = combo + "x COMBO!";
        int color = getComboColor(combo);
        color = (color & 0x00FFFFFF) | ((int)(displayAlpha * 255) << 24);

        int cx = client.getWindow().getScaledWidth() / 2;
        int cy = client.getWindow().getScaledHeight() / 3;

        ctx.getMatrices().push();
        ctx.getMatrices().translate(cx, cy, 0);
        ctx.getMatrices().scale(displayScale, displayScale, 1);
        ctx.drawCenteredTextWithShadow(tr, text, 0, 0, color);
        ctx.getMatrices().pop();
    }

    private static int getComboColor(int combo) {
        if (combo >= 20) return 0xFFFF00FF; // Фиолетовый
        if (combo >= 10) return 0xFFFF0000; // Красный
        if (combo >= 5) return 0xFFFF6600;  // Оранжевый
        return 0xFFFFFF00;                   // Жёлтый
    }
}
