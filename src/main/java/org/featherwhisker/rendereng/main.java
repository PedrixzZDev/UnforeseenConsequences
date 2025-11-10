package org.featherwhisker.rendereng;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class main implements ModInitializer {
    public static final Logger log = LoggerFactory.getLogger("UnforeseenConsequences");

    @Override
    public void onInitialize() {
        Config.load();
        log.info("Prepare for unforeseen consequences... configuration loaded.");
    }

    public static String convertShader(String source) {
        if (!Config.enableShaderConversion) {
            return source;
        }

        // A versão 150 é comum em shaders do core do Minecraft
        if (!source.contains("#version 150")) {
            return source; // Não modifica shaders que não são da versão 150
        }
        
        String header = "#version " + Config.shaderVersion + "\n" +
                        "precision mediump float;\n" +
                        "precision mediump int;\n\n";

        return source
                .replace("#version 150", header)
                .replace("texCoord2 = UV2;", "texCoord2 = vec2(UV2);")
                .replace("uv / 256.0", "vec2(uv) / 256.0");
    }
}