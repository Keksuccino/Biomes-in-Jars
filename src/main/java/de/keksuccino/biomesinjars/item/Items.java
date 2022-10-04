package de.keksuccino.biomesinjars.item;

import de.keksuccino.biomesinjars.item.items.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Items {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "biomesinjars");

    public static final RegistryObject<Item> EMPTY_BIOME_JAR_ITEM = ITEMS.register("empty_biome_jar", () -> new EmptyBiomeJarItem(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<Item> FILLED_BIOME_JAR_ITEM = ITEMS.register("filled_biome_jar", () -> new FilledBiomeJarItem(new Item.Properties().tab(ItemGroup.TAB_MISC).stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant()));

    public static void registerAll() {

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

}
