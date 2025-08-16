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
     * @reason Prevents GL debug info from spamming the log by cancelling the wrapper method.
     * CORREÇÃO DEFINITIVA: A assinatura correta do wrapper do Minecraft é (int, int, int, int, String).
     */
    @Inject(method = "info(IIIILjava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private static void shutup(int source, int type, int id, int severity, String message, CallbackInfo ci) {
        if (!debugMode) {
            ci.cancel();
        }
    }
}