package de.keksuccino.biomesinjars;

import de.keksuccino.biomesinjars.events.PlayerLoginEvent;
import de.keksuccino.konkrete.Konkrete;
import de.keksuccino.konkrete.events.SubscribeEvent;
import net.minecraft.resources.ResourceLocation;

public class EventHandler {

    public static void init() {
        Konkrete.getEventHandler().registerEventsFrom(new EventHandler());
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerLoginEvent e) {

        ResourceLocation recipe = new ResourceLocation("biomesinjars", "empty_biome_jar");
        if (!e.player.getRecipeBook().contains(recipe)) {
            e.player.awardRecipesByKey(new ResourceLocation[]{recipe});
        }

    }

}
