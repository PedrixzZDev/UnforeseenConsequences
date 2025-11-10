package org.featherwhisker.rendereng;

import net.fabricmc.loader.api.FabricLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("unforeseenconsequences.properties");
    private static final Properties properties = new Properties();

    public static boolean enableShaderConversion = true;
    public static boolean enableDebugLogging = false;
    public static String shaderVersion = "300 es";

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                properties.load(Files.newInputStream(CONFIG_PATH));
            } catch (IOException e) {
                main.log.error("Failed to load config file", e);
            }
        }

        enableShaderConversion = Boolean.parseBoolean(properties.getProperty("enable-shader-conversion", "true"));
        enableDebugLogging = Boolean.parseBoolean(properties.getProperty("enable-debug-logging", "false"));
        shaderVersion = properties.getProperty("shader-version", "300 es");

        save();
    }

    public static void save() {
        properties.setProperty("enable-shader-conversion", String.valueOf(enableShaderConversion));
        properties.setProperty("enable-debug-logging", String.valueOf(enableDebugLogging));
        properties.setProperty("shader-version", shaderVersion);

        try {
            properties.store(Files.newOutputStream(CONFIG_PATH), "Unforeseen Consequences Configuration");
        } catch (IOException e) {
            main.log.error("Failed to save config file", e);
        }
    }
}