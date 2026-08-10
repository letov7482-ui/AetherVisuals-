package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class JumpRing {
    /**
     * Кольцо частиц при прыжке.
     */
    public static void spawn() {
        if (!VisualsConfig.get("jump_ring")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.player.isOnGround()) return;

        Vec3d pos = client.player.getPos();

        for (int i = 0; i < 12; i++) {
            double angle = (double)i / 12 * Math.PI * 2;
            double dx = Math.cos(angle) * 0.4;
            double dz = Math.sin(angle) * 0.4;

            client.particleManager.addParticle(
                ParticleTypes.CLOUD,
                pos.x + dx, pos.y, pos.z + dz,
                dx * 0.2, 0.05, dz * 0.2
            );
        }
    }
}
