package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class RespawnEffect {
    private static final Random RANDOM = Random.create();

    /**
     * Золотое свечение при возрождении.
     */
    public static void spawn() {
        if (!VisualsConfig.get("low_hp_glow")) return; // Используем тот же ключ

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        Vec3d pos = client.player.getPos();

        for (int i = 0; i < 25; i++) {
            double angle = RANDOM.nextDouble() * Math.PI * 2;
            double radius = 0.5 + RANDOM.nextDouble() * 0.5;
            double dx = Math.cos(angle) * radius;
            double dz = Math.sin(angle) * radius;

            client.particleManager.addParticle(
                ParticleTypes.END_ROD,
                pos.x + dx, pos.y + i * 0.07, pos.z + dz,
                0, 0.1, 0
            );
        }
    }
}
