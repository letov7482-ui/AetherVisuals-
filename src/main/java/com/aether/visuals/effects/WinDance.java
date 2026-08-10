package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

public class WinDance {
    /**
     * Частицы танцуют вокруг победителя.
     */
    public static void spawn() {
        if (!VisualsConfig.get("win_dance")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        Vec3d pos = client.player.getPos();

        for (int i = 0; i < 30; i++) {
            double angle = (double)i / 30 * Math.PI * 2;
            double radius = 1.0;
            double dx = Math.cos(angle) * radius;
            double dz = Math.sin(angle) * radius;

            client.particleManager.addParticle(
                ParticleTypes.NOTE,
                pos.x + dx, pos.y + 0.5 + i * 0.05, pos.z + dz,
                0, 0.1, 0
            );
        }
    }
}
