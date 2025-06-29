package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.ShaderProgram;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

import static com.mojang.blaze3d.systems.RenderSystem.glShaderSource;
import static org.featherwhisker.rendereng.main.convertShader;
import static org.featherwhisker.rendereng.main.shouldConvertShaders;

// A classe alvo mudou de ShaderStage para ShaderProgram
@Mixin(ShaderProgram.class)
public class ShaderProgramMixin {

    // A chamada para glShaderSource foi movida de GlStateManager para RenderSystem
    @Redirect(
            method = "loadShader(Lnet/minecraft/client/gl/Shader;Ljava/lang/String;Ljava/io/InputStream;Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    // O alvo da chamada agora é RenderSystem
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;glShaderSource(ILjava/util/List;)V"
            )
    )
    private void redirectGlShaderSource(int shader, @NotNull List<String> strings) {
        if (shouldConvertShaders) {
            for (int i = 0; i < strings.size(); i++) {
                var originalShader = strings.get(i);
                strings.set(i, convertShader(originalShader, i));
            }
        }
        // Chama o método original (que agora é RenderSystem.glShaderSource) com a lista modificada
        glShaderSource(shader, strings);
    }
}