package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.Program;
import org.featherwhisker.rendereng.ClientMain;
import org.featherwhisker.rendereng.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(Program.class)
public class ProgramMixin {

    @Redirect(
            method = "createShader",
            at = @At(
                    value = "INVOKE",
                    // Alvo atualizado para RenderSystem.glShaderSource
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;glShaderSource(ILjava/util/List;)V"
            )
    )
    private static void onGlShaderSource(int shader, List<String> sources) {
        if (!Config.enableShaderConversion) {
            RenderSystem.glShaderSource(shader, sources);
            return;
        }

        List<String> convertedSources = sources.stream()
                .map(ClientMain::convertShader)
                .collect(Collectors.toList());

        // Chama o método correto de RenderSystem
        RenderSystem.glShaderSource(shader, convertedSources);
    }
}