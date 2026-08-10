package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class GGEffect {
    private static final Random RANDOM = Random.create();

    /**
     * Фейерверк при написании "gg" в чат.
     */
    public static void spawn() {
        if (!VisualsConfig.get("gg_effect")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        Vec3d pos = client.player.getPos().add(0, 2, 0);

        for (int i = 0; i < 20; i++) {
            double dx = (RANDOM.nextDouble() - 0.5) * 0.5;
            double dy = RANDOM.nextDouble() * 0.5;
            double dz = (RANDOM.nextDouble() - 0.5) * 0.5;

            client.particleManager.addParticle(
                ParticleTypes.FIREWORK,
                pos.x + dx, pos.y + dy, pos.z + dz,
                dx * 0.5, dy * 0.5, dz * 0.5
            );
        }
    }
}
