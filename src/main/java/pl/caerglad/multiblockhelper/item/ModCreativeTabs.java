package pl.caerglad.multiblockhelper.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import pl.caerglad.multiblockhelper.MultiblockHelper;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MultiblockHelper.MODID);

    public static final RegistryObject<CreativeModeTab> MB_HELPER_TAB = CREATIVE_TABS.register("mb_helper_tag",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SELECTOR_WAND.get()))
                    .title(Component.translatable("multiblockhelper.creative_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.SELECTOR_WAND.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}
