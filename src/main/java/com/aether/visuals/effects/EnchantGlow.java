package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class EnchantGlow {
    private static final Random RANDOM = Random.create();

    /**
     * Улучшенное свечение зачарованных предметов в руках.
     */
    public static void tick() {
        if (!VisualsConfig.get("enchant_glow")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // Проверяем, держит ли игрок зачарованный предмет
        var stack = client.player.getMainHandStack();
        if (!stack.hasEnchantments()) return;

        Vec3d pos = client.player.getPos().add(0, 1.2, 0);

        if (RANDOM.nextFloat() < 0.5f) {
            double dx = (RANDOM.nextDouble() - 0.5) * 0.3;
            double dy = (RANDOM.nextDouble() - 0.5) * 0.3;
            double dz = (RANDOM.nextDouble() - 0.5) * 0.3;

            client.particleManager.addParticle(
                ParticleTypes.ENCHANT,
                pos.x + dx, pos.y + dy, pos.z + dz,
                0, 0.02, 0
            );
        }
    }
}
