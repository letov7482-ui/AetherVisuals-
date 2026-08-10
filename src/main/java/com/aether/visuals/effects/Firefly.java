package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class Firefly {
    private static final Random RANDOM = Random.create();

    /**
     * Светлячки ночью в лесах.
     */
    public static void tick() {
        if (!VisualsConfig.get("firefly")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || !client.world.isNight()) return;

        Vec3d pos = client.player.getPos();
        int radius = 10;

        if (RANDOM.nextFloat() < 0.1f) {
            double dx = (RANDOM.nextDouble() - 0.5) * radius * 2;
            double dy = 1 + RANDOM.nextDouble() * 2;
            double dz = (RANDOM.nextDouble() - 0.5) * radius * 2;

            client.particleManager.addParticle(
                ParticleTypes.GLOW,
                pos.x + dx, pos.y + dy, pos.z + dz,
                (RANDOM.nextDouble() - 0.5) * 0.02, (RANDOM.nextDouble() - 0.5) * 0.02, (RANDOM.nextDouble() - 0.5) * 0.02
            );
        }
    }
}
