package de.keksuccino.biomesinjars.mixin.both;

import com.mojang.datafixers.DataFixer;
import de.keksuccino.biomesinjars.server.ServerAccess;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Services;
import net.minecraft.server.WorldStem;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {

    private static Logger MIXIN_LOGGER = LogManager.getLogger();

    @Inject(at = @At("TAIL"), method = "<init>")
    private void onConstruct(Thread thread, LevelStorageSource.LevelStorageAccess levelStorageAccess, PackRepository packRepository, WorldStem worldStem, Proxy proxy, DataFixer dataFixer, Services services, ChunkProgressListenerFactory chunkProgressListenerFactory, CallbackInfo info) {
        MinecraftServer s = ((MinecraftServer)((Object)this));
        if ((s instanceof DedicatedServer) || (s instanceof IntegratedServer)) {
            ServerAccess.instance = s;
        }
    }

}
