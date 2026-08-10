package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.random.Random;

public class GoldenAppleShine {
    private static final Random RANDOM = Random.create();

    /**
     * Золотые яблоки сияют в руке.
     */
    public static void tick() {
        if (!VisualsConfig.get("golden_apple_shine")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        var main = client.player.getMainHandStack();
        var off = client.player.getOffHandStack();

        boolean hasGoldenApple = main.getItem() == Items.GOLDEN_APPLE || main.getItem() == Items.ENCHANTED_GOLDEN_APPLE
            || off.getItem() == Items.GOLDEN_APPLE || off.getItem() == Items.ENCHANTED_GOLDEN_APPLE;

        if (!hasGoldenApple) return;

        var pos = client.player.getPos().add(0, 1.0, 0);

        for (int i = 0; i < 2; i++) {
            double dx = (RANDOM.nextDouble() - 0.5) * 0.2;
            double dz = (RANDOM.nextDouble() - 0.5) * 0.2;

            client.particleManager.addParticle(
                ParticleTypes.GLOW,
                pos.x + dx, pos.y, pos.z + dz,
                0, 0.05, 0
            );
        }
    }
}
