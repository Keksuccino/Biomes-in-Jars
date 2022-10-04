package de.keksuccino.biomesinjars.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import net.minecraft.world.chunk.Chunk;

public class WorldUtils {

    /**
     * Set full chunk to specific biome
     */
    public static boolean setChunkBiomeAtBlockPos(World level, BlockPos pos, RegistryKey<Biome> biome) {
        try {
            Biome biomeHolder = level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getOrThrow(biome);
            return setChunkBiomeAtBlockPos(level, pos, biomeHolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Set full chunk to specific biome
     */
    public static boolean setChunkBiomeAtBlockPos(World level, BlockPos pos, Biome biome) {
        try {

            Chunk chunk = level.getChunkAt(pos);
            int i = 0;
            int length = chunk.getBiomes().biomes.length;
            while (i < length) {
                chunk.getBiomes().biomes[i] = biome;
                i++;
            }
            level.getChunk(pos).setUnsaved(true);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean chunkContainsBiome(Chunk levelChunk, Biome biome) {
        try {
            for (Biome b : levelChunk.getBiomes().biomes) {
                if (b == biome) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean chunkContainsBiome(Chunk levelChunk, RegistryKey<Biome> biome) {
        try {
            DynamicRegistries.Impl writable = DynamicRegistries.builtin();
            return chunkContainsBiome(writable, levelChunk, biome);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean chunkContainsBiome(DynamicRegistries registryAccess, Chunk levelChunk, RegistryKey<Biome> biome) {
        try {
            Biome biomeHolder = registryAccess.registryOrThrow(Registry.BIOME_REGISTRY).getOrThrow(biome);
            return chunkContainsBiome(levelChunk, biomeHolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
