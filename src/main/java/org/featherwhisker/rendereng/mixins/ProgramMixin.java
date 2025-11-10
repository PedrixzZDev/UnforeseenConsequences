package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.platform.GlStateManager; // Importa a classe correta
import net.minecraft.client.gl.ShaderProgram;
import org.featherwhisker.rendereng.main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(ShaderProgram.class)
public class ProgramMixin {

    @Redirect(
            method = "loadShader",
            at = @At(
                    value = "INVOKE",
                    // CORREÇÃO: O alvo correto é _glShaderSource na classe GlStateManager
                    target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glShaderSource(ILjava/util/List;)V"
            )
    )
    private static void onGlShaderSource(int shader, List<String> sources) {
        // Concatena a lista de strings em uma única string para processamento
        String combinedSource = String.join("\n", sources);
        String convertedSource = main.convertShader(combinedSource);

        // A API ainda espera uma List<String>, então colocamos nossa string convertida em uma nova lista
        GlStateManager._glShaderSource(shader, List.of(convertedSource));
    }
}