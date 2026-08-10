package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class FallImpact {
    private static final Random RANDOM = Random.create();
    private static float lastFallDistance = 0;

    /**
     * Частицы пыли при падении с высоты.
     */
    public static void tick() {
        if (!VisualsConfig.get("fall_impact")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        float fallDist = client.player.fallDistance;
        boolean justLanded = client.player.isOnGround() && lastFallDistance > 3;

        if (justLanded) {
            Vec3d pos = client.player.getPos();

            for (int i = 0; i < (int)(lastFallDistance * 3); i++) {
                double dx = (RANDOM.nextDouble() - 0.5) * 0.5;
                double dz = (RANDOM.nextDouble() - 0.5) * 0.5;

                client.particleManager.addParticle(
                    ParticleTypes.CLOUD,
                    pos.x + dx, pos.y, pos.z + dz,
                    dx * 0.5, 0.1, dz * 0.5
                );
            }
        }

        lastFallDistance = client.player.isOnGround() ? 0 : fallDist;
    }
}
