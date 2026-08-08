package github.formlessdragon.appcompat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;

class AppCompatLoadingPluginTest {

    @Test
    void registersBothMixinConfigurationsInProduction() {
        final List<String> registered = new ArrayList<>();

        AppCompatLoadingPlugin.registerMissingConfigurations(new HashSet<>(), registered::add);

        assertEquals(List.of(
            "appcompat.default.mixin.json",
            "appcompat.mod.mixin.json"
        ), registered);
    }

    @Test
    void skipsConfigurationsThatAreAlreadyRegistered() {
        final List<String> registered = new ArrayList<>();
        final HashSet<String> loaded = new HashSet<>();
        loaded.add("appcompat.default.mixin.json");

        AppCompatLoadingPlugin.registerMissingConfigurations(loaded, registered::add);

        assertEquals(List.of("appcompat.mod.mixin.json"), registered);
    }
}
