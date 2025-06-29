package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.GlDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.featherwhisker.rendereng.main.debugMode;

@Mixin(GlDebug.class)
public class GlDebugMixin {
    
    // CORREÇÃO: O método 'info' foi renomeado para 'onGlDebugMessage' e sua assinatura mudou.
    // Usamos o nome correto e os parâmetros correspondentes.
    @Inject(method = "onGlDebugMessage", at = @At("HEAD"), cancellable = true)
    private static void shutup(int source, int type, int id, int severity, int length, long message, long userParam, CallbackInfo ci) {
        if (!debugMode) {
            ci.cancel();
        }
    }
}