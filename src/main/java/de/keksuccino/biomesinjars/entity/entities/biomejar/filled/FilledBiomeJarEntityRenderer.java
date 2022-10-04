package de.keksuccino.biomesinjars.entity.entities.biomejar.filled;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FilledBiomeJarEntityRenderer extends MobRenderer<FilledBiomeJarEntity, FilledBiomeJarEntityModel> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("biomesinjars", "filled_biome_jar"), "main");

    public FilledBiomeJarEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new FilledBiomeJarEntityModel(context.bakeLayer(LAYER_LOCATION)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(FilledBiomeJarEntity entity) {
        return new ResourceLocation("biomesinjars", "textures/entity/biome_jar/filled_biome_jar.png");
    }

}
