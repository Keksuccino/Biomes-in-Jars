package de.keksuccino.biomesinjars.entity.entities.biomejar.empty;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EmptyBiomeJarEntityRenderer extends MobRenderer<EmptyBiomeJarEntity, EmptyBiomeJarEntityModel> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("biomesinjars", "empty_biome_jar"), "main");

    public EmptyBiomeJarEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new EmptyBiomeJarEntityModel(context.bakeLayer(LAYER_LOCATION)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(EmptyBiomeJarEntity entity) {
        return new ResourceLocation("biomesinjars", "textures/entity/biome_jar/empty_biome_jar.png");
    }

}
