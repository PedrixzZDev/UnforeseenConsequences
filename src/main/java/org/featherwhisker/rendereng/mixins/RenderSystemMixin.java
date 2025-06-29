package org.featherwhisker.rendereng.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;

// CORREÇÃO IMPORTANTE: Removido 'remap = false'.
// A maioria dos métodos em RenderSystem SÃO remapeados pelo Yarn. Forçar 'remap = false'
// faz com que o Mixin procure pelos nomes originais (obfuscados) ou nomes de desenvolvimento, que não correspondem.
// Deixar o Loom decidir a remapeação é a abordagem correta.
@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {

    // CORREÇÃO: 'logicOp' foi renomeado para 'setShaderLogicOp'
    @Inject(method = "setShaderLogicOp", at = @At("HEAD"), cancellable = true)
    private static void onSetShaderLogicOp(int op, CallbackInfo ci) {
        ci.cancel();
    }

    // O nome 'polygonMode' está correto, o problema era o 'remap = false'.
    @Inject(method = "polygonMode", at = @At("HEAD"), cancellable = true)
    private static void onPolygonMode(int face, int mode, CallbackInfo ci) {
        ci.cancel();
    }
    
    // CORREÇÃO: 'getTexImage' foi completamente removido do RenderSystem.
    // Não há mais um método equivalente direto para mixar.
    // Esta injeção foi removida.

    // O nome 'texParameter' está correto, o problema era o 'remap = false'.
    @Inject(method = "texParameter(IIF)V", at = @At("HEAD"), cancellable = true)
    private static void onTexParameter(int target, int pname, float param, CallbackInfo ci) {
        if (target == GL_TEXTURE_2D && pname == GL_TEXTURE_LOD_BIAS) {
            ci.cancel();
        }
    }
}