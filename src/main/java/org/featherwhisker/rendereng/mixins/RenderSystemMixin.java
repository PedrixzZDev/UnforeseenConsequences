package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;

@Mixin(value = RenderSystem.class, remap = false)
public abstract class RenderSystemMixin {

    // Os métodos em RenderSystem são 'final', então @Overwrite falha.
    // Usamos @Inject na "cabeça" (HEAD) do método e o cancelamos imediatamente.

    @Inject(method = "logicOp(I)V", at = @At("HEAD"), cancellable = true)
    private static void onLogicOp(int op, CallbackInfo ci) {
        ci.cancel(); // Desativa a funcionalidade
    }

    @Inject(method = "polygonMode(II)V", at = @At("HEAD"), cancellable = true)
    private static void onPolygonMode(int face, int mode, CallbackInfo ci) {
        ci.cancel(); // Desativa a funcionalidade
    }

    @Inject(method = "getTexImage(IIIIJ)V", at = @At("HEAD"), cancellable = true)
    private static void onGetTexImage(int target, int level, int format, int type, long pixels, CallbackInfo ci) {
        ci.cancel(); // Desativa a funcionalidade
    }

    // Este método existe e sua assinatura é (IIF)V -> (int, int, float)void
    @Inject(method = "texParameter(IIF)V", at = @At("HEAD"), cancellable = true)
    private static void onTexParameter(int target, int pname, float param, CallbackInfo ci) {
        // GL_TEXTURE_LOD_BIAS não é suportado no core do OpenGL ES 3.0
        if (target == GL_TEXTURE_2D && pname == GL_TEXTURE_LOD_BIAS) {
            ci.cancel();
        }
    }
}