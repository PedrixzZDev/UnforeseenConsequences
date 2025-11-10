package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgram;
import org.featherwhisker.rendereng.main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ShaderProgram.class)
public class ProgramMixin {

    @Redirect(
            // O método em ShaderProgram que cria e compila um shader individual
            method = "loadShader",
            at = @At(
                    value = "INVOKE",
                    // Alvo atualizado para a assinatura correta de RenderSystem.glShaderSource
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;glShaderSource(ILjava/lang/String;)V"
            )
    )
    private static void onGlShaderSource(int shader, String source) {
        // A lógica de conversão agora opera na string única
        String convertedSource = main.convertShader(source);
        RenderSystem.glShaderSource(shader, convertedSource);
    }
}