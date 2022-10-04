package de.keksuccino.biomesinjars.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ItemUtils {

    @Nullable
    public static Item getItemByKey(String key) {
        try {
            if (key.contains(":")) {
                String domain = key.split("[:]", 2)[0];
                String nameItem = key.split("[:]", 2)[1];
                return Registry.ITEM.get(new ResourceLocation(domain, nameItem));
            }
            return Registry.ITEM.get(new ResourceLocation(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static ResourceLocation getKeyForItem(Item item) {
        try {
            return Registry.ITEM.getKey(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String getStringKeyForItem(Item item) {
        ResourceLocation r = getKeyForItem(item);
        if (r != null) {
            return r.toString();
        }
        return null;
    }

    public static void dropItemStack(World level, ItemStack itemStack, BlockPos blockPos) {
        popResource(level, () -> new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack), itemStack);
    }

    private static void popResource(World level, Supplier<ItemEntity> supplier, ItemStack itemStack) {
        if (level.isClientSide || itemStack.isEmpty() || !level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            return;
        }
        ItemEntity itemEntity = supplier.get();
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
    }

}
