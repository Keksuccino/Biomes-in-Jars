package de.keksuccino.biomesinjars.commands.server;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.keksuccino.biomesinjars.item.items.FilledBiomeJarItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ServerBiomeJarCommand {

    public static void register(CommandDispatcher<CommandSourceStack> d) {

        d.register(Commands.literal("biomejar")
                        .requires((stack) -> {
                            return (stack.permissionLevel >= 2);
                        })
                .then(Commands.argument("biome", ResourceLocationArgument.id())
                        .suggests(((context, builder) -> {
                            return buildBiomeSuggestions(context, builder);
                        }))
                                .executes((context -> {
                                    return giveBiomeJar(context.getSource(), getBiomeArgument(context, "biome"));
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

    private static ResourceKey<Biome> getBiomeArgument(CommandContext<CommandSourceStack> context, String argument) {
        ResourceLocation resourceLoc = ResourceLocationArgument.getId(context, argument);
        return ResourceKey.create(Registry.BIOME_REGISTRY, resourceLoc);
    }

    private static int giveBiomeJar(CommandSourceStack stack, ResourceKey<Biome> biome) {
        stack.getServer().execute(() -> {
            try {

                int slot = stack.getPlayerOrException().getInventory().getFreeSlot();
                ItemStack filledJarStack = FilledBiomeJarItem.createStack(biome);
                if (slot != -1) {
                    stack.getPlayerOrException().getInventory().setItem(slot, filledJarStack);
                } else {
                    stack.sendFailure(new TranslatableComponent("biomesinjars.command.biomejar.unable_to_give"));
                }

            } catch (Exception e) {
                stack.sendFailure(new TextComponent("§cError while executing command!"));
                e.printStackTrace();
            }
        });
        return 1;
    }

}