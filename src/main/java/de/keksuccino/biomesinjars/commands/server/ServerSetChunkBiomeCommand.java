package de.keksuccino.biomesinjars.commands.server;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.keksuccino.biomesinjars.mixin.both.IMixinChunkMap;
import de.keksuccino.biomesinjars.util.WorldUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ServerSetChunkBiomeCommand {

    public static void register(CommandDispatcher<CommandSourceStack> d) {

        d.register(Commands.literal("setchunkbiome")
                .requires((stack) -> {
                    return (stack.permissionLevel >= 2);
                })
                .then(Commands.argument("biome", ResourceLocationArgument.id())
                        .suggests(((context, builder) -> {
                            return buildBiomeSuggestions(context, builder);
                        }))
                        .executes((context -> {
                            return setChunkBiome(context.getSource(), getBiomeArgument(context, "biome"));
                        }))
                )
        );

    }

    private static CompletableFuture<Suggestions> buildBiomeSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        List<String> l = new ArrayList<>();
        try {
            for (ResourceLocation r : context.getSource().getServer().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).keySet()) {
                l.add(r.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (l.isEmpty()) {
            l.add("");
        }
        return getStringSuggestions(builder, l.toArray(new String[0]));
    }

    private static CompletableFuture<Suggestions> getStringSuggestions(SuggestionsBuilder suggestionsBuilder, String... suggestions) {
        return SharedSuggestionProvider.suggest(suggestions, suggestionsBuilder);
    }

    private static Biome getBiomeArgument(CommandContext<CommandSourceStack> context, String argument) throws CommandSyntaxException {
        ResourceLocation resourceLoc = ResourceLocationArgument.getId(context, argument);
        return context.getSource().getServer().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).get(resourceLoc);
    }

    private static int setChunkBiome(CommandSourceStack stack, Biome biome) {
        stack.getServer().execute(() -> {
            try {

                //Set all sections in chunk to new biome
                if (WorldUtils.setChunkBiomeAtBlockPos(stack.getLevel(), stack.getPlayerOrException().blockPosition(), stack.getLevel().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getResourceKey(biome).get())) {
                    stack.sendSuccess(Component.literal("§aChunk biome changed to: " + stack.getLevel().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getResourceKey(biome).get().location().toString()), false);
                } else {
                    stack.sendFailure(Component.literal("§cUnable to change biome!"));
                }

                //Update client chunk renderer for all players
                stack.getServer().getPlayerList().getPlayers().forEach((player) -> {
                    try {
                        ((IMixinChunkMap)stack.getLevel().getChunkSource().chunkMap).playerLoadedChunkBiomesInJars(player, new MutableObject<>(), stack.getLevel().getChunkAt(stack.getPlayerOrException().blockPosition()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (Exception e) {
                stack.sendFailure(Component.literal("§cError while executing command!"));
                e.printStackTrace();
            }
        });
        return 1;
    }

}