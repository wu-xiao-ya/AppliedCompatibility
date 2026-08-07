package github.formlessdragon.appcompat.proxy;

import github.formlessdragon.appcompat.AppCompatConfig;
import github.formlessdragon.appcompat.AppCompatMixinDecisions;
import github.formlessdragon.appcompat.bridge.ae.AppCompatAEHooks;
import github.formlessdragon.appcompat.bridge.mmce.AppCompatMMCEHooks;
import github.formlessdragon.appcompat.bridge.mmce.mekeng.AppCompatMekEngInitHooks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
    }

    public void init(FMLInitializationEvent event) {
        AppCompatAEHooks.init();
        if (AppCompatConfig.enableMMCE) {
            if (AppCompatMixinDecisions.isMMCELoaded()) AppCompatMMCEHooks.init();
            if (AppCompatMixinDecisions.isMMCELoaded() && AppCompatMixinDecisions.isMekEngLoaded()) {
                AppCompatMekEngInitHooks.init();
            }
        }
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
