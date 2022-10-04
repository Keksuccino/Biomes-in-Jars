package de.keksuccino.biomesinjars.entity;

import de.keksuccino.biomesinjars.entity.entities.biomejar.empty.EmptyBiomeJarEntityRenderer;
import de.keksuccino.biomesinjars.entity.entities.biomejar.filled.FilledBiomeJarEntityRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class EntitiesClient {

    public static void registerAll() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(EntitiesClient::onRegisterRenderers);

    }

    private static void onRegisterRenderers(FMLClientSetupEvent e) {

        RenderingRegistry.registerEntityRenderingHandler(Entities.EMPTY_BIOME_JAR_ENTITY.get(), EmptyBiomeJarEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Entities.FILLED_BIOME_JAR_ENTITY.get(), FilledBiomeJarEntityRenderer::new);

    }

}
