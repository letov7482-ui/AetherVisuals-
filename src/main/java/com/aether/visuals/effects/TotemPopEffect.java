package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class TotemPopEffect {
    private static final Random RANDOM = Random.create();

    /**
     * Золотое свечение при срабатывании тотема.
     */
    public static void spawn(PlayerEntity player) {
        if (!VisualsConfig.get("totem_pop")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        Vec3d pos = player.getPos().add(0, player.getHeight() / 2, 0);

        // Золотые частицы по спирали
        for (int i = 0; i < 30; i++) {
            double angle = (double)i / 30 * Math.PI * 2;
            double radius = 0.5 + RANDOM.nextDouble() * 0.3;
            double dx = Math.cos(angle) * radius * 0.5;
            double dy = (double)i / 30 * 1.5;
            double dz = Math.sin(angle) * radius * 0.5;

            client.particleManager.addParticle(
                ParticleTypes.GLOW,
                pos.x + dx, pos.y + dy - 0.5, pos.z + dz,
                0, 0.05, 0
            );
        }

        // Кольцо внизу
        for (int i = 0; i < 20; i++) {
            double angle = RANDOM.nextDouble() * Math.PI * 2;
            double dx = Math.cos(angle) * 0.8;
            double dz = Math.sin(angle) * 0.8;

            client.particleManager.addParticle(
                ParticleTypes.SCRAPE,
                pos.x + dx, pos.y, pos.z + dz,
                dx * 0.3, 0.1, dz * 0.3
            );
        }
    }
}
