package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class CrystalSparkle {
    private static final Random RANDOM = Random.create();

    /**
     * Кристаллы Края красиво мерцают.
     * Вызывается каждый тик для всех кристаллов в мире.
     */
    public static void tick() {
        if (!VisualsConfig.get("crystal_sparkle")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        for (var entity : client.world.getEntities()) {
            if (entity instanceof EndCrystalEntity crystal) {
                Vec3d pos = crystal.getPos();

                // Мерцающие частицы вокруг кристалла
                if (RANDOM.nextFloat() < 0.3f) {
                    double dx = (RANDOM.nextDouble() - 0.5) * 0.5;
                    double dy = RANDOM.nextDouble() * 1.0;
                    double dz = (RANDOM.nextDouble() - 0.5) * 0.5;

                    client.particleManager.addParticle(
                        ParticleTypes.ELECTRIC_SPARK,
                        pos.x + dx, pos.y + dy, pos.z + dz,
                        0, 0.02, 0
                    );
                }
            }
        }
    }
}
