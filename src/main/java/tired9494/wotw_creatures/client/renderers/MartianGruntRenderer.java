package tired9494.wotw_creatures.client.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import tired9494.wotw_creatures.WotwCreatures;
import tired9494.wotw_creatures.client.models.MartianGruntModel;
import tired9494.wotw_creatures.entities.MartianGrunt;

public class MartianGruntRenderer extends MobRenderer<MartianGrunt, MartianGruntModel<MartianGrunt>> {
    private static final ResourceLocation TEXTURE = WotwCreatures.ID("textures/entity/martian_grunt.png");
    public MartianGruntRenderer(EntityRendererProvider.Context renderContext) {
        super(renderContext, new MartianGruntModel<>(renderContext.bakeLayer(MartianGruntModel.LAYER_LOCATION)), 0.5f); //shadow size
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull MartianGrunt entity) {
        return TEXTURE;
    }
}
