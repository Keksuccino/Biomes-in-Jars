package de.keksuccino.biomesinjars.entity;

import de.keksuccino.biomesinjars.entity.entities.biomejar.empty.EmptyBiomeJarEntity;
import de.keksuccino.biomesinjars.entity.entities.biomejar.filled.FilledBiomeJarEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class Entities {

    public static final EntityType<EmptyBiomeJarEntity> EMPTY_BIOME_JAR_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new ResourceLocation("biomesinjars", "empty_biome_jar"),
            FabricEntityTypeBuilder.create(MobCategory.MISC, EmptyBiomeJarEntity::new).spawnGroup(MobCategory.CREATURE).dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build()
    );
    public static final EntityType<FilledBiomeJarEntity> FILLED_BIOME_JAR_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new ResourceLocation("biomesinjars", "filled_biome_jar"),
            FabricEntityTypeBuilder.create(MobCategory.MISC, FilledBiomeJarEntity::new).spawnGroup(MobCategory.CREATURE).dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build()
    );

    public static void registerAll() {

        FabricDefaultAttributeRegistry.register(EMPTY_BIOME_JAR_ENTITY, EmptyBiomeJarEntity.createEmptyBiomeJarEntityAttributes());
        FabricDefaultAttributeRegistry.register(FILLED_BIOME_JAR_ENTITY, FilledBiomeJarEntity.createFilledBiomeJarEntityAttributes());

    }

}
