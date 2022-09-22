package de.keksuccino.biomesinjars.events;

import de.keksuccino.konkrete.events.EventBase;
import net.minecraft.server.level.ServerPlayer;

public class PlayerLoginEvent extends EventBase {

    public ServerPlayer player;

    public PlayerLoginEvent(ServerPlayer player) {
        this.player = player;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

}
