package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class SnowParticles {
    private static final Random RANDOM = Random.create();

    /**
     * Реалистичные хлопья снега.
     */
    public static void tick() {
        if (!VisualsConfig.get("snow_particles")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || !client.world.isRaining()) return;

        // Снег только в холодных биомах
        if (client.world.getBiome(client.player.getBlockPos()).value().getTemperature() > 0.15f) return;

        Vec3d pos = client.player.getPos();
        int radius = 12;

        for (int i = 0; i < 3; i++) {
            double dx = (RANDOM.nextDouble() - 0.5) * radius * 2;
            double dz = (RANDOM.nextDouble() - 0.5) * radius * 2;
            double dy = 5 + RANDOM.nextDouble() * 5;

            client.particleManager.addParticle(
                ParticleTypes.SNOWFLAKE,
                pos.x + dx, pos.y + dy, pos.z + dz,
                (RANDOM.nextDouble() - 0.5) * 0.1, -0.05 - RANDOM.nextDouble() * 0.1, (RANDOM.nextDouble() - 0.5) * 0.1
            );
        }
    }
}
