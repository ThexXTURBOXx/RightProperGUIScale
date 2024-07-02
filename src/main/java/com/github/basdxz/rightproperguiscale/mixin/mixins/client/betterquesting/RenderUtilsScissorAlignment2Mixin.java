package com.github.basdxz.rightproperguiscale.mixin.mixins.client.betterquesting;

import betterquesting.api.utils.RenderUtils;
import betterquesting.api2.client.gui.misc.GuiRectangle;
import com.github.basdxz.rightproperguiscale.mixin.interfaces.client.minecraft.IScaledResolutionMixin;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * A Mixin for {@link RenderUtils} to fix the offset and scale in {@link RenderUtils#startScissor}.
 * <p>
 * The warnings are supressed as the Minecraft Development plugin for Intellij IDEA fails to recongise the entry points.
 */
@SuppressWarnings("ALL")
@Mixin(value = RenderUtils.class, remap = false)
public abstract class RenderUtilsScissorAlignment2Mixin {
    /**
     * Injects right before the scissor is started, captures the variables in scope.
     * Then creates and caches a new {@link GuiRectangle} with the correct scale and offset.
     *
     * @param ci   mixin callback info
     * @param mc   minecraft instance
     * @param r    scaled resolution
     * @param f    original gui scale
     * @param x    pos x to scissor
     * @param y    pos y to scissor
     * @param w    width to scissor
     * @param h    height to scissor
     */
    @Inject(method = "guiScissor",
            at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glScissor(IIII)V"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1, cancellable = true)
    private static void glScissor(Minecraft mc,
                                  int x,
                                  int y,
                                  int w,
                                  int h,
                                  CallbackInfo ci,
                                  ScaledResolution r,
                                  int f) {
        val scaledResolution = (IScaledResolutionMixin) r;
        val scale = scaledResolution.scaleFactor();
        val scaledHeight = (float) scaledResolution.scaledHeight();

        // Does not change the behavior of the original code, simply makes it scale with the float scale factor.
        val posX = safeRound(x * scale);
        val posY = safeRound((scaledHeight - y - h) * scale);
        val width = safeRound(w * scale);
        val height = safeRound(h * scale);

        GL11.glScissor(posX, posY, width, height);
        ci.cancel();
    }

    /**
     * Rounds a float to the nearest integer, clamping it to be more than or equal to one.
     * <p>
     * Important as otherwise GUI elements may have a scale of zero, which causes problems.
     *
     * @param value the value to round
     * @return the rounded value clamped to >= 1
     */
    private static int safeRound(float value) {
        return Math.max(Math.round(value), 1);
    }
}
