package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {

    /**
     * @author Featherwhisker
     * @reason A operação lógica (logicOp) não é uma funcionalidade principal no OpenGL ES 3.0.
     * Desativado para compatibilidade.
     */
    @Overwrite
    public static void logicOp(int op) {
        // Ignorado
    }

    /**
     * @author Featherwhisker
     * @reason glPolygonMode não está disponível no OpenGL ES 3.0.
     * A renderização de wireframe não funcionará.
     */
    @Overwrite
    public static void polygonMode(int face, int mode) {
        // Ignorado
    }
}