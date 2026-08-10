package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class HitParticles {
    private static final Random RANDOM = Random.create();

    /**
     * Создаёт красивые жёлтые звёзды при ударе.
     * @param target — цель, по которой попали
     * @param damage — урон (влияет на количество звёзд)
     * @param critical — был ли крит
     */
    public static void spawn(Entity target, float damage, boolean critical) {
        if (!VisualsConfig.get("hit_particles") && !critical) return;
        if (critical && !VisualsConfig.get("critical_hit")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;

        int count = critical ? 20 : (int)(damage * 3);
        Vec3d pos = target.getPos().add(0, target.getHeight() / 2, 0);

        for (int i = 0; i < count; i++) {
            double angle = RANDOM.nextDouble() * Math.PI * 2;
            double speed = 0.1 + RANDOM.nextDouble() * 0.3;
            double dx = Math.cos(angle) * speed;
            double dy = 0.1 + RANDOM.nextDouble() * 0.4;
            double dz = Math.sin(angle) * speed;

            // Звёзды: жёлтые (обычный удар) или фиолетовые (крит)
            Particle particle = client.particleManager.addParticle(
                critical ? ParticleTypes.END_ROD : ParticleTypes.WAX_ON,
                pos.x, pos.y, pos.z, dx, dy, dz
            );

            if (particle != null) {
                // Масштабируем для детализации
                particle.scale(1.5f + RANDOM.nextFloat() * 0.5f);
                // Частицы живут 15-25 тиков (плавное затухание)
            }
        }
    }

    /**
     * Спавнит критические звёзды + молнии.
     */
    public static void spawnCritical(Entity target, float damage) {
        spawn(target, damage, true);

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        Vec3d pos = target.getPos().add(0, target.getHeight() / 2, 0);

        // Дополнительные фиолетовые частицы
        for (int i = 0; i < 10; i++) {
            double dx = (RANDOM.nextDouble() - 0.5) * 0.5;
            double dy = RANDOM.nextDouble() * 0.5;
            double dz = (RANDOM.nextDouble() - 0.5) * 0.5;

            client.particleManager.addParticle(
                ParticleTypes.ENCHANT,
                pos.x, pos.y, pos.z, dx, dy, dz
            );
        }
    }
}
