package pl.caerglad.multiblockhelper.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pl.caerglad.multiblockhelper.MultiblockHelper;
import pl.caerglad.multiblockhelper.item.custom.SelectorWandItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MultiblockHelper.MODID);

    public static final RegistryObject<Item> SELECTOR_WAND = ITEMS.register("selector_wand",
            () -> new SelectorWandItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
