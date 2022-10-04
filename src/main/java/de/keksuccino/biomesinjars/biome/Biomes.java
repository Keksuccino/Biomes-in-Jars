package de.keksuccino.biomesinjars.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Biomes {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, "biomesinjars");

    public static final RegistryObject<Biome> DEAD_LAND = BIOMES.register("dead_land", () -> createDeadLand());

    public static void registerAll() {

        BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

    private static Biome createDeadLand() {
        return new Biome.Builder()
                .precipitation(Biome.RainType.RAIN)
                .specialEffects(new BiomeAmbience.Builder()
                        .foliageColorOverride(12765631)
                        .grassColorOverride(12765631)
                        .waterColor(10391163)
                        .fogColor(10391163)
                        .waterFogColor(10391163)
                        .skyColor(6853283)
                        .build())
                .temperature(0.8F)
                .downfall(0.4F)
                .mobSpawnSettings(MobSpawnInfo.EMPTY)
                .generationSettings(BiomeGenerationSettings.EMPTY)
                .biomeCategory(Biome.Category.NONE)
                .depth(1.0F)
                .scale(1.0F)
                .temperatureAdjustment(Biome.TemperatureModifier.NONE)
                .build();
    }

}
