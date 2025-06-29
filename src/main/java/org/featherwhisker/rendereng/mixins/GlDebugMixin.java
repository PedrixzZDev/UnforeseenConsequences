package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.GlDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.featherwhisker.rendereng.main.debugMode;

@Mixin(GlDebug.class)
public class GlDebugMixin {
    // Adicionamos a assinatura completa do método (descritor) para remover a ambiguidade.
    // (IIIIJJJ)V significa: um método com parâmetros (int, int, int, int, long, long, long) que retorna void.
    @Inject(method = "info(IIIIJJJ)V", at = @At("HEAD"), cancellable = true)
    private static void shutup(int source, int type, int id, int severity, long message, long userParam, CallbackInfo ci) {
        if (!debugMode) {
            ci.cancel();
        }
    }
}