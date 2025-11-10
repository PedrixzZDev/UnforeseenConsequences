package org.featherwhisker.rendereng.mixins;

import net.minecraft.MinecraftVersion;
import net.minecraft.client.util.Window;
import org.featherwhisker.rendereng.main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.GL_TRUE;

@Mixin(Window.class)
public class WindowMixin {

    @Inject(method = "<init>", at = @At(value = "INVOKE", remap = false, target = "Lorg/lwjgl/glfw/GLFW;glfwCreateWindow(IILjava/lang/CharSequence;JJ)J", shift = At.Shift.BEFORE))
    private void setGlfwWindowHints(CallbackInfo ci) {
        // CORREÇÃO: O método correto é getName()
        String frameName = "Minecraft " + MinecraftVersion.create().getName();

        main.log.info("Applying OpenGL ES 3.0 context hints...");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_ES_API);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        glfwWindowHint(GLFW_SAMPLES, 0);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_CREATION_API, GLFW_NATIVE_CONTEXT_API);
        glfwWindowHint(GLFW_CONTEXT_NO_ERROR, GL_TRUE);

        main.log.info("OpenGL ES hints applied for window: {}", frameName);
    }
}