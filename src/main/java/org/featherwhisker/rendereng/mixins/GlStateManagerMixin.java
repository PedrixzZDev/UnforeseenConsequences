package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_LOD_BIAS;

@Mixin(GlStateManager.class)
public class GlStateManagerMixin {

    /**
     * @author Featherwhisker
     * @reason Not supported in OpenGL ES. Replaced Overwrite with cancellable Inject for better mod compatibility.
     */
    @Inject(method = "_logicOp", at = @At("HEAD"), cancellable = true)
    private static void cancelLogicOp(int op, CallbackInfo ci) {
        ci.cancel();
    }

    /**
     * @author Featherwhisker
     * @reason Not supported in OpenGL ES. Replaced Overwrite with cancellable Inject.
     */
    @Inject(method = "_getTexImage", at = @At("HEAD"), cancellable = true)
    private static void cancelGetTexImage(int target, int level, int format, int type, long pixels, CallbackInfo ci) {
        ci.cancel();
    }

    /**
     * @author Featherwhisker
     * @reason Not supported in OpenGL ES. Replaced Overwrite with cancellable Inject.
     */
    @Inject(method = "_polygonMode", at = @At("HEAD"), cancellable = true)
    private static void cancelPolygonMode(int face, int mode, CallbackInfo ci) {
        ci.cancel();
    }

    /**
     * @author Featherwhisker
     * @reason Not supported in OpenGL ES. Replaced Overwrite with cancellable Inject.
     */
    @Inject(method = "_glDrawPixels", at = @At("HEAD"), cancellable = true)
    private static void cancelGlDrawPixels(int width, int height, int format, int type, long pixels, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "_texParameter(IIF)V", // Especificando a assinatura para evitar ambiguidade
            at=@At("HEAD"),
            cancellable = true
    )
    private static void texParameterFloatInject(int target, int pname, float param, CallbackInfo ci) {
        // This parameter is deprecated and causes errors on some GLES drivers.
        if (target == GL_TEXTURE_2D && pname == GL_TEXTURE_LOD_BIAS) {
            ci.cancel();
        }
    }
}