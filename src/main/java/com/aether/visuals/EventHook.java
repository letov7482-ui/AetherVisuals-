package com.aether.visuals;

import com.aether.visuals.effects.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.util.ActionResult;

public class EventHook {
    private static boolean worldLoaded = false;

    public static void register() {
        // Хук удара
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!worldLoaded) return ActionResult.PASS;
            if (world.isClient && entity != null) {
                float damage = 1.0f;
                boolean critical = player.fallDistance > 0 && !player.isOnGround();
                HitParticles.spawn(entity, damage, critical);
                if (critical) HitParticles.spawnCritical(entity, damage);
                HitSound.play(entity.getPos());
                ComboCounter.onHit();
                DamageNumbers.spawn(entity, damage, false);
            }
            return ActionResult.PASS;
        });

        // Хук рендера (без TargetESP до загрузки мира)
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            if (!worldLoaded) return;
            TargetESP.render(context.matrixStack(), context.camera(), context.tickCounter().getTickDelta(true));
            FpsCounter.update();
        });

        // Хук HUD (без эффектов до загрузки мира)
        HudRenderCallback.EVENT.register((ctx, delta) -> {
            FpsCounter.render(ctx);
            PingDisplay.render(ctx);
            if (!worldLoaded) return;
            ArmorHUD.render(ctx);
            TotemCounter.render(ctx);
            PotionTimer.render(ctx);
            ComboCounter.render(ctx);
            LowHPVignette.render(ctx, delta.getTickDelta(true));
            DamageNumbers.render(ctx, ctx.getMatrices(), null, delta.getTickDelta(true));
        });

        // Хук тика
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null) worldLoaded = true;
            if (!worldLoaded) return;

            CrystalSparkle.tick();
            TrailEffect.tick();
            SprintSpark.tick();
            EnchantGlow.tick();
            GoldenAppleShine.tick();
            ArrowTrail.tick();
            XPOrbGlow.tick();
            WaterRipple.tick();
            FallImpact.tick();
            SneakSmoke.tick();
            RainImproved.tick();
            SnowParticles.tick();
            LeafFall.tick();
            Firefly.tick();
            TorchFlicker.tick();
            LowHPSound.tick();
        });
    }
}
