// Conteúdo do novo arquivo ShaderProgramMixin.java
package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgram;
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
     */
    @Redirect(
            method = "loadShader(Lnet/minecraft/client/gl/Shader$Type;Ljava/lang/String;Ljava/io/InputStream;)Lnet/minecraft/client/gl/Shader;",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;glShaderSource(ILjava/lang/String;)V"
            ),
            remap = false // A chamada do RenderSystem não é mapeada pelo Yarn
    )
    private static void redirectGlShaderSource(int shader, String source) {
        if (shouldConvertShaders) {
            String convertedSource = convertShader(source);
            RenderSystem.glShaderSource(shader, convertedSource);
        } else {
            RenderSystem.glShaderSource(shader, source);
        }
    }
}