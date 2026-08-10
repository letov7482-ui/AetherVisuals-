package com.aether.visuals.effects;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

public class ArmorHUD {
    /**
     * Рендерит прочность брони в виде цветных полосок.
     */
    public static void render(DrawContext ctx) {
        if (!VisualsConfig.get("armor_hud")) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        int x = 10;
        int y = client.getWindow().getScaledHeight() / 2 - 30;

        for (ItemStack armor : client.player.getArmorItems()) {
            if (!armor.isEmpty() && armor.isDamageable()) {
                float durability = 1f - (float)armor.getDamage() / armor.getMaxDamage();
                int color = getColor(durability);

                int barW = 60;
                int barH = 4;
                int fill = (int)(barW * durability);

                ctx.fill(x, y, x + barW, y + barH, 0xFF333333);
                ctx.fill(x, y, x + fill, y + barH, color);
                y += 6;
            }
        }
    }

    private static int getColor(float pct) {
        if (pct > 0.6f) return 0xFF00FF00;
        if (pct > 0.3f) return 0xFFFFFF00;
        return 0xFFFF0000;
    }
}
