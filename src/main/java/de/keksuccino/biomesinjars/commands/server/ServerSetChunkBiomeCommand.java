package de.keksuccino.biomesinjars.commands.server;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import de.keksuccino.biomesinjars.mixin.both.IMixinChunkMap;
import de.keksuccino.biomesinjars.mixin.both.IMixinCommandSourceStack;
import de.keksuccino.biomesinjars.util.WorldUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.mutable.MutableObject;

public class ServerSetChunkBiomeCommand {

    private static final DynamicCommandExceptionType ERROR_UNKNOWN_BIOME = new DynamicCommandExceptionType(object -> Component.literal("Unknown biome: " + object));

    public static void register(CommandDispatcher<CommandSourceStack> d) {

        d.register(Commands.literal("setchunkbiome")
                        .requires((stack) -> {
                            return (((IMixinCommandSourceStack)stack).getPermissionLevelBiomesInJars() >= 2);
                        })
                .then(Commands.argument("biome", ResourceKeyArgument.key(Registry.BIOME_REGISTRY))
                                .executes((context -> {
                                    return setChunkBiome(context.getSource(), getBiomeArgument(context, "biome"));
                                }))
                )
        );

    }

    private static Holder<Biome> getBiomeArgument(CommandContext<CommandSourceStack> context, String argument) throws CommandSyntaxException {
        return ResourceKeyArgument.getRegistryKeyType(context, argument, Registry.BIOME_REGISTRY, ERROR_UNKNOWN_BIOME);
    }

    private static int setChunkBiome(CommandSourceStack stack, Holder<Biome> biome) {
        stack.getServer().execute(() -> {
            try {

                //Set all sections in chunk to new biome
                if (WorldUtils.setChunkBiomeAtBlockPos(stack.getLevel(), stack.getPlayerOrException().blockPosition(), biome)) {
                    stack.sendSuccess(Component.literal("§aChunk biome changed to: " + biome.unwrapKey().get().location().toString()), false);
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