package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class KillEffect {
    private static final Random RANDOM = Random.create();

    /**
     * Эпичный взрыв частиц при убийстве.
     */
    public static void spawn(Entity target) {
        if (!VisualsConfig.get("kill_effect")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        Vec3d pos = target.getPos().add(0, target.getHeight() / 2, 0);

        // Золотые звёзды во все стороны
        for (int i = 0; i < 40; i++) {
            double angle = RANDOM.nextDouble() * Math.PI * 2;
            double pitch = RANDOM.nextDouble() * Math.PI;
            double speed = 0.2 + RANDOM.nextDouble() * 0.5;
            double dx = Math.cos(angle) * Math.sin(pitch) * speed;
            double dy = Math.cos(pitch) * speed;
            double dz = Math.sin(angle) * Math.sin(pitch) * speed;

            client.particleManager.addParticle(
                ParticleTypes.FIREWORK,
                pos.x, pos.y, pos.z, dx, dy, dz
            );
        }

        // Кольцо частиц
        for (int i = 0; i < 20; i++) {
            double angle = RANDOM.nextDouble() * Math.PI * 2;
            double dx = Math.cos(angle) * 0.3;
            double dz = Math.sin(angle) * 0.3;

            client.particleManager.addParticle(
                ParticleTypes.END_ROD,
                pos.x, pos.y + 0.5, pos.z, dx, 0.2, dz
            );
        }
    }
}
