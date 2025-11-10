package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.CompiledShader;
import org.featherwhisker.rendereng.main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CompiledShader.class)
public class CompiledShaderMixin {

    /**
     * Intercepta e modifica o código-fonte do shader (o argumento 'source')
     * exatamente antes de ser passado para a função de compilação do OpenGL.
     * Este é o ponto mais preciso e robusto para a injeção.
     */
    @ModifyArg(
            // O alvo é o método estático 'compile' dentro da classe CompiledShader.
            method = "compile",
            at = @At(
                    value = "INVOKE",
                    // O ponto de injeção é a chamada para a função subjacente do RenderSystem.
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;shaderSource(ILjava/lang/String;)V"
            ),
            // Modificamos o segundo argumento (índice 1), que é a String do código-fonte.
            index = 1
    )
    private static String convertShaderSource(String source) {
        // A lógica de conversão existente é reutilizada aqui.
        return main.convertShader(source);
    }
}