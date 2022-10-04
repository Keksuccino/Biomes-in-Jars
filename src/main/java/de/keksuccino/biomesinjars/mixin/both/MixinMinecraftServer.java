package de.keksuccino.biomesinjars.mixin.both;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import de.keksuccino.biomesinjars.server.ServerAccess;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.chunk.listener.IChunkStatusListenerFactory;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.world.storage.SaveFormat;
import net.minecraft.world.storage.IServerConfiguration;
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
    private void onConstruct(Thread p_129769_, DynamicRegistries.Impl p_129770_, SaveFormat.LevelSave p_129771_, IServerConfiguration p_129772_, ResourcePackList p_129773_, Proxy p_129774_, DataFixer p_129775_, DataPackRegistries p_129776_, MinecraftSessionService p_129777_, GameProfileRepository p_129778_, PlayerProfileCache p_129779_, IChunkStatusListenerFactory p_129780_, CallbackInfo info) {
        MinecraftServer s = ((MinecraftServer)((Object)this));
        if ((s instanceof DedicatedServer) || (s instanceof IntegratedServer)) {
            ServerAccess.instance = s;
        }
    }

}
