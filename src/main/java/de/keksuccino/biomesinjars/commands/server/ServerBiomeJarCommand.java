package de.keksuccino.biomesinjars.commands.server;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import de.keksuccino.biomesinjars.item.items.FilledBiomeJarItem;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ServerBiomeJarCommand {

    public static void register(CommandDispatcher<CommandSource> d) {

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

    private static RegistryKey<Biome> getBiomeArgument(CommandContext<CommandSource> context, String argument) {
        ResourceLocation resourceLoc = ResourceLocationArgument.getId(context, argument);
        return RegistryKey.create(Registry.BIOME_REGISTRY, resourceLoc);
    }

    private static int giveBiomeJar(CommandSource stack, RegistryKey<Biome> biome) {
        stack.getServer().execute(() -> {
            try {

                int slot = stack.getPlayerOrException().inventory.getFreeSlot();
                ItemStack filledJarStack = FilledBiomeJarItem.createStack(biome);
                if (slot != -1) {
                    stack.getPlayerOrException().inventory.setItem(slot, filledJarStack);
                } else {
                    stack.sendFailure(new TranslationTextComponent("biomesinjars.command.biomejar.unable_to_give"));
                }

            } catch (Exception e) {
                stack.sendFailure(new StringTextComponent("§cError while executing command!"));
                e.printStackTrace();
            }
        });
        return 1;
    }

}