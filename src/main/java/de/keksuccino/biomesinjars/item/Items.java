package de.keksuccino.biomesinjars.item;

import de.keksuccino.biomesinjars.item.items.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Items {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "biomesinjars");

    public static final RegistryObject<Item> EMPTY_BIOME_JAR_ITEM = ITEMS.register("empty_biome_jar", () -> new EmptyBiomeJarItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant()));
    public static final RegistryObject<Item> FILLED_BIOME_JAR_ITEM = ITEMS.register("filled_biome_jar", () -> new FilledBiomeJarItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant()));

//    public static final Item EMPTY_BIOME_JAR_ITEM = new EmptyBiomeJarItem(new FabricItemSettings().group(CreativeModeTab.TAB_MISC).maxCount(1).rarity(Rarity.UNCOMMON).fireproof());
//    public static final Item FILLED_BIOME_JAR_ITEM = new FilledBiomeJarItem(new FabricItemSettings().group(CreativeModeTab.TAB_MISC).maxCount(1).rarity(Rarity.UNCOMMON).fireproof());

    public static void registerAll() {

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

//        Registry.register(Registry.ITEM, new ResourceLocation("biomesinjars", "empty_biome_jar"), EMPTY_BIOME_JAR_ITEM);
//        Registry.register(Registry.ITEM, new ResourceLocation("biomesinjars", "filled_biome_jar"), FILLED_BIOME_JAR_ITEM);

    }

}
