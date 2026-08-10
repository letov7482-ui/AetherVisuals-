package com.aether.visuals.config;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class VisualsConfig {
    private static final Path CONFIG_PATH = Paths.get(System.getProperty("user.dir"), "config/aether_visuals.properties");
    private static final Properties props = new Properties();
    private static final Map<String, Boolean> defaults = new LinkedHashMap<>();

    static {
        defaults.put("hit_particles", true);
        defaults.put("critical_hit", true);
        defaults.put("target_esp", true);
        defaults.put("kill_effect", true);
        defaults.put("damage_numbers", true);
        defaults.put("heal_numbers", true);
        defaults.put("armor_break", true);
        defaults.put("totem_pop", true);
        defaults.put("low_hp_glow", true);
        defaults.put("combo_counter", true);
        defaults.put("trail", true);
        defaults.put("jump_ring", true);
        defaults.put("sneak_smoke", false);
        defaults.put("sprint_spark", true);
        defaults.put("fall_impact", true);
        defaults.put("water_ripple", true);
        defaults.put("crystal_sparkle", true);
        defaults.put("enchant_glow", true);
        defaults.put("golden_apple_shine", true);
        defaults.put("totem_glow", true);
        defaults.put("arrow_trail", true);
        defaults.put("xp_orb_glow", false);
        defaults.put("rain_improved", true);
        defaults.put("snow_particles", true);
        defaults.put("fog_effect", false);
        defaults.put("sun_rays", false);
        defaults.put("moon_glow", true);
        defaults.put("starfield", true);
        defaults.put("leaf_fall", false);
        defaults.put("firefly", false);
        defaults.put("torch_flicker", true);
        defaults.put("fps_counter", true);
        defaults.put("ping_display", false);
        defaults.put("armor_hud", true);
        defaults.put("totem_counter", true);
        defaults.put("potion_timer", true);
        defaults.put("combo_display", false);
        defaults.put("hit_sound", true);
        defaults.put("kill_sound", true);
        defaults.put("low_hp_sound", false);
        defaults.put("gg_effect", true);
        defaults.put("win_dance", true);

        load();
    }

    private static void load() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            if (CONFIG_PATH.toFile().exists()) {
                try (FileInputStream fis = new FileInputStream(CONFIG_PATH.toFile())) {
                    props.load(fis);
                }
            }
        } catch (IOException ignored) {}
    }

    private static void save() {
        try {
            try (FileOutputStream fos = new FileOutputStream(CONFIG_PATH.toFile())) {
                props.store(fos, "Aether Visuals Config");
            }
        } catch (IOException ignored) {}
    }

    public static boolean get(String key) {
        String val = props.getProperty(key);
        if (val != null) return Boolean.parseBoolean(val);
        return defaults.getOrDefault(key, false);
    }

    public static void set(String key, boolean value) {
        props.setProperty(key, String.valueOf(value));
        save();
    }

    public static Map<String, Boolean> getAll() {
        Map<String, Boolean> all = new LinkedHashMap<>();
        for (String key : defaults.keySet()) {
            all.put(key, get(key));
        }
        return all;
    }
}
