package com.github.basdxz.rightproperguiscale.mixin.plugin;

import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.ITargetedMod;
import com.github.basdxz.rightproperguiscale.RightProperGUIScale;
import lombok.*;

import java.util.List;
import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.*;
import static com.falsepattern.lib.mixin.IMixin.Side.CLIENT;
import static com.github.basdxz.rightproperguiscale.mixin.plugin.TargetedMod.*;


/**
 * Mixin declaration enum which is loaded before the Forge injection point: {@link RightProperGUIScale}.
 * <p>
 * Sponge Mixins handles the loading of the mixins, and the location is provided to it by the buildscript.
 */
@Getter
@RequiredArgsConstructor
public enum Mixin implements IMixin {
    /**
     * Always required Mixins.
     */
    MinecraftGUIScaleConfigLoading(CLIENT, always(), "minecraft.MinecraftGUIScaleConfigLoading"),
    GameSettingsInitGUIScale(CLIENT, always(), "minecraft.GameSettingsInitGUIScaleMixin"),
    GameSettingGUIScaleSliderMixin(CLIENT, always(), "minecraft.GameSettingGUIScaleSliderMixin"),
    GameSettingGUIScaleLoadSaveMixin(CLIENT, always(), "minecraft.GameSettingGUIScaleLoadSaveMixin"),
    ScaledResolutionPatchMixin(CLIENT, always(), "minecraft.ScaledResolutionPatchMixin"),
    GuiEnchantmentBookAlignmentMixin(CLIENT, always(), "minecraft.GuiEnchantmentBookAlignmentMixin"),
    LoadingScreenRendererSizeMixin(CLIENT, always(), "minecraft.LoadingScreenRendererSizeMixin"),
    GuiNewChatMouseOffsetMixin(CLIENT, always(), "minecraft.GuiNewChatMouseOffsetMixin"),
    /**
     * Better Loading Screen avoiding Mixins.
     */
    MinecraftLoadingScreenSizeMixin(CLIENT, avoid(BETTER_LOADING_SCREEN), "minecraft.MinecraftLoadingScreenSizeMixin"),
    /**
     * OptiFine avoiding Mixins.
     */
    GuiVideoSettingsGUIScaleUpdateMixin(CLIENT, avoid(OPTIFINE), "minecraft.GuiVideoSettingsGUIScaleUpdateMixin"),
    /**
     * OptiFine specific Mixins.
     */
    GuiScreenGUIScaleUpdateMixin(CLIENT, require(OPTIFINE), "optifine.GuiScreenGUIScaleUpdateMixin"),
    TooltipProviderOptionsGUIScaleLangMixin(CLIENT, require(OPTIFINE), "optifine.TooltipProviderOptionsGUIScaleLangMixin"),
    /**
     * Better Questing specific Mixins.
     */
    //RenderUtilsScissorAlignment3Mixin(CLIENT, require(BETTER_QUESTING_3), "betterquesting.RenderUtilsScissorAlignment3Mixin"),
    RenderUtilsScissorAlignment2Mixin(CLIENT, require(BETTER_QUESTING_2), "betterquesting.RenderUtilsScissorAlignment2Mixin"),
    ;

    private final Side side;
    private final Predicate<List<ITargetedMod>> filter;
    private final String mixin;
}
