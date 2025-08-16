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
     * @reason Prevents GL debug info from spamming the log.
     * CORREÇÃO FINAL: A assinatura correta do método é info(String), sem varargs.
     */
    @Inject(method="info(Ljava/lang/String;)V", at=@At("HEAD"), cancellable = true)
    private static void shutup(String message, CallbackInfo ci) {
        if (!debugMode) {
            ci.cancel();
        }
    }
}