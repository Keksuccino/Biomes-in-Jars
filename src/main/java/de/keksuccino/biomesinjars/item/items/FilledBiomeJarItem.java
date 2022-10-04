package de.keksuccino.biomesinjars.item.items;

import de.keksuccino.biomesinjars.BiomesInJars;
import de.keksuccino.biomesinjars.biome.Biomes;
import de.keksuccino.biomesinjars.item.Items;
import de.keksuccino.biomesinjars.mixin.both.IMixinChunkMap;
import de.keksuccino.biomesinjars.util.WorldUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.network.IPacket;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ActionResult;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemUseContext;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.block.BlockState;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.Item.Properties;

public class FilledBiomeJarItem extends Item {

    public FilledBiomeJarItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext useOnContext) {
        World level = useOnContext.getLevel();
        if (!(level instanceof ServerWorld)) {
            return ActionResultType.SUCCESS;
        }
        ItemStack itemStack = useOnContext.getItemInHand();
        BlockPos blockPos = useOnContext.getClickedPos();
        Direction direction = useOnContext.getClickedFace();
        BlockState blockState = level.getBlockState(blockPos);
        BlockPos blockPos2 = blockState.getCollisionShape(level, blockPos).isEmpty() ? blockPos : blockPos.relative(direction);
        if (onUse((ServerWorld)level, useOnContext.getPlayer(), itemStack, blockPos2)) {
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!(level instanceof ServerWorld)) {
            return ActionResult.success(itemStack);
        }
        if (onUse((ServerWorld)level, player, itemStack, new BlockPos(player.position().x, player.position().y, player.position().z))) {
            return ActionResult.success(itemStack);
        }
        return ActionResult.fail(itemStack);
    }

    private boolean onUse(ServerWorld level, PlayerEntity player, ItemStack itemStack, BlockPos blockPos) {
        RegistryKey<Biome> b = RegistryKey.create(Registry.BIOME_REGISTRY, Biomes.DEAD_LAND.getId());
        if (!WorldUtils.chunkContainsBiome(level.registryAccess(), level.getChunkAt(blockPos), b) || BiomesInJars.config.getOrDefault("allow_overriding_dead_land", false)) {
            RegistryKey<Biome> biome = getBiomeOfStack(itemStack);
            if (biome == null) {
                biome = RegistryKey.create(Registry.BIOME_REGISTRY, net.minecraft.world.biome.Biomes.PLAINS.location());
            }
            if (setChunkBiome(level, blockPos, biome)) {
                itemStack.hurtAndBreak(1, player, livingEntity -> livingEntity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
                level.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS, 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }

    private static boolean setChunkBiome(ServerWorld level, BlockPos pos, RegistryKey<Biome> biome) {
        try {
            if (WorldUtils.setChunkBiomeAtBlockPos(level, pos, biome)) {
                level.getServer().getPlayerList().getPlayers().forEach((player) -> {
                    ((IMixinChunkMap)level.getChunkSource().chunkMap).playerLoadedChunkBiomesInJars(player, new IPacket[2], level.getChunkAt(pos));
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

    private static RegistryKey<Biome> getBiomeOfStack(ItemStack stack) {
        try {
            if (stack.getOrCreateTag().contains("biome")) {
                String bs = stack.getOrCreateTag().getString("biome");
                return RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(bs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ITextComponent getName(ItemStack itemStack) {
        RegistryKey<Biome> biome = getBiomeOfStack(itemStack);
        if (biome != null) {
            String biomeNamespace = biome.location().getNamespace();
            String biomePath = biome.location().getPath();
            return new TranslationTextComponent("item.biomesinjars.filled_biome_jar", new TranslationTextComponent("biome." + biomeNamespace + "." + biomePath));
        }
        return new TranslationTextComponent("item.biomesinjars.filled_biome_jar.generic");
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World level, List<ITextComponent> list, ITooltipFlag tooltipFlag) {
        list.add(new StringTextComponent(""));
        list.add(new TranslationTextComponent("item.biomesinjars.filled_biome_jar.tooltip.line_1"));
        list.add(new TranslationTextComponent("item.biomesinjars.filled_biome_jar.tooltip.line_2"));
        list.add(new TranslationTextComponent("item.biomesinjars.filled_biome_jar.tooltip.line_3"));
        list.add(new TranslationTextComponent("item.biomesinjars.filled_biome_jar.tooltip.line_4"));
        list.add(new TranslationTextComponent("item.biomesinjars.filled_biome_jar.tooltip.line_5"));
    }

    public static ItemStack createStack(RegistryKey<Biome> biome) {
        ItemStack s = new ItemStack(Items.FILLED_BIOME_JAR_ITEM.get());
        s.addTagElement("biome", StringNBT.valueOf(biome.location().toString()));
        return s;
    }

}
