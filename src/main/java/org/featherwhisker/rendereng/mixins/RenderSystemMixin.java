package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_LOD_BIAS;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {

    /**
     * @author Featherwhisker
     * @reason logicOp is not supported in OpenGL ES.
     */
    @Inject(method = "logicOp", at = @At("HEAD"), cancellable = true, remap = false)
    private static void cancelLogicOp(int op, CallbackInfo ci) {
        ci.cancel();
    }

    /**
     * @author Featherwhisker
     * @reason polygonMode is not supported in OpenGL ES.
     */
    @Inject(method = "polygonMode", at = @At("HEAD"), cancellable = true, remap = false)
    private static void cancelPolygonMode(int face, int mode, CallbackInfo ci) {
        ci.cancel();
    }
    
    /**
     * @author Featherwhisker
     * @reason getTexImage is not supported in OpenGL ES.
     */
    @Inject(method = "getTexImage", at = @At("HEAD"), cancellable = true, remap = false)
    private static void cancelGetTexImage(int target, int level, int format, int type, long pixels, CallbackInfo ci) {
        ci.cancel();
    }

    /**
     * @author Featherwhisker
     * @reason GL_TEXTURE_LOD_BIAS is deprecated and causes errors on some GLES drivers.
     */
    @Inject(method = "texParameter(IIF)V", at = @At("HEAD"), cancellable = true, remap = false)
    private static void texParameterFloatInject(int target, int pname, float param, CallbackInfo ci) {
        if (target == GL_TEXTURE_2D && pname == GL_TEXTURE_LOD_BIAS) {
            ci.cancel();
        }
    }
}