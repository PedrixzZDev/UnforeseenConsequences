package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.io.InputStream;

import static org.featherwhisker.rendereng.main.convertShader;
import static org.featherwhisker.rendereng.main.shouldConvertShaders;

@Mixin(ShaderProgram.class)
public class ShaderProgramMixin {

    // CORREÇÃO: O método 'loadShader' foi renomeado para 'loadShader(Shader.Type, String, InputStream)'
    // Simplificamos o seletor para apenas o nome, pois ele não é sobrecarregado.
    @ModifyVariable(
            method = "loadShader(Lnet/minecraft/client/gl/Shader$Type;Ljava/lang/String;Ljava/io/InputStream;)I",
            at = @At(value = "STORE"),
            ordinal = 0
    )
    private static String modifyShaderSource(String originalShaderCode) {
        if (shouldConvertShaders) {
            return convertShader(originalShaderCode, 0);
        }
        return originalShaderCode;
    }
}