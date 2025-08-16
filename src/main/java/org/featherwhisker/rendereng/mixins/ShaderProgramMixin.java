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
     * Isso funciona independentemente do nome do método que o chama (loadShader, compileShader, etc.).
     */
    @ModifyArg(
            // O método é o construtor, onde os shaders são criados
            method = "<init>", 
            at = @At(
                    value = "INVOKE",
                    // O alvo é a chamada exata para a função do LWJGL
                    target = "Lorg/lwjgl/opengl/GL20;glShaderSource(ILjava/lang/String;)V"
            ),
            // 'index = 1' significa que queremos modificar o segundo argumento (o código fonte)
            index = 1 
    )
    private String modifyShaderSource(String source) {
        if (shouldConvertShaders) {
            return convertShader(source);
        }
        return source;
    }
}