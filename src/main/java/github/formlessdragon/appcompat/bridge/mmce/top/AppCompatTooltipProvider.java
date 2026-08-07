package github.formlessdragon.appcompat.bridge.mmce.top;

import ae2.api.integrations.igtooltip.BaseClassRegistration;
import ae2.api.integrations.igtooltip.TooltipProvider;
import github.formlessdragon.appcompat.AppCompatMixinDecisions;
import github.kasuminova.mmce.common.block.appeng.BlockMEMachineComponent;
import github.kasuminova.mmce.common.tile.base.MEMachineComponent;

@SuppressWarnings("ALL")
public final class AppCompatTooltipProvider implements TooltipProvider {

    @Override
    public void registerBlockEntityBaseClasses(final BaseClassRegistration registration) {
        if (AppCompatMixinDecisions.isMMCELoaded()) {
            registration.addBaseBlockEntity(MEMachineComponent.class, BlockMEMachineComponent.class);
        }
    }
}
