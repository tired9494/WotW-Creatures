package tired9494.wotw_creatures.client.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import tired9494.wotw_creatures.WotwCreatures;
import tired9494.wotw_creatures.client.models.MartianSapiensModel;
import tired9494.wotw_creatures.entities.MartianSapiens;

public class MartianSapiensRenderer extends MobRenderer<MartianSapiens, MartianSapiensModel<MartianSapiens>> {
    private static final ResourceLocation TEXTURE = WotwCreatures.ID("textures/entity/martian_sapiens.png");
    public MartianSapiensRenderer(EntityRendererProvider.Context renderContext) {
        super(renderContext, new MartianSapiensModel<>(renderContext.bakeLayer(MartianSapiensModel.LAYER_LOCATION)), 0.5f); //shadow size
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull MartianSapiens entity) {
        return TEXTURE;
    }
}
