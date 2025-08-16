package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {

    /**
     * @author Featherwhisker
     * @reason Not supported in OpenGL ES. Replaced Overwrite with cancellable Inject for better mod compatibility.
     */
    @Inject(method = "logicOp", at = @At("HEAD"), cancellable = true, remap = false)
    public static void cancelLogicOp(GlStateManager.LogicOp op, CallbackInfo ci) {
        ci.cancel();
    }

    /**
     * @author Featherwhisker
     * @reason Not supported in OpenGL ES. Replaced Overwrite with cancellable Inject.
     */
    @Inject(method = "polygonMode", at = @At("HEAD"), cancellable = true, remap = false)
    public static void cancelPolygonMode(int face, int mode, CallbackInfo ci) {
        ci.cancel();
    }
}