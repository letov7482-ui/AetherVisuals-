package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PotionTimer {
    /**
     * Таймеры активных зелий на экране.
     */
    public static void render(DrawContext ctx) {
        if (!VisualsConfig.get("potion_timer")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        int x = 10;
        int y = client.getWindow().getScaledHeight() / 2 + 25;

        for (StatusEffectInstance effect : client.player.getStatusEffects()) {
            int duration = effect.getDuration() / 20; // В секундах
            if (duration <= 0) continue;

            String name = effect.getEffectType().getName().getString();
            String text = name + ": " + duration + "s";

            int color = effect.getEffectType().isBeneficial() ? 0xFF00FF88 : 0xFFFF6666;

            ctx.drawTextWithShadow(client.textRenderer, text, x, y, color);
            y += 10;
        }
    }
}
