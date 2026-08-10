package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class SneakSmoke {
    private static final Random RANDOM = Random.create();

    /**
     * Лёгкий дымок при подкрадывании.
     */
    public static void tick() {
        if (!VisualsConfig.get("sneak_smoke")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || !client.player.isSneaking()) return;

        Vec3d pos = client.player.getPos();

        if (RANDOM.nextFloat() < 0.3f) {
            double dx = (RANDOM.nextDouble() - 0.5) * 0.3;
            double dz = (RANDOM.nextDouble() - 0.5) * 0.3;

            client.particleManager.addParticle(
                ParticleTypes.CLOUD,
                pos.x + dx, pos.y + 0.1, pos.z + dz,
                0, -0.005, 0
            );
        }
    }
}
