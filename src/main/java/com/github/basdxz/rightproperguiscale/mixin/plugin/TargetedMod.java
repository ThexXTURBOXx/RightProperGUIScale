package com.github.basdxz.rightproperguiscale.mixin.plugin;

import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.*;

import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.ITargetedMod.PredicateHelpers.startsWith;

/**
 * List of targeted mods used for mixing loading logic.
 */
@Getter
@RequiredArgsConstructor
public enum TargetedMod implements ITargetedMod {
    OPTIFINE("OptiFine", false, startsWith("optifine"), null),
    BETTER_LOADING_SCREEN("BetterLoadingScreen", false, startsWith("betterloadingscreen"), null),
    BETTER_QUESTING_2("BetterQuesting", true, startsWith("betterquesting"), startsWith("3")),
    BETTER_QUESTING_3("BetterQuesting", true, startsWith("betterquesting"), startsWith("2")),
    ;

    private final String modName;
    private final boolean loadInDevelopment;
    private final Predicate<String> condition;
    private final Predicate<String> version;
}
