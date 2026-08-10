package com.aether.visuals.gui;

import com.aether.visuals.config.VisualsConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import java.util.*;

public class VisualsScreen extends Screen {
    private final Map<String, Boolean> settings = new LinkedHashMap<>();
    private final Map<String, String> descriptions = new LinkedHashMap<>();
    private final Map<String, String> icons = new LinkedHashMap<>();
    private List<String> filteredKeys = new ArrayList<>();
    private TextFieldWidget searchField;
    private String searchText = "";
    private int scrollY = 0;
    private final Random rand = new Random();
    private final float[] px = new float[40], py = new float[40], ps = new float[40];

    public VisualsScreen() {
        super(Text.literal("Aether Visuals"));
        for (int i = 0; i < 40; i++) {
            px[i] = rand.nextFloat() * 1000;
            py[i] = rand.nextFloat() * 1000;
            ps[i] = 0.1f + rand.nextFloat() * 0.3f;
        }
        initDescriptions();
        refreshSettings();
    }

    private void initDescriptions() {
        descriptions.put("hit_particles", "Yellow stars on hit");
        icons.put("hit_particles", "⭐");
        descriptions.put("critical_hit", "Purple stars + lightning on crit");
        icons.put("critical_hit", "💜");
        descriptions.put("target_esp", "4 ghosts circling target");
        icons.put("target_esp", "👻");
        descriptions.put("kill_effect", "Explosion on kill");
        icons.put("kill_effect", "💀");
        descriptions.put("damage_numbers", "Floating damage numbers");
        icons.put("damage_numbers", "🔢");
        descriptions.put("totem_pop", "Golden effect on totem");
        icons.put("totem_pop", "🛡️");
        descriptions.put("trail", "Particle trail while running");
        icons.put("trail", "💨");
        descriptions.put("crystal_sparkle", "Crystals shimmer");
        icons.put("crystal_sparkle", "💎");
        descriptions.put("fps_counter", "Neon FPS counter");
        icons.put("fps_counter", "📊");
        descriptions.put("armor_hud", "Armor durability HUD");
        icons.put("armor_hud", "🦺");
        descriptions.put("hit_sound", "Satisfying hit sound");
        icons.put("hit_sound", "🔔");
        descriptions.put("gg_effect", "Fireworks on 'gg'");
        icons.put("gg_effect", "🎆");
        // Остальные 50+ описаний добавляются так же
        for (String key : VisualsConfig.getAll().keySet()) {
            descriptions.putIfAbsent(key, key.replace("_", " "));
            icons.putIfAbsent(key, "✨");
        }
    }

    private void refreshSettings() {
        settings.clear();
        settings.putAll(VisualsConfig.getAll());
        applyFilter();
    }

    private void applyFilter() {
        filteredKeys = new ArrayList<>();
        for (String key : settings.keySet()) {
            if (searchText.isEmpty() || key.toLowerCase().contains(searchText.toLowerCase())
                || descriptions.getOrDefault(key, "").toLowerCase().contains(searchText.toLowerCase())) {
                filteredKeys.add(key);
            }
        }
    }

    @Override
    protected void init() {
        int cx = this.width / 2;

        searchField = new TextFieldWidget(textRenderer, cx - 100, 38, 200, 16, Text.literal("Search"));
        searchField.setMaxLength(50);
        searchField.setText(searchText);
        searchField.setChangedListener(text -> {
            searchText = text;
            applyFilter();
            scrollY = 0;
            clearChildren();
            init();
        });
        addSelectableChild(searchField);
        setInitialFocus(searchField);

        int y = 60;
        int visible = (height - 100) / 22;
        for (int i = scrollY; i < Math.min(filteredKeys.size(), scrollY + visible); i++) {
            String key = filteredKeys.get(i);
            boolean val = settings.get(key);
            String icon = icons.getOrDefault(key, "✨");
            String desc = descriptions.getOrDefault(key, key);
            String label = (val ? "✅" : "❌") + " " + icon + " " + desc;
            int btnY = y + (i - scrollY) * 22;
            addDrawableChild(ButtonWidget.builder(Text.literal(label), btn -> {
                VisualsConfig.set(key, !val);
                refreshSettings();
                clearChildren();
                init();
            }).dimensions(10, btnY, width - 20, 20).build());
        }

        if (scrollY > 0) {
            addDrawableChild(ButtonWidget.builder(Text.literal("▲"), btn -> {
                scrollY = Math.max(0, scrollY - 5);
                clearChildren();
                init();
            }).dimensions(width - 20, 60, 15, 15).build());
        }
        if (scrollY + visible < filteredKeys.size()) {
            addDrawableChild(ButtonWidget.builder(Text.literal("▼"), btn -> {
                scrollY = Math.min(filteredKeys.size() - visible, scrollY + 5);
                clearChildren();
                init();
            }).dimensions(width - 20, 80, 15, 15).build());
        }

        addDrawableChild(ButtonWidget.builder(Text.literal("Close"), btn -> close())
            .dimensions(cx - 30, height - 30, 60, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        renderBackground(ctx, mx, my, delta);
        for (int x = 0; x < width; x += 32) for (int y = 0; y < height; y += 32)
            ctx.fill(x, y, x + 1, y + 1, 0xFF1A1A2E);
        PulseEffects.tick();
        for (int i = 0; i < 40; i++) {
            py[i] -= ps[i];
            if (py[i] < 0) { py[i] = height; px[i] = rand.nextFloat() * width; }
            ctx.fill((int) px[i], (int) py[i], (int) px[i] + 2, (int) py[i] + 2, 0xFF6C63FF);
        }
        PulseEffects.renderGlitchText(ctx, textRenderer, "AETHER VISUALS", width / 2, 12, 0xFF6C63FF);
        ctx.drawCenteredTextWithShadow(textRenderer, "Beauty. Smoothness. Detail. Shock.", width / 2, 26, 0xFF888888);
        super.render(ctx, mx, my, delta);
    }
                            }
