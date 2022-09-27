package de.keksuccino.biomesinjars.mixin.both;

import de.keksuccino.biomesinjars.events.PlayerLoginEvent;
import de.keksuccino.biomesinjars.events.PlayerLogoutEvent;
import de.keksuccino.konkrete.Konkrete;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class MixinPlayerList {

    @Inject(at = @At("TAIL"), method = "placeNewPlayer")
    private void onPlaceNewPlayer(Connection connection, ServerPlayer serverPlayer, CallbackInfo info) {
        PlayerLoginEvent event = new PlayerLoginEvent(serverPlayer);
        Konkrete.getEventHandler().callEventsFor(event);
    }

    @Inject(at = @At("HEAD"), method = "remove")
    private void onRemove(ServerPlayer serverPlayer, CallbackInfo info) {
        PlayerLogoutEvent event = new PlayerLogoutEvent(serverPlayer);
        Konkrete.getEventHandler().callEventsFor(event);
    }

}
