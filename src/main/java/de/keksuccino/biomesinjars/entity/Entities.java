package de.keksuccino.biomesinjars.entity;

import de.keksuccino.biomesinjars.entity.entities.biomejar.empty.EmptyBiomeJarEntity;
import de.keksuccino.biomesinjars.entity.entities.biomejar.filled.FilledBiomeJarEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Entities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, "biomesinjars");

    public static final RegistryObject<EntityType<EmptyBiomeJarEntity>> EMPTY_BIOME_JAR_ENTITY = ENTITY_TYPES.register(
            "empty_biome_jar",
            () -> EntityType.Builder.of(EmptyBiomeJarEntity::new, MobCategory.MISC)
                    .sized(0.4f, 0.4f)
                    .build(new ResourceLocation("biomesinjars", "empty_biome_jar").toString()));

    public static final RegistryObject<EntityType<FilledBiomeJarEntity>> FILLED_BIOME_JAR_ENTITY = ENTITY_TYPES.register(
            "filled_biome_jar",
            () -> EntityType.Builder.of(FilledBiomeJarEntity::new, MobCategory.MISC)
                    .sized(0.4f, 0.4f)
                    .build(new ResourceLocation("biomesinjars", "filled_biome_jar").toString()));

    private static void onCreateAttributes(EntityAttributeCreationEvent e) {

        e.put(EMPTY_BIOME_JAR_ENTITY.get(), EmptyBiomeJarEntity.createEmptyBiomeJarEntityAttributes().build());
        e.put(FILLED_BIOME_JAR_ENTITY.get(), FilledBiomeJarEntity.createFilledBiomeJarEntityAttributes().build());

    }

    public static void registerAll() {

        ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(Entities::onCreateAttributes);

    }


}
