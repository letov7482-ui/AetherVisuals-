package com.aether.visuals;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public class EventHook {
    public static void register() {
        // Hit Particles — при ударе
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClient && entity != null) {
                float damage = 1.0f; // Базовая оценка урона
                boolean critical = player.fallDistance > 0 && !player.isOnGround();
                com.aether.visuals.effects.HitParticles.spawn(entity, damage, critical);
                if (critical) {
                    com.aether.visuals.effects.HitParticles.spawnCritical(entity, damage);
                }
            }
            return ActionResult.PASS;
        });

        // Target ESP — рендер каждый кадр
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            com.aether.visuals.effects.TargetESP.render(
                context.matrixStack(),
                context.camera(),
                context.tickCounter().getTickDelta(true)
            );
        });
    }
}
