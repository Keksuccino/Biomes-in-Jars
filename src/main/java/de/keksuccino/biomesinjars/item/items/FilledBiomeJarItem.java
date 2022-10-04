package de.keksuccino.biomesinjars.item.items;

import de.keksuccino.biomesinjars.BiomesInJars;
import de.keksuccino.biomesinjars.biome.Biomes;
import de.keksuccino.biomesinjars.item.Items;
import de.keksuccino.biomesinjars.mixin.both.IMixinChunkMap;
import de.keksuccino.biomesinjars.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FilledBiomeJarItem extends Item {

    public FilledBiomeJarItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }
        ItemStack itemStack = useOnContext.getItemInHand();
        BlockPos blockPos = useOnContext.getClickedPos();
        Direction direction = useOnContext.getClickedFace();
        BlockState blockState = level.getBlockState(blockPos);
        BlockPos blockPos2 = blockState.getCollisionShape(level, blockPos).isEmpty() ? blockPos : blockPos.relative(direction);
        if (onUse((ServerLevel)level, useOnContext.getPlayer(), itemStack, blockPos2)) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!(level instanceof ServerLevel)) {
            return InteractionResultHolder.success(itemStack);
        }
        if (onUse((ServerLevel)level, player, itemStack, new BlockPos(player.position().x, player.position().y, player.position().z))) {
            return InteractionResultHolder.success(itemStack);
        }
        return InteractionResultHolder.fail(itemStack);
    }

    private boolean onUse(ServerLevel level, Player player, ItemStack itemStack, BlockPos blockPos) {
        ResourceKey<Biome> b = ResourceKey.create(Registry.BIOME_REGISTRY, Biomes.DEAD_LAND.getId());
        if (!WorldUtils.chunkContainsBiome(level.registryAccess(), level.getChunk(blockPos), b) || BiomesInJars.config.getOrDefault("allow_overriding_dead_land", false)) {
            ResourceKey<Biome> biome = getBiomeOfStack(itemStack);
            if (biome == null) {
                biome = ResourceKey.create(Registry.BIOME_REGISTRY, net.minecraft.world.level.biome.Biomes.PLAINS.location());
            }
            if (setChunkBiome(level, blockPos, biome)) {
                itemStack.hurtAndBreak(1, player, livingEntity -> livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }

    private static boolean setChunkBiome(ServerLevel level, BlockPos pos, ResourceKey<Biome> biome) {
        try {
            if (WorldUtils.setChunkBiomeAtBlockPos(level, pos, biome)) {
                level.getServer().getPlayerList().getPlayers().forEach((player) -> {
                    ((IMixinChunkMap)level.getChunkSource().chunkMap).playerLoadedChunkBiomesInJars(player, new MutableObject<>(), level.getChunkAt(pos));
                });
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }

    private static ResourceKey<Biome> getBiomeOfStack(ItemStack stack) {
        try {
            if (stack.getOrCreateTag().contains("biome")) {
                String bs = stack.getOrCreateTag().getString("biome");
                return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(bs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Component getName(ItemStack itemStack) {
        ResourceKey<Biome> biome = getBiomeOfStack(itemStack);
        if (biome != null) {
            String biomeNamespace = biome.location().getNamespace();
            String biomePath = biome.location().getPath();
            return new TranslatableComponent("item.biomesinjars.filled_biome_jar", new TranslatableComponent("biome." + biomeNamespace + "." + biomePath));
        }
        return new TranslatableComponent("item.biomesinjars.filled_biome_jar.generic");
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(new TextComponent(""));
        list.add(new TranslatableComponent("item.biomesinjars.filled_biome_jar.tooltip.line_1"));
        list.add(new TranslatableComponent("item.biomesinjars.filled_biome_jar.tooltip.line_2"));
        list.add(new TranslatableComponent("item.biomesinjars.filled_biome_jar.tooltip.line_3"));
        list.add(new TranslatableComponent("item.biomesinjars.filled_biome_jar.tooltip.line_4"));
        list.add(new TranslatableComponent("item.biomesinjars.filled_biome_jar.tooltip.line_5"));
    }

    public static ItemStack createStack(ResourceKey<Biome> biome) {
        ItemStack s = new ItemStack(Items.FILLED_BIOME_JAR_ITEM.get());
        s.addTagElement("biome", StringTag.valueOf(biome.location().toString()));
        return s;
    }

}
