package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.GlDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

import static org.featherwhisker.rendereng.main.debugMode;

@Mixin(GlDebug.class)
public class GlDebugMixin {
    
    /**
     * @author Featherwhisker
     * @reason Prevents GL debug logging from being initialized, based on the documented `enableDebug` method.
     * SOLUÇÃO DEFINITIVA: Interceptamos o método de inicialização 'enableDebug' revelado pela documentação.
     */
    @Inject(
            method = "enableDebug(IZLjava/util/Set;)Lnet/minecraft/client/gl/GlDebug;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void cancelDebugInitialization(int verbosity, boolean sync, Set<String> usedGlCaps, CallbackInfoReturnable<GlDebug> cir) {
        // Se o modo debug NÃO estiver ativo, impedimos a inicialização retornando null.
        if (!debugMode) {
            cir.setReturnValue(null);
        }
    }
}