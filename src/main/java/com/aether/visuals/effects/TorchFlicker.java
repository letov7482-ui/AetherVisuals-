package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class TorchFlicker {
    private static final Random RANDOM = Random.create();

    /**
     * Факелы реалистично мерцают.
     */
    public static void tick() {
        if (!VisualsConfig.get("torch_flicker")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        BlockPos playerPos = client.player.getBlockPos();
        int radius = 8;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos bp = playerPos.add(dx, dy, dz);
                    if (client.world.getBlockState(bp).getBlock() == Blocks.TORCH) {
                        if (RANDOM.nextFloat() < 0.3f) {
                            Vec3d pos = new Vec3d(bp.getX() + 0.5, bp.getY() + 0.7, bp.getZ() + 0.5);
                            client.particleManager.addParticle(
                                ParticleTypes.FLAME,
                                pos.x, pos.y, pos.z,
                                (RANDOM.nextDouble() - 0.5) * 0.01, 0.02, (RANDOM.nextDouble() - 0.5) * 0.01
                            );
                        }
                    }
                }
            }
        }
    }
}
