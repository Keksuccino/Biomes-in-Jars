package de.keksuccino.biomesinjars.item.items;

import de.keksuccino.biomesinjars.entity.entities.biomejar.empty.EmptyBiomeJarEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemUseContext;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class EmptyBiomeJarItem extends Item {

    public EmptyBiomeJarItem(Properties properties) {
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
        EmptyBiomeJarEntity.spawnAt((ServerWorld)level, blockPos2, 0.0F, true);
        itemStack.shrink(1);
        return ActionResultType.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        return ActionResult.pass(itemStack);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World level, List<ITextComponent> list, ITooltipFlag tooltipFlag) {
        list.add(new StringTextComponent(""));
        list.add(new TranslationTextComponent("item.biomesinjars.empty_biome_jar.tooltip.line_1"));
        list.add(new TranslationTextComponent("item.biomesinjars.empty_biome_jar.tooltip.line_2"));
        list.add(new TranslationTextComponent("item.biomesinjars.empty_biome_jar.tooltip.line_3"));
        list.add(new TranslationTextComponent("item.biomesinjars.empty_biome_jar.tooltip.line_4"));
        list.add(new TranslationTextComponent("item.biomesinjars.empty_biome_jar.tooltip.line_5"));
    }

}
