package tired9494.wotw_creatures.registry_helpers;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tired9494.wotw_creatures.WotwCreatures;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WotwCreatures.MODID);

    private static final List<Supplier<? extends ItemLike>> TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> WOTW_TAB = TABS.register("wotw_creatures_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.wotw_creatures"))
                    .icon(() -> ModItems.MARTIAN_GRUNT_SPAWN_EGG.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        TAB_ITEMS.forEach(item -> output.accept(item.get()));
                    })
                    .build()
    );

    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> item) {
        TAB_ITEMS.add(item);
        return item;
    }
}
