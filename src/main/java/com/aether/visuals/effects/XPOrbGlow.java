package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class XPOrbGlow {
    private static final Random RANDOM = Random.create();

    /**
     * Сферы опыта светятся ярче.
     */
    public static void tick() {
        if (!VisualsConfig.get("xp_orb_glow")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        for (var entity : client.world.getEntities()) {
            if (entity instanceof ExperienceOrbEntity orb) {
                Vec3d pos = orb.getPos();

                if (RANDOM.nextFloat() < 0.3f) {
                    client.particleManager.addParticle(
                        ParticleTypes.HAPPY_VILLAGER,
                        pos.x, pos.y, pos.z,
                        0, 0.02, 0
                    );
                }
            }
        }
    }
}
