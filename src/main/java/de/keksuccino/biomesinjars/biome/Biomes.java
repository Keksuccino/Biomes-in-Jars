package de.keksuccino.biomesinjars.biome;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Biomes {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, "biomesinjars");

//    public static final ResourceKey<Biome> DEAD_LAND = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation("biomesinjars", "dead_land"));

    public static final RegistryObject<Biome> DEAD_LAND = BIOMES.register("dead_land", () -> createDeadLand());

    public static void registerAll() {

        BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());

//        BuiltinRegistries.register(BuiltinRegistries.BIOME, DEAD_LAND, createDeadLand());

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
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .generationSettings(BiomeGenerationSettings.EMPTY)
                .build();
    }

}
