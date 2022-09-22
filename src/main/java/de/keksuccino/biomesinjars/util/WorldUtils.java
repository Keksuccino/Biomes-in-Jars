package de.keksuccino.biomesinjars.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;

public class WorldUtils {

    /**
     * Set full chunk to specific biome
     */
    public static boolean setChunkBiomeAtBlockPos(Level level, BlockPos pos, ResourceKey<Biome> biome) {
        try {
            Holder<Biome> biomeHolder = level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getHolderOrThrow(biome);
            return setChunkBiomeAtBlockPos(level, pos, biomeHolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Set full chunk to specific biome
     */
    public static boolean setChunkBiomeAtBlockPos(Level level, BlockPos pos, Holder<Biome> biome) {
        try {
            ChunkAccess access = level.getChunk(pos);
            for (LevelChunkSection sec : access.getSections()) {
                if (sec.getBiomes() instanceof PalettedContainer<Holder<Biome>>) {
                    Holder<Biome> biomeHolder = biome;
                    int biomeId = ((PalettedContainer<Holder<Biome>>)sec.getBiomes()).data.palette().idFor(biomeHolder);
                    int size = ((PalettedContainer<Holder<Biome>>)sec.getBiomes()).data.storage().getSize();
                    int i = 0;
                    while (i <= (size-1)) {
                        ((PalettedContainer<Holder<Biome>>)sec.getBiomes()).data.storage().set(i, biomeId);
                        i++;
                    }
                }
            }
            access.setUnsaved(true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
