package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class TrailEffect {
    private static final Random RANDOM = Random.create();
    private static Vec3d lastPos = null;

    /**
     * Красивый след из частиц за игроком при беге.
     */
    public static void tick() {
        if (!VisualsConfig.get("trail")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || !client.player.isSprinting()) {
            lastPos = null;
            return;
        }

        Vec3d pos = client.player.getPos();
        if (lastPos != null && lastPos.distanceTo(pos) < 0.2) return;

        lastPos = pos;

        // Частицы за спиной
        Vec3d lookDir = client.player.getRotationVec(1.0f);
        Vec3d behind = pos.subtract(lookDir.multiply(0.5));

        for (int i = 0; i < 3; i++) {
            double dx = (RANDOM.nextDouble() - 0.5) * 0.3;
            double dz = (RANDOM.nextDouble() - 0.5) * 0.3;

            client.particleManager.addParticle(
                ParticleTypes.CLOUD,
                behind.x + dx, behind.y + 0.1, behind.z + dz,
                0, -0.01, 0
            );
        }
    }
}
