package de.keksuccino.biomesinjars.biome;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class Biomes {

    public static final ResourceKey<Biome> DEAD_LAND = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation("biomesinjars", "dead_land"));

    public static void registerAll() {

        BuiltinRegistries.register(BuiltinRegistries.BIOME, DEAD_LAND, createDeadLand());

    }

    private static Biome createDeadLand() {
        return new Biome.BiomeBuilder()
                .precipitation(Biome.Precipitation.RAIN)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .foliageColorOverride(12765631)
                        .grassColorOverride(12765631)
                        .waterColor(10391163)
                        .fogColor(10391163)
                        .waterFogColor(10391163)
                        .skyColor(6853283)
                        .build())
                .temperature(0.8F)
                .downfall(0.4F)
                .biomeCategory(Biome.BiomeCategory.NONE)
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(BiomeGenerationSettings.EMPTY)
                .build();
    }

}
