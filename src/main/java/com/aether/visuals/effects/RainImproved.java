package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class RainImproved {
    private static final Random RANDOM = Random.create();

    /**
     * Красивый дождь с брызгами от земли.
     */
    public static void tick() {
        if (!VisualsConfig.get("rain_improved")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || !client.world.isRaining()) return;

        Vec3d pos = client.player.getPos();
        int radius = 10;

        for (int i = 0; i < 5; i++) {
            double dx = (RANDOM.nextDouble() - 0.5) * radius * 2;
            double dz = (RANDOM.nextDouble() - 0.5) * radius * 2;
            double dy = 3 + RANDOM.nextDouble() * 5;

            client.particleManager.addParticle(
                ParticleTypes.RAIN,
                pos.x + dx, pos.y + dy, pos.z + dz,
                0, -0.3, 0
            );
        }
    }
}
