package de.keksuccino.biomesinjars.entity.entities.biomejar.empty;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EmptyBiomeJarEntityRenderer extends GeoEntityRenderer<EmptyBiomeJarEntity> {

    public EmptyBiomeJarEntityRenderer(EntityRendererManager ctx) {
        super(ctx, new EmptyBiomeJarEntityModel());
        this.shadowRadius = 0.3F;
    }

    @Override
    public ResourceLocation getTextureLocation(EmptyBiomeJarEntity entity) {
        return new ResourceLocation("biomesinjars", "textures/entity/biome_jar/empty_biome_jar.png");
    }

    @Override
    public RenderType getRenderType(EmptyBiomeJarEntity animatable, float partialTicks, MatrixStack stack, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        //It's possible to scale the model here (could be useful in the future)
        //stack.scale(0, 0, 0);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }

}
