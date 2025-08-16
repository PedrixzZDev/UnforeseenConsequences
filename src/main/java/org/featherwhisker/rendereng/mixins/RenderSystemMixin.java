package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    // O método texParameter(IIF) foi removido pois não existe mais no RenderSystem do Minecraft 1.21.1
}