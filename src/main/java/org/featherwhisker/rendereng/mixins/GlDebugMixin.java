package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.GlDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.featherwhisker.rendereng.main.debugMode;

@Mixin(GlDebug.class)
public class GlDebugMixin {
    
    /**
     * @author Featherwhisker
     * @reason Prevents GL debug info from spamming the log by cancelling the main debug callback.
     * CORREÇÃO DEFINITIVA: A assinatura correta é a do callback do OpenGL.
     */
    @Inject(method = "info", at = @At("HEAD"), cancellable = true)
    private static void shutup(int source, int type, int id, int severity, int length, long message, long userParam, CallbackInfo ci) {
        if (!debugMode) {
            ci.cancel();
        }
    }
}