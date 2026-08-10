package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class HitSound {
    private static final Random RANDOM = Random.create();

    public static void play(Vec3d pos) {
        if (!VisualsConfig.get("hit_sound")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        float pitch = 0.9f + RANDOM.nextFloat() * 0.2f;
        client.getSoundManager().play(
            PositionedSoundInstance.master(SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), pitch, 0.8f)
        );
    }
}
