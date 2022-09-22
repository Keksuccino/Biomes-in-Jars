package de.keksuccino.biomesinjars.item.items;

import de.keksuccino.biomesinjars.entity.entities.biomejar.empty.EmptyBiomeJarEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmptyBiomeJarItem extends Item {

    public EmptyBiomeJarItem(Properties properties) {
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
        EmptyBiomeJarEntity.spawnAt((ServerLevel)level, blockPos2, 0.0F, true);
        itemStack.shrink(1);
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.literal(""));
        list.add(Component.translatable("item.biomesinjars.empty_biome_jar.tooltip.line_1"));
        list.add(Component.translatable("item.biomesinjars.empty_biome_jar.tooltip.line_2"));
        list.add(Component.translatable("item.biomesinjars.empty_biome_jar.tooltip.line_3"));
        list.add(Component.translatable("item.biomesinjars.empty_biome_jar.tooltip.line_4"));
        list.add(Component.translatable("item.biomesinjars.empty_biome_jar.tooltip.line_5"));
    }

}
