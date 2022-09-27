package de.keksuccino.biomesinjars.entity.entities.biomejar.filled;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FilledBiomeJarEntityModel extends AnimatedGeoModel<FilledBiomeJarEntity> {

    @Override
    public ResourceLocation getModelLocation(FilledBiomeJarEntity object) {
        return new ResourceLocation("biomesinjars", "geo/filled_biome_jar.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FilledBiomeJarEntity object) {
        return new ResourceLocation("biomesinjars", "textures/entity/biome_jar/filled_biome_jar.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FilledBiomeJarEntity animatable) {
        return new ResourceLocation("biomesinjars", "animations/biome_jar.animation.json");
    }

}
