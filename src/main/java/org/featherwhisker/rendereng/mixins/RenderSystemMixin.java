package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;

@Mixin(value = RenderSystem.class, remap = false) // remap = false é importante para RenderSystem
public class RenderSystemMixin {
    /**
     * @author Featherwhisker
     * @reason Not supported in OpenGL ES.
     * A assinatura do método mudou em versões recentes do MC.
     */
    @Overwrite
    public static void logicOp(int op) {
        // Deixar vazio para desativar a funcionalidade.
    }

    /**
     * @author Featherwhisker
     * @reason Not supported in OpenGL ES.
     */
    @Overwrite
    public static void polygonMode(int face, int mode) {
        // Deixar vazio para desativar a funcionalidade.
    }

    /**
     * @author Featherwhisker
     * @reason Not supported in OpenGL ES.
     */
    @Overwrite
    public static void getTexImage(int target, int level, int format, int type, long pixels) {
        // Deixar vazio para desativar a funcionalidade.
    }

    // Movido de GlStateManagerMixin
    @Inject(
            method = "_texParameter(IFI)V", // Usar a assinatura descritora para mais robustez
            at=@At("HEAD"),
            cancellable = true
    )
    private static void onTexParameter(int target, int pname, float param, CallbackInfo ci) {
        // GL_TEXTURE_LOD_BIAS não é suportado no core do OpenGL ES 3.0
        if (target == GL_TEXTURE_2D && pname == GL_TEXTURE_LOD_BIAS) {
            ci.cancel();
        }
    }
}