package github.formlessdragon.appcompat.mixins.mmce;

import github.kasuminova.mmce.common.util.AEFluidInventoryUpgradeable;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AEFluidInventoryUpgradeable.class, remap = false)
public abstract class MixinAEFluidInventoryUpgradeable implements IFluidHandler {
}
