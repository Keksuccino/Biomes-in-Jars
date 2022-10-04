package de.keksuccino.biomesinjars;

import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandler {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e) {

        if (e.getPlayer() instanceof ServerPlayerEntity) {
            ResourceLocation recipe = new ResourceLocation("biomesinjars", "empty_biome_jar");
            if (!((ServerPlayerEntity)e.getPlayer()).getRecipeBook().contains(recipe)) {
                e.getPlayer().awardRecipesByKey(new ResourceLocation[]{recipe});
            }
        }

    }

}
