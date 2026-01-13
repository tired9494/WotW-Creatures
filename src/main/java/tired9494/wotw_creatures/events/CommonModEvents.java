package tired9494.wotw_creatures.events;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tired9494.wotw_creatures.WotwCreatures;
import tired9494.wotw_creatures.entities.MartianSapiens;
import tired9494.wotw_creatures.registry_helpers.ModEntities;

@Mod.EventBusSubscriber(modid= WotwCreatures.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MARTIAN_SAPIENS.get(), MartianSapiens.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.MARTIAN_SAPIENS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MartianSapiens::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
    }
}
