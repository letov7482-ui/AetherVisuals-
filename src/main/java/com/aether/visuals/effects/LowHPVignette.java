package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class LowHPVignette {
    private static float currentAlpha = 0f;
    private static final Identifier VIGNETTE_TEXTURE = Identifier.of("aethervisuals", "textures/effect/vignette.png");

    /**
     * Красная виньетка при низком HP. Плавно появляется и исчезает.
     */
    public static void render(DrawContext ctx, float tickDelta) {
        if (!VisualsConfig.get("low_hp_glow")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        float hpPercent = client.player.getHealth() / client.player.getMaxHealth();
        float targetAlpha = hpPercent < 0.3f ? (0.3f - hpPercent) / 0.3f * 0.6f : 0f;

        // Плавная интерполяция (плавность!)
        currentAlpha += (targetAlpha - currentAlpha) * tickDelta * 0.1f;
        if (currentAlpha < 0.01f) currentAlpha = 0f;

        if (currentAlpha <= 0f) return;

        int screenW = client.getWindow().getScaledWidth();
        int screenH = client.getWindow().getScaledHeight();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        float r = 0.8f, g = 0.1f, b = 0.1f, a = currentAlpha;

        buffer.vertex(0, screenH, 0).color(r, g, b, a);
        buffer.vertex(screenW, screenH, 0).color(r, g, b, a);
        buffer.vertex(screenW, 0, 0).color(r, g, b, a * 0.3f);
        buffer.vertex(0, 0, 0).color(r, g, b, a * 0.3f);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        GL11.glDisable(GL11.GL_BLEND);
    }
}
