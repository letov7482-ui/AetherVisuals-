package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class ArrowTrail {
    private static final Random RANDOM = Random.create();

    /**
     * Цветной след за стрелой.
     */
    public static void tick() {
        if (!VisualsConfig.get("arrow_trail")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        for (var entity : client.world.getEntities()) {
            if (entity instanceof ArrowEntity arrow) {
                Vec3d pos = arrow.getPos();

                client.particleManager.addParticle(
                    ParticleTypes.CRIT,
                    pos.x, pos.y, pos.z,
                    0, 0, 0
                );
            }
        }
    }
}
