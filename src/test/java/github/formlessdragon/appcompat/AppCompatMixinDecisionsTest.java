package github.formlessdragon.appcompat;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import github.formlessdragon.appcompat.mixins.mmce.MixinMEItemInputBus;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.spongepowered.asm.mixin.injection.Redirect;

class AppCompatMixinDecisionsTest {

    @Test
    void usesClassResourcesForEarlyMmceMixinSelection() {
        assertTrue(AppCompatMixinDecisions.isClassPresent(
            "github.kasuminova.mmce.common.tile.MEItemInputBus"));
        assertTrue(AppCompatMixinDecisions.shouldApply(
            "mmce.MixinMEItemInputBus",
            "github.kasuminova.mmce.common.tile.MEItemInputBus"));
        assertFalse(AppCompatMixinDecisions.shouldApply(
            "mmce.MixinMEItemInputBus",
            "github.kasuminova.mmce.common.tile.DoesNotExist"));
    }

    @Test
    void requiresBothItemInputBusAlertRedirects() throws NoSuchMethodException {
        final Method markNoUpdate = MixinMEItemInputBus.class.getDeclaredMethod(
            "appcompat$redirectMarkNoUpdateAlert",
            appeng.api.networking.ticking.ITickManager.class,
            appeng.api.networking.IGridNode.class);
        final Method uploadSettings = MixinMEItemInputBus.class.getDeclaredMethod(
            "appcompat$redirectUploadSettingsAlert",
            appeng.api.networking.ticking.ITickManager.class,
            appeng.api.networking.IGridNode.class);

        final Redirect markRedirect = markNoUpdate.getAnnotation(Redirect.class);
        final Redirect uploadRedirect = uploadSettings.getAnnotation(Redirect.class);
        assertNotNull(markRedirect);
        assertNotNull(uploadRedirect);
        assertArrayEquals(new String[] {"markNoUpdate"}, markRedirect.method());
        assertArrayEquals(new String[] {"uploadSettings"}, uploadRedirect.method());
        assertEquals(1, markRedirect.require());
        assertEquals(1, uploadRedirect.require());
    }
}
