package tired9494.wotw_creatures.registry_helpers;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tired9494.wotw_creatures.WotwCreatures;
import tired9494.wotw_creatures.entities.MartianSapiens;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WotwCreatures.MODID);

    public static final RegistryObject<EntityType<MartianSapiens>> MARTIAN_SAPIENS = ENTITY_TYPES.register("martian_sapiens",
            () -> (EntityType<MartianSapiens>) EntityType.Builder.<MartianSapiens>of(MartianSapiens::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .build(WotwCreatures.ID("martian_sapiens").toString())
    );
}
