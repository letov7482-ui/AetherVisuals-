package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WaterRipple {
    /**
     * Круги на воде при ходьбе.
     */
    public static void tick() {
        if (!VisualsConfig.get("water_ripple")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || !client.player.isOnGround()) return;

        BlockPos bp = client.player.getBlockPos();
        FluidState fluid = client.world.getFluidState(bp);

        if (fluid.getFluid() == Fluids.WATER || fluid.getFluid() == Fluids.FLOWING_WATER) {
            Vec3d pos = client.player.getPos();

            client.particleManager.addParticle(
                ParticleTypes.FISHING,
                pos.x, pos.y + 0.1, pos.z,
                0, 0, 0
            );
        }
    }
}
