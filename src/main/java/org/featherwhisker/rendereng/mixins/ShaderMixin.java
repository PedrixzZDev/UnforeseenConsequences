package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.Shader;
import org.featherwhisker.rendereng.main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Shader.class)
public class ShaderMixin {

    /**
     * Intercepta o código-fonte do shader (source) antes que ele seja compilado.
     * O @ModifyArg é um injetor limpo que nos permite modificar um argumento
     * passado para um método ou construtor.
     *
     * @param source O código-fonte original do shader.
     * @return O código-fonte do shader convertido para GLES.
     */
    @ModifyArg(
            method = "<init>", // O alvo é o construtor da classe Shader
            at = @At(
                    value = "INVOKE",
                    // O ponto de injeção é a chamada para RenderSystem.shaderSource
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;shaderSource(ILjava/lang/String;)V"
            ),
            index = 1 // Modifica o segundo argumento (o source string), que tem índice 1
    )
    private String convertShaderSource(String source) {
        return main.convertShader(source);
    }
}