package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;

public class KillSound {
    public static void play() {
        if (!VisualsConfig.get("kill_sound")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        client.getSoundManager().play(
            PositionedSoundInstance.master(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE.value(), 1.0f, 1.0f)
        );
    }
}
