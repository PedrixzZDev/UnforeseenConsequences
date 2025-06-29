package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.ShaderProgram;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.List;

// Importação corrigida: A função agora vem da biblioteca LWJGL diretamente.
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.featherwhisker.rendereng.main.convertShader;
import static org.featherwhisker.rendereng.main.shouldConvertShaders;

@Mixin(ShaderProgram.class)
public class ShaderProgramMixin {

    // A classe alvo do redirect está correta, mas a forma de chamar o método precisa mudar.
    // O Redirect ainda é necessário para interceptar o código, mas a chamada dentro dele será para a classe certa.
    @Redirect(
            method = "loadShader(Lnet/minecraft/client/gl/Shader;Ljava/lang/String;Ljava/io/InputStream;Ljava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
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
        // Chamada corrigida: Usando o método estático importado de org.lwjgl.opengl.GL20
        glShaderSource(shader, strings);
    }
}