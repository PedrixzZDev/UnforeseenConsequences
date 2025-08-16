package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static org.featherwhisker.rendereng.main.convertShader;
import static org.featherwhisker.rendereng.main.shouldConvertShaders;

@Mixin(ShaderProgram.class)
public class ShaderProgramMixin {

    /**
     * @author Featherwhisker
     * @reason Modifies the shader source code just before it's passed to the GL driver.
     * ABORDAGEM ROBUSTA: Usamos @ModifyArg para alterar o código do shader diretamente.
     */
    @ModifyArg(
            method = "<init>", 
            at = @At(
                    value = "INVOKE",
                    // CORREÇÃO FINAL: O alvo usa CharSequence, não String.
                    target = "Lorg/lwjgl/opengl/GL20;glShaderSource(ILjava/lang/CharSequence;)V"
            ),
            index = 1 
    )
    private CharSequence modifyShaderSource(CharSequence source) {
        if (shouldConvertShaders) {
            // convertShader espera uma String, então convertemos.
            // A String retornada é um CharSequence válido.
            return convertShader(source.toString());
        }
        return source;
    }
}