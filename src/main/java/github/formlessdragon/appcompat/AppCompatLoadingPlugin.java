package github.formlessdragon.appcompat;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixins;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class AppCompatLoadingPlugin implements IFMLLoadingPlugin {

    static final List<String> MIXIN_CONFIGURATIONS = List.of(
        "appcompat.default.mixin.json",
        "appcompat.mod.mixin.json"
    );

    @Override
    public @Nullable String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public @Nullable String getModContainerClass() {
        return null;
    }

    @Override
    public @Nullable String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> map) {
        final Set<String> loadedConfigurations = new HashSet<>();
        Mixins.getConfigs().forEach(config -> loadedConfigurations.add(config.getName()));
        registerMissingConfigurations(loadedConfigurations, Mixins::addConfiguration);
    }

    @Override
    public @Nullable String getAccessTransformerClass() {
        return null;
    }

    static void registerMissingConfigurations(final Set<String> loadedConfigurations,
                                              final Consumer<String> registrar) {
        for (final String configuration : MIXIN_CONFIGURATIONS) {
            if (loadedConfigurations.add(configuration)) {
                registrar.accept(configuration);
            }
        }
    }
}
