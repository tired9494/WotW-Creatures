package tired9494.wotw_creatures.registry_helpers;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tired9494.wotw_creatures.WotwCreatures;
import tired9494.wotw_creatures.entities.MartianGrunt;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WotwCreatures.MODID);

    public static final RegistryObject<EntityType<MartianGrunt>> MARTIAN_GRUNT = ENTITY_TYPES.register("martian_grunt",
            () -> (EntityType<MartianGrunt>) EntityType.Builder.<MartianGrunt>of(MartianGrunt::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .build(WotwCreatures.ID("martian_grunt").toString())
    );
}
