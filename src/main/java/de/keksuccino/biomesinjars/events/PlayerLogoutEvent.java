package de.keksuccino.biomesinjars.events;

import de.keksuccino.konkrete.events.EventBase;
import net.minecraft.server.level.ServerPlayer;

public class PlayerLogoutEvent extends EventBase {

    public ServerPlayer player;

    public PlayerLogoutEvent(ServerPlayer player) {
        this.player = player;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

}
