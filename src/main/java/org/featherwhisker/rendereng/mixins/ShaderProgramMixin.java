package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.ShaderProgram;
import org.lwjgl.opengl.GL20; // CORREÇÃO: Importação necessária
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static org.featherwhisker.rendereng.main.convertShader;
import static org.featherwhisker.rendereng.main.shouldConvertShaders;

@Mixin(ShaderProgram.class)
public class ShaderProgramMixin {

    /**
     * @author Featherwhisker
     * @reason Intercept shader source code before compilation to convert it from GLSL 150 to GLSL 300 ES.
     *         The target is the direct LWJGL call, as RenderSystem no longer wraps glShaderSource.
     */
    @Redirect(
            method = "loadShader(Lnet/minecraft/client/gl/Shader$Type;Ljava/lang/String;Ljava/io/InputStream;)Lnet/minecraft/client/gl/Shader;",
            at = @At(
                    value = "INVOKE",
                    // CORREÇÃO: O alvo agora é a chamada de baixo nível do LWJGL
                    target = "Lorg/lwjgl/opengl/GL20;glShaderSource(ILjava/lang/CharSequence;)V"
            )
    )
    private static void redirectGlShaderSource(int shader, CharSequence source) {
        if (shouldConvertShaders) {
            String convertedSource = convertShader(source.toString());
            // CORREÇÃO: Chamando o método original que estávamos redirecionando
            GL20.glShaderSource(shader, convertedSource);
        } else {
            GL20.glShaderSource(shader, source);
        }
    }
}