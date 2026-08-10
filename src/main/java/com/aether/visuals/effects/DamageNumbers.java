package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import java.util.*;

public class DamageNumbers {
    private static final List<FloatingNumber> numbers = new ArrayList<>();
    private static final Random RANDOM = new Random();

    private static class FloatingNumber {
        Vec3d pos;
        String text;
        int life;
        int color;
        float offsetX, offsetY;

        FloatingNumber(Vec3d pos, String text, int color) {
            this.pos = pos;
            this.text = text;
            this.color = color;
            this.life = 40;
            this.offsetX = (RANDOM.nextFloat() - 0.5f) * 20;
            this.offsetY = RANDOM.nextFloat() * 10;
        }
    }

    public static void spawn(Entity target, float damage, boolean heal) {
        if (!VisualsConfig.get(heal ? "heal_numbers" : "damage_numbers")) return;

        Vec3d pos = target.getPos().add(0, target.getHeight(), 0);
        int color = heal ? 0xFF00FF00 : 0xFFFF6600;
        String text = heal ? "+" + (int)damage : "-" + (int)damage;

        numbers.add(new FloatingNumber(pos, text, color));
    }

    public static void render(MatrixStack matrices, Camera camera, float tickDelta) {
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        Vec3d camPos = camera.getPos();

        for (var it = numbers.iterator(); it.hasNext(); ) {
            FloatingNumber n = it.next();
            n.life--;
            n.offsetY += 0.3f;

            if (n.life <= 0) { it.remove(); continue; }

            float alpha = Math.min(1f, n.life / 20f);
            int color = n.color | ((int)(alpha * 255) << 24);

            matrices.push();
            matrices.translate(n.pos.x - camPos.x, n.pos.y - camPos.y + n.offsetY / 50, n.pos.z - camPos.z);
            matrices.multiply(camera.getRotation());
            matrices.scale(-0.025f, -0.025f, 0.025f);

            tr.draw(matrices, n.text, n.offsetX, n.offsetY, color);

            matrices.pop();
        }
    }
}
