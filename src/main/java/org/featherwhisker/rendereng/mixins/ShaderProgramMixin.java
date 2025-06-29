package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static org.featherwhisker.rendereng.main.convertShader;
import static org.featherwhisker.rendereng.main.shouldConvertShaders;

@Mixin(ShaderProgram.class)
public class ShaderProgramMixin {

    /**
     * Esta é uma abordagem muito mais robusta. Em vez de redirecionar uma chamada de função,
     * nós interceptamos a variável que contém o código do shader logo depois que ela é lida do arquivo.
     * Isso nos permite modificar o código do shader antes que ele seja compilado,
     * independentemente de como o jogo decide chamar as funções OpenGL.
     */
    @ModifyVariable(
            // O método que carrega os shaders
            method = "loadShader(Lnet/minecraft/client/gl/Shader$Type;Ljava/lang/String;Ljava/io/InputStream;Ljava/lang/String;)I",
            // Intercepta a primeira variável String (ordinal = 0) logo após ser armazenada (STORE)
            at = @At(value = "STORE"),
            ordinal = 0
    )
    private static String modifyShaderSource(String originalShaderCode) {
        if (shouldConvertShaders) {
            // A nossa função de conversão recebe o código completo do shader
            return convertShader(originalShaderCode, 0);
        }
        return originalShaderCode;
    }
}