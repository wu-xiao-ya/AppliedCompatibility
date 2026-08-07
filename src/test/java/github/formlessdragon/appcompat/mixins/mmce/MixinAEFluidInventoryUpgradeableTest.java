package github.formlessdragon.appcompat.mixins.mmce;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import github.kasuminova.mmce.common.util.AEFluidInventoryUpgradeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.junit.jupiter.api.Test;

class MixinAEFluidInventoryUpgradeableTest {

    @Test
    void exposesTheForgeFluidHandlerContract() {
        assertTrue(IFluidHandler.class.isAssignableFrom(MixinAEFluidInventoryUpgradeable.class));
    }

    @Test
    void targetAlreadyImplementsEveryFluidHandlerMethod() throws NoSuchMethodException {
        for (Method contractMethod : IFluidHandler.class.getMethods()) {
            Method targetMethod = AEFluidInventoryUpgradeable.class.getMethod(
                contractMethod.getName(),
                contractMethod.getParameterTypes());
            assertTrue(contractMethod.getReturnType().isAssignableFrom(targetMethod.getReturnType()));
        }
    }

    @Test
    void mixinIsRegistered() throws IOException {
        try (var stream = getClass().getResourceAsStream("/appcompat.mod.mixin.json")) {
            assertNotNull(stream);
            String config = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            assertTrue(config.contains("\"mmce.MixinAEFluidInventoryUpgradeable\""));
        }
    }
}
