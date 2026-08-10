package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class SprintSpark {
    private static final Random RANDOM = Random.create();

    /**
     * Искры из-под ног при спринте.
     */
    public static void tick() {
        if (!VisualsConfig.get("sprint_spark")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || !client.player.isSprinting() || !client.player.isOnGround()) return;

        Vec3d pos = client.player.getPos();

        for (int i = 0; i < 2; i++) {
            double dx = (RANDOM.nextDouble() - 0.5) * 0.4;
            double dz = (RANDOM.nextDouble() - 0.5) * 0.4;

            client.particleManager.addParticle(
                ParticleTypes.ELECTRIC_SPARK,
                pos.x + dx, pos.y, pos.z + dz,
                dx * 0.5, 0.1, dz * 0.5
            );
        }
    }
}
