package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.GlDebug;
import org.featherwhisker.rendereng.Config; // Importa a classe Config
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GlDebug.class)
public class GlDebugMixin {
    @Inject(method="info", at=@At("HEAD"), cancellable = true)
    private static void shutup(CallbackInfo ci) {
        // CORREÇÃO: Usa a variável da classe Config
        if (!Config.enableDebugLogging) {
            ci.cancel();
        }
    }
}