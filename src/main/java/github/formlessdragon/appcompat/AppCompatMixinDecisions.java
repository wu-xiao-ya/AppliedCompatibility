package github.formlessdragon.appcompat;

import net.minecraftforge.fml.common.Loader;
import net.minecraft.launchwrapper.Launch;

public final class AppCompatMixinDecisions {

    private static boolean enableMMCE = true;
    private static boolean enableGTCEu = true;
    private static boolean enablePackagedAuto = true;
    private static boolean enablePackagingProvider = true;
    private static boolean enableEnderIOAppliedEnergistics = true;
    private static boolean enablePneumaticCraft = true;
    private static boolean enableBuildingGadgets = true;
    private static final String MMCE_PROBE_CLASS = "github.kasuminova.mmce.common.tile.MEItemInputBus";
    private static final String TOP_PROBE_CLASS = "mcjty.theoneprobe.api.IProbeInfo";
    private static final String MEKENG_PROBE_CLASS = "com.mekeng.github.common.me.inventory.impl.GasInventory";
    private static final String GTCEU_PROBE_CLASS = "gregtech.api.GregTechAPI";
    private static final String PACKAGED_AUTO_PROBE_CLASS = "thelm.packagedauto.tile.TilePackager";
    private static final String PACKAGING_PROVIDER_PROBE_CLASS = "thelm.packagingprovider.tile.TilePackagingProvider";
    private static final String ENDER_IO_AE_PROBE_CLASS = "crazypants.enderio.conduit.me.conduit.MEConduit";
    private static final String PNEUMATIC_CRAFT_PROBE_CLASS = "me.desht.pneumaticcraft.common.semiblock.SemiBlockRequester";
    private static final String BUILDING_GADGETS_PROBE_CLASS = "com.direwolf20.buildinggadgets.common.tools.NetworkIO";
    private static final String JEI_PROBE_CLASS = "mezz.jei.api.IModPlugin";

    public static final boolean mmceLoaded = isClassPresent(MMCE_PROBE_CLASS);
    public static final boolean topLoaded = isClassPresent(TOP_PROBE_CLASS);
    public static final boolean mekengLoaded = isClassPresent(MEKENG_PROBE_CLASS);
    public static final boolean gtceuLoaded = isClassPresent(GTCEU_PROBE_CLASS);
    public static final boolean packagedautoLoaded = isClassPresent(PACKAGED_AUTO_PROBE_CLASS);
    public static final boolean packagingproviderLoaded = isClassPresent(PACKAGING_PROVIDER_PROBE_CLASS);
    public static final boolean enderioaeLoaded = isClassPresent(ENDER_IO_AE_PROBE_CLASS);
    public static final boolean pneumaticcraftLoaded = isClassPresent(PNEUMATIC_CRAFT_PROBE_CLASS);
    public static final boolean buildingGadgetsLoaded = isClassPresent(BUILDING_GADGETS_PROBE_CLASS);
    public static final boolean jeiLoaded = isClassPresent(JEI_PROBE_CLASS);

    private AppCompatMixinDecisions() {
    }

    public static void refreshFromEnvironment() {
        enableMMCE = AppCompatConfig.enableMMCE;
        enableGTCEu = AppCompatConfig.enableGTCEu;
        enablePackagedAuto = AppCompatConfig.enablePackagedAuto;
        enablePackagingProvider = AppCompatConfig.enablePackagingProvider;
        enableEnderIOAppliedEnergistics = AppCompatConfig.enableEnderIOAppliedEnergistics;
        enablePneumaticCraft = AppCompatConfig.enablePneumaticCraft;
        enableBuildingGadgets = AppCompatConfig.enableBuildingGadgets;
    }

    public static boolean shouldApply(final String mixinName) {
        return shouldApply(mixinName, null);
    }

    public static boolean shouldApply(final String mixinName, final String targetClassName) {
        final int split = mixinName.indexOf('.');
        if (split < 0) {
            return true;
        }

        final String group = mixinName.substring(0, split);

        return switch (group) {
            case "mmce" -> enableMMCE && shouldApplyMMCE(mixinName, targetClassName);
            case "gtceu" -> enableGTCEu && isTargetForFeaturePresent(targetClassName, GTCEU_PROBE_CLASS);
            case "packagedauto" -> enablePackagedAuto
                && isTargetForFeaturePresent(targetClassName, PACKAGED_AUTO_PROBE_CLASS)
                && shouldApplyPackage(mixinName);
            case "packagingprovider" -> enablePackagingProvider
                && isTargetForFeaturePresent(targetClassName, PACKAGING_PROVIDER_PROBE_CLASS);
            case "enderioae" -> enableEnderIOAppliedEnergistics
                && isTargetForFeaturePresent(targetClassName, ENDER_IO_AE_PROBE_CLASS);
            case "pneumaticcraft" -> enablePneumaticCraft
                && isTargetForFeaturePresent(targetClassName, PNEUMATIC_CRAFT_PROBE_CLASS);
            case "buildinggadgets" -> enableBuildingGadgets
                && isTargetForFeaturePresent(targetClassName, BUILDING_GADGETS_PROBE_CLASS);
            default -> true;
        };
    }

    public static boolean isMMCELoaded() {
        return isModLoaded("modularmachinery") || isClassPresent(MMCE_PROBE_CLASS);
    }

    public static boolean isMekEngLoaded() {
        return isModLoaded("mekeng") || isClassPresent(MEKENG_PROBE_CLASS);
    }

    static boolean isClassPresent(final String className) {
        final String resourceName = className.replace('.', '/') + ".class";
        final ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        if (hasResource(contextLoader, resourceName)) {
            return true;
        }

        if (hasResource(Launch.classLoader, resourceName)) {
            return true;
        }

        return hasResource(AppCompatMixinDecisions.class.getClassLoader(), resourceName);
    }

    private static boolean hasResource(final ClassLoader loader, final String resourceName) {
        return loader != null && loader.getResource(resourceName) != null;
    }

    private static boolean shouldApplyMMCE(final String mixinName, final String targetClassName) {
        if (!isClassPresent(MMCE_PROBE_CLASS) || targetClassName == null || !isClassPresent(targetClassName)) {
            return false;
        }
        if (mixinName.startsWith("mmce.top")) {
            return isClassPresent(TOP_PROBE_CLASS);
        }
        if (mixinName.startsWith("mmce.mekeng")) {
            return isClassPresent(MEKENG_PROBE_CLASS);
        }
        return targetClassName != null && isClassPresent(targetClassName);
    }

    private static boolean isTargetForFeaturePresent(final String targetClassName, final String featureProbeClass) {
        return isClassPresent(featureProbeClass)
            && (targetClassName == null || isClassPresent(targetClassName));
    }

    private static boolean isModLoaded(final String modId) {
        try {
            return Loader.isModLoaded(modId);
        } catch (RuntimeException ignored) {
            return false;
        }
    }

    private static boolean shouldApplyPackage(final String mixinName) {
        if (mixinName.startsWith("packagedauto.jei")) {
            return jeiLoaded;
        }
        return true;
    }
}
