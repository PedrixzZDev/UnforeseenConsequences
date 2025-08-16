package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.GlDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.featherwhisker.rendereng.main.debugMode;

@Mixin(GlDebug.class)
public class GlDebugMixin {
    // CORREÇÃO: Adicionados os parâmetros do método original para resolver a ambiguidade
    @Inject(method="info(Ljava/lang/String;[Ljava/lang/Object;)V", at=@At("HEAD"), cancellable = true)
    private static void shutup(String message, Object[] args, CallbackInfo ci) {
        if (!debugMode) {
            ci.cancel();
        }
    }
}