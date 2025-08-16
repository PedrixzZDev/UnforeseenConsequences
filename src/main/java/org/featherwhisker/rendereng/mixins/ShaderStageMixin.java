package org.featherwhisker.rendereng.mixins;

import net.minecraft.client.gl.ShaderStage;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

import static com.mojang.blaze3d.platform.GlStateManager.glShaderSource;
import static org.featherwhisker.rendereng.main.*;

@Mixin(ShaderStage.class)
public class ShaderStageMixin {
	@Redirect(
			method="load",
			at=@At(
					value="INVOKE",
					target="com/mojang/blaze3d/platform/GlStateManager.glShaderSource (ILjava/util/List;)V"
			)
	)
	private static void glShaderSourceIntercept(int shader, @NotNull List<String> sources) {
		if (shouldConvertShaders) {
			for (int i = 0; i < sources.size(); i++) {
				sources.set(i, convertShader(sources.get(i)));
			}
		}
		glShaderSource(shader, sources);
	}
}