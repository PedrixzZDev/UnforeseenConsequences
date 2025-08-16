package org.featherwhisker.rendereng.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.featherwhisker.rendereng.main.log;
import static org.lwjgl.glfw.GLFW.*;

@Environment(EnvType.CLIENT)
@Mixin(Window.class)
public class WindowMixin {
    // Inject our function right before the window is created
    @Inject(method = "<init>", at = @At(value = "INVOKE", remap = false, target = "org/lwjgl/glfw/GLFW.glfwCreateWindow (IILjava/lang/CharSequence;JJ)J", shift = At.Shift.BEFORE))
    public void injected(CallbackInfo ci) {
        // CORREÇÃO: Usando a constante VERSION_STRING para maior estabilidade
        String frameName = "Minecraft " + SharedConstants.VERSION_STRING;

        // Throw out whatever vanilla tells GLFW
        glfwDefaultWindowHints();

        // Set OpenGL version
        glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_ES_API);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);

        // Rendering related things
        glfwWindowHint(GLFW_REFRESH_RATE, GLFW_DONT_CARE);
        glfwWindowHint(GLFW_SAMPLES, 0);

        // Misc OpenGL hints
        // CORREÇÃO: Usando a constante correta do GLFW
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_CREATION_API, GLFW_NATIVE_CONTEXT_API);
        // CORREÇÃO: Usando a constante correta do GLFW
        glfwWindowHint(GLFW_CONTEXT_NO_ERROR, GLFW_TRUE);

        //Platform Specific
        try {
            String platformName = "Unknown";
            int platform = glfwGetPlatform();

            if (platform == GLFW_PLATFORM_X11) {
                platformName = "*nix with X11";
                glfwWindowHintString(GLFW_X11_CLASS_NAME, "Minecraft");
                glfwWindowHintString(GLFW_X11_INSTANCE_NAME, frameName);
            } else if (platform == GLFW_PLATFORM_WAYLAND) {
                platformName = "*nix with Wayland";
            } else if (platform == GLFW_PLATFORM_COCOA) {
                platformName = "macOS";
                glfwWindowHintString(GLFW_COCOA_FRAME_NAME, frameName);
            } else if (platform == GLFW_PLATFORM_WIN32) {
                platformName = "Windows";
            }

            log.info("Running on: {}", platformName);
            if (!"Windows".equals(platformName)) {
                log.warn("Running on a non-Windows platform. Stricter OpenGL ES drivers (like Mesa on Linux) may have issues.");
            }
        } catch(Exception e) {
            log.warn("Could not detect platform, likely an old GLFW version.", e);
        }
    }
}