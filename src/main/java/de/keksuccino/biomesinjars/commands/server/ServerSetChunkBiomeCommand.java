package de.keksuccino.biomesinjars.commands.server;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.keksuccino.biomesinjars.mixin.both.IMixinChunkMap;
import de.keksuccino.biomesinjars.util.WorldUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.network.IPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ServerSetChunkBiomeCommand {

    public static void register(CommandDispatcher<CommandSource> d) {

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

    private static CompletableFuture<Suggestions> buildBiomeSuggestions(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
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
        return ISuggestionProvider.suggest(suggestions, suggestionsBuilder);
    }

    private static Biome getBiomeArgument(CommandContext<CommandSource> context, String argument) throws CommandSyntaxException {
        ResourceLocation resourceLoc = ResourceLocationArgument.getId(context, argument);
        return context.getSource().getServer().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).get(resourceLoc);
    }

    private static int setChunkBiome(CommandSource stack, Biome biome) {
        stack.getServer().execute(() -> {
            try {

                //Set all sections in chunk to new biome
                if (WorldUtils.setChunkBiomeAtBlockPos(stack.getLevel(), stack.getPlayerOrException().blockPosition(), stack.getLevel().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getResourceKey(biome).get())) {
                    stack.sendSuccess(new StringTextComponent("§aChunk biome changed to: " + stack.getLevel().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getResourceKey(biome).get().location().toString()), false);
                } else {
                    stack.sendFailure(new StringTextComponent("§cUnable to change biome!"));
                }

                //Update client chunk renderer for all players
                stack.getServer().getPlayerList().getPlayers().forEach((player) -> {
                    try {
                        ((IMixinChunkMap)stack.getLevel().getChunkSource().chunkMap).playerLoadedChunkBiomesInJars(player, new IPacket[2], stack.getLevel().getChunkAt(stack.getPlayerOrException().blockPosition()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (Exception e) {
                stack.sendFailure(new StringTextComponent("§cError while executing command!"));
                e.printStackTrace();
            }
        });
        return 1;
    }

}