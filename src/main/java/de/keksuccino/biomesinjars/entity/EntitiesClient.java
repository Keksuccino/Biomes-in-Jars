package de.keksuccino.biomesinjars.entity;

import de.keksuccino.biomesinjars.entity.entities.biomejar.empty.EmptyBiomeJarEntityModel;
import de.keksuccino.biomesinjars.entity.entities.biomejar.empty.EmptyBiomeJarEntityRenderer;
import de.keksuccino.biomesinjars.entity.entities.biomejar.filled.FilledBiomeJarEntityModel;
import de.keksuccino.biomesinjars.entity.entities.biomejar.filled.FilledBiomeJarEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class EntitiesClient {

    public static void registerAll() {

        EntityRendererRegistry.register(Entities.EMPTY_BIOME_JAR_ENTITY, (context -> {
            return new EmptyBiomeJarEntityRenderer(context);
        }));
        EntityModelLayerRegistry.registerModelLayer(EmptyBiomeJarEntityRenderer.LAYER_LOCATION, EmptyBiomeJarEntityModel::createBodyLayer);

        EntityRendererRegistry.register(Entities.FILLED_BIOME_JAR_ENTITY, (context -> {
            return new FilledBiomeJarEntityRenderer(context);
        }));
        EntityModelLayerRegistry.registerModelLayer(FilledBiomeJarEntityRenderer.LAYER_LOCATION, FilledBiomeJarEntityModel::createBodyLayer);

    }

}
