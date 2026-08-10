package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class TargetESP {
    private static long ticks = 0;
    private static final float RADIUS = 0.6f;
    private static final float HEIGHT_OFFSET = 0.3f;
    private static final float HEIGHT_RANGE = 1.0f;

    /**
     * Рендерит 4 привидения вокруг цели.
     * Вызывается каждый кадр, если функция включена.
     */
    public static void render(MatrixStack matrices, Camera camera, float tickDelta) {
        if (!VisualsConfig.get("target_esp")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;

        ticks++;

        Entity target = client.targetedEntity;
        if (!(target instanceof PlayerEntity) || target == client.player) return;

        PlayerEntity player = (PlayerEntity) target;
        Vec3d pos = player.getPos().add(0, player.getHeight() / 2, 0);

        // Цвет зависит от HP
        float hpPercent = player.getHealth() / player.getMaxHealth();
        float r, g, b;
        if (hpPercent > 0.6f) { r = 0.2f; g = 1.0f; b = 0.3f; }      // Зелёный
        else if (hpPercent > 0.3f) { r = 1.0f; g = 0.8f; b = 0.1f; } // Жёлтый
        else { r = 1.0f; g = 0.2f; b = 0.1f; }                        // Красный

        float alpha = 0.4f + (float)Math.sin(ticks * 0.05) * 0.2f; // Пульсация

        matrices.push();
        Vec3d camPos = camera.getPos();
        matrices.translate(pos.x - camPos.x, pos.y - camPos.y, pos.z - camPos.z);

        // Рендерим 4 привидения на разной высоте
        for (int i = 0; i < 4; i++) {
            float angle = (float)(ticks * 0.03 + i * Math.PI / 2);
            float ghostX = (float)(Math.cos(angle) * RADIUS);
            float ghostZ = (float)(Math.sin(angle) * RADIUS);
            float ghostY = HEIGHT_OFFSET + (float)Math.sin(angle * 1.5 + i) * HEIGHT_RANGE / 2;

            matrices.push();
            matrices.translate(ghostX, ghostY, ghostZ);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotation(-angle));
            matrices.multiply(RotationAxis.POSITIVE_X.rotation((float)(Math.sin(ticks * 0.05 + i) * 0.2)));

            // Размер привидения
            float size = 0.25f;
            float scale = 0.8f + (float)Math.sin(ticks * 0.04 + i) * 0.2f; // Плавное изменение размера

            matrices.scale(scale, scale, scale);

            renderGhostQuad(matrices, r, g, b, alpha);

            matrices.pop();
        }

        matrices.pop();
    }

    private static void renderGhostQuad(MatrixStack matrices, float r, float g, float b, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        buffer.vertex(matrices.peek().getPositionMatrix(), -0.15f, -0.3f, 0).color(r, g, b, alpha);
        buffer.vertex(matrices.peek().getPositionMatrix(), 0.15f, -0.3f, 0).color(r, g, b, alpha * 0.7f);
        buffer.vertex(matrices.peek().getPositionMatrix(), 0.15f, 0.3f, 0).color(r, g, b, alpha);
        buffer.vertex(matrices.peek().getPositionMatrix(), -0.15f, 0.3f, 0).color(r, g, b, alpha * 0.7f);

        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }
    }
