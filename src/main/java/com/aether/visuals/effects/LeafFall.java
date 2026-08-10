package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class LeafFall {
    private static final Random RANDOM = Random.create();

    /**
     * Листья падают с деревьев.
     */
    public static void tick() {
        if (!VisualsConfig.get("leaf_fall")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        Vec3d pos = client.player.getPos();
        int radius = 8;

        for (int i = 0; i < 2; i++) {
            double dx = (RANDOM.nextDouble() - 0.5) * radius * 2;
            double dz = (RANDOM.nextDouble() - 0.5) * radius * 2;
            double dy = 5 + RANDOM.nextDouble() * 10;

            client.particleManager.addParticle(
                ParticleTypes.CHERRY_LEAVES,
                pos.x + dx, pos.y + dy, pos.z + dz,
                (RANDOM.nextDouble() - 0.5) * 0.05, -0.02 - RANDOM.nextDouble() * 0.03, (RANDOM.nextDouble() - 0.5) * 0.05
            );
        }
    }
}
