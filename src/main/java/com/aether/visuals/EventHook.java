package com.aether.visuals;

import com.aether.visuals.effects.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.util.ActionResult;

public class EventHook {
    public static void register() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
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

        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            TargetESP.render(context.matrixStack(), context.camera(), context.tickCounter().getTickDelta(true));
            FpsCounter.update();
        });

        HudRenderCallback.EVENT.register((ctx, delta) -> {
            FpsCounter.render(ctx);
            PingDisplay.render(ctx);
            ArmorHUD.render(ctx);
            TotemCounter.render(ctx);
            PotionTimer.render(ctx);
            ComboCounter.render(ctx);
            LowHPVignette.render(ctx, delta.getTickDelta(true));
            DamageNumbers.render(ctx, ctx.getMatrices(), null, delta.getTickDelta(true));
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
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

        ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {
            String text = message.getContent().getString().toLowerCase();
            if (text.contains("gg")) GGEffect.spawn();
        });
    }
}
