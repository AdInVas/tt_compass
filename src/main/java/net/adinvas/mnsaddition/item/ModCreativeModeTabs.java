package net.adinvas.mnsaddition.item;

import net.adinvas.mnsaddition.MnsAddition;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVEMODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MnsAddition.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MNS_ADDITIONS_TAB =CREATIVEMODE_TABS.register("mnsaddition",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(ModItems.TABLET.get()))
                    .title(Component.translatable("creativetab.mnstab"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModItems.TABLET.get());
                    }))
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVEMODE_TABS.register(eventBus);
    }
}
