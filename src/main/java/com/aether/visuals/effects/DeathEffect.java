package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class DeathEffect {
    private static final Random RANDOM = Random.create();

    /**
     * Тёмные частицы + черепки при смерти.
     */
    public static void spawn(PlayerEntity player) {
        if (!VisualsConfig.get("kill_effect")) return; // Используем ключ kill_effect

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        Vec3d pos = player.getPos().add(0, player.getHeight() / 2, 0);

        // Тёмные частицы
        for (int i = 0; i < 15; i++) {
            double dx = (RANDOM.nextDouble() - 0.5) * 0.4;
            double dy = RANDOM.nextDouble() * 0.5;
            double dz = (RANDOM.nextDouble() - 0.5) * 0.4;

            client.particleManager.addParticle(
                ParticleTypes.SMOKE,
                pos.x + dx, pos.y + dy, pos.z + dz,
                dx * 0.3, dy * 0.3, dz * 0.3
            );
        }

        // Черепки
        for (int i = 0; i < 5; i++) {
            double dx = (RANDOM.nextDouble() - 0.5) * 0.6;
            double dy = RANDOM.nextDouble() * 0.6;
            double dz = (RANDOM.nextDouble() - 0.5) * 0.6;

            client.particleManager.addParticle(
                ParticleTypes.SCULK_SOUL,
                pos.x + dx, pos.y + dy, pos.z + dz,
                dx * 0.5, dy * 0.5, dz * 0.5
            );
        }
    }
              }
