package org.featherwhisker.rendereng.mixins;

// CORREÇÃO FINAL: A classe Shader foi renomeada para Program.
import net.minecraft.client.gl.Program;
import net.minecraft.client.gl.ShaderProgram;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.InputStream;

import static org.featherwhisker.rendereng.main.convertShader;
import static org.featherwhisker.rendereng.main.shouldConvertShaders;

@Mixin(ShaderProgram.class)
public class ShaderProgramMixin {

    /**
     * @author Featherwhisker
     * @reason Intercept shader source code before compilation to convert it from GLSL 150 to GLSL 300 ES.
     */
    @Redirect(
            // CORREÇÃO FINAL: A assinatura do método agora usa Program e Program.Type.
            method = "loadShader(Lnet/minecraft/client/gl/Program$Type;Ljava/lang/String;Ljava/io/InputStream;Ljava/lang/String;)Lnet/minecraft/client/gl/Program;",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/opengl/GL20;glShaderSource(ILjava/lang/String;)V"
            )
    )
    private static void redirectGlShaderSource(int shader, String source) {
        if (shouldConvertShaders) {
            String convertedSource = convertShader(source);
            GL20.glShaderSource(shader, convertedSource);
        } else {
            GL20.glShaderSource(shader, source);
        }
    }
}