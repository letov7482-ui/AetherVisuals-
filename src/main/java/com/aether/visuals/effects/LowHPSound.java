package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;

public class LowHPSound {
    private static long lastBeat = 0;
    private static final long BEAT_INTERVAL = 800;

    public static void tick() {
        if (!VisualsConfig.get("low_hp_sound")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        float hp = client.player.getHealth() / client.player.getMaxHealth();
        if (hp > 0.3f) return;

        long now = System.currentTimeMillis();
        if (now - lastBeat < BEAT_INTERVAL) return;
        lastBeat = now;

        float speed = 1.5f - hp;
        client.getSoundManager().play(
            PositionedSoundInstance.master(SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM.value(), speed, 0.6f)
        );
    }
}
