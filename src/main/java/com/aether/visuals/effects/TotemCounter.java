package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;

public class TotemCounter {
    /**
     * Показывает количество тотемов в инвентаре.
     */
    public static void render(DrawContext ctx) {
        if (!VisualsConfig.get("totem_counter")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        int count = client.player.getInventory().count(Items.TOTEM_OF_UNDYING);
        String text = "🛡️ " + count;

        int x = 10;
        int y = client.getWindow().getScaledHeight() / 2 + 10;

        ctx.drawTextWithShadow(client.textRenderer, text, x, y, 0xFFFFD700);
    }
}
