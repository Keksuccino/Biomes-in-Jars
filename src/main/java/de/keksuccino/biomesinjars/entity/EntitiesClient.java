package de.keksuccino.biomesinjars.entity;

import de.keksuccino.biomesinjars.entity.entities.biomejar.empty.EmptyBiomeJarEntityModel;
import de.keksuccino.biomesinjars.entity.entities.biomejar.empty.EmptyBiomeJarEntityRenderer;
import de.keksuccino.biomesinjars.entity.entities.biomejar.filled.FilledBiomeJarEntityModel;
import de.keksuccino.biomesinjars.entity.entities.biomejar.filled.FilledBiomeJarEntityRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class EntitiesClient {

    public static void registerAll() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(EntitiesClient::onRegisterRenderers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(EntitiesClient::onRegisterLayerDefinitions);

    }

    private static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers e) {

        e.registerEntityRenderer(Entities.EMPTY_BIOME_JAR_ENTITY.get(), EmptyBiomeJarEntityRenderer::new);
        e.registerEntityRenderer(Entities.FILLED_BIOME_JAR_ENTITY.get(), FilledBiomeJarEntityRenderer::new);

    }

    private static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions e) {

        e.registerLayerDefinition(EmptyBiomeJarEntityRenderer.LAYER_LOCATION, () -> EmptyBiomeJarEntityModel.createBodyLayer());
        e.registerLayerDefinition(FilledBiomeJarEntityRenderer.LAYER_LOCATION, () -> FilledBiomeJarEntityModel.createBodyLayer());

    }

}
