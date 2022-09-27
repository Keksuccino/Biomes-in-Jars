package de.keksuccino.biomesinjars.entity.entities.biomejar.filled;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FilledBiomeJarEntityRenderer extends GeoEntityRenderer<FilledBiomeJarEntity> {

    public FilledBiomeJarEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new FilledBiomeJarEntityModel());
        this.shadowRadius = 0.3F;
    }

    @Override
    public ResourceLocation _getTextureLocation(FilledBiomeJarEntity entity) {
        return new ResourceLocation("biomesinjars", "textures/entity/biome_jar/filled_biome_jar.png");
    }

    @Override
    public RenderType getRenderType(FilledBiomeJarEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        //It's possible to scale the model here (could be useful in the future)
        //stack.scale(0, 0, 0);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }

}
