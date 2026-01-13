package tired9494.wotw_creatures.registry_helpers;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tired9494.wotw_creatures.WotwCreatures;

import static tired9494.wotw_creatures.registry_helpers.ModCreativeTab.addToTab;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WotwCreatures.MODID);

    public static final RegistryObject<Item> MARTIAN_FLESH = ITEMS.register("martian_flesh",
            () -> new Item(new Item.Properties().food(Items.ROTTEN_FLESH.getFoodProperties())));

    public static final RegistryObject<ForgeSpawnEggItem> MARTIAN_GRUNT_SPAWN_EGG = addToTab(ITEMS.register("martian_grunt_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MARTIAN_GRUNT, 0x958f6e, 0x020202, new Item.Properties())));
}
