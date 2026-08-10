package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;

public class PingDisplay {
    /**
     * Индикатор пинга с цветовой индикацией.
     */
    public static void render(DrawContext ctx) {
        if (!VisualsConfig.get("ping_display")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.getNetworkHandler() == null) return;

        PlayerListEntry entry = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid());
        if (entry == null) return;

        int ping = entry.getLatency();
        int color;
        if (ping < 50) color = 0xFF00FF88;
        else if (ping < 100) color = 0xFFFFFF00;
        else color = 0xFFFF0000;

        String text = ping + "ms";
        int x = client.getWindow().getScaledWidth() - 45;
        int y = 15;

        ctx.drawTextWithShadow(client.textRenderer, text, x, y, color);
    }
}
