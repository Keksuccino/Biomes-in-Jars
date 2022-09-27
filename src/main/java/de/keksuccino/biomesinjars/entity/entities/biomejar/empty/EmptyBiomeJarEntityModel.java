package de.keksuccino.biomesinjars.entity.entities.biomejar.empty;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EmptyBiomeJarEntityModel extends AnimatedGeoModel<EmptyBiomeJarEntity> {

    @Override
    public ResourceLocation getModelLocation(EmptyBiomeJarEntity object) {
        return new ResourceLocation("biomesinjars", "geo/empty_biome_jar.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EmptyBiomeJarEntity object) {
        return new ResourceLocation("biomesinjars", "textures/entity/biome_jar/empty_biome_jar.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EmptyBiomeJarEntity animatable) {
        return new ResourceLocation("biomesinjars", "animations/biome_jar.animation.json");
    }

}
