package org.featherwhisker.rendereng;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.regex.Pattern;

public class main implements ModInitializer {
    public static Logger log = LoggerFactory.getLogger("rendereng");
    public static boolean shouldConvertShaders = true;
    public static boolean debugMode = false;
    public static String shaderVersion = "300 es";

    // Usar Regex Pattern para uma substituição mais segura e evitar falsos positivos.
    private static final Pattern SHADER_VERSION_PATTERN = Pattern.compile("#version\\s+150");

    @Override
    public void onInitialize() {
        log.info("Unforeseen Consequences is preparing to modify the rendering pipeline...");
    }

    public static String convertShader(String str) {
        if (!shouldConvertShaders) {
            return str;
        }

        String append = "precision mediump float;\nprecision mediump int;\n\n";

        // Substitui a diretiva #version de forma segura usando o Pattern
        String converted = SHADER_VERSION_PATTERN.matcher(str).replaceFirst("#version " + shaderVersion);

        // Se a substituição ocorreu, adiciona o cabeçalho de precisão logo após
        if (!converted.equals(str)) {
            converted = converted.replaceFirst("(#version\\s+" + shaderVersion + ")", "$1\n" + append);
        }
        
        // Mantém as outras substituições específicas
        return converted
                .replaceAll("texCoord2 = UV2;","texCoord2 = vec2(UV2);")
                .replaceAll("uv / 256.0","vec2(uv) / 256.0");
    }
}