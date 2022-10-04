package de.keksuccino.biomesinjars;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e) {

        if (e.getEntity() instanceof ServerPlayer) {
            ResourceLocation recipe = new ResourceLocation("biomesinjars", "empty_biome_jar");
            if (!((ServerPlayer)e.getEntity()).getRecipeBook().contains(recipe)) {
                e.getEntity().awardRecipesByKey(new ResourceLocation[]{recipe});
            }
        }

    }

}
