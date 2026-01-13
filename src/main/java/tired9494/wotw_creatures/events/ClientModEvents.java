package tired9494.wotw_creatures.events;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tired9494.wotw_creatures.WotwCreatures;
import tired9494.wotw_creatures.client.models.MartianSapiensModel;
import tired9494.wotw_creatures.client.renderers.MartianSapiensRenderer;
import tired9494.wotw_creatures.registry_helpers.ModEntities;

@Mod.EventBusSubscriber(modid = WotwCreatures.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType)ModEntities.MARTIAN_SAPIENS.get(), MartianSapiensRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MartianSapiensModel.LAYER_LOCATION, MartianSapiensModel::createBodyLayer);
    }
}
