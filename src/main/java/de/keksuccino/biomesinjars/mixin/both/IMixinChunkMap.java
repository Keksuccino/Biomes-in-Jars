package de.keksuccino.biomesinjars.mixin.both;

import net.minecraft.network.IPacket;
import net.minecraft.world.server.ChunkManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ChunkManager.class)
public interface IMixinChunkMap {

    @Invoker("playerLoadedChunk") void playerLoadedChunkBiomesInJars(ServerPlayerEntity p_140196_, IPacket<?>[] p_140197_, Chunk p_140198_);

}
