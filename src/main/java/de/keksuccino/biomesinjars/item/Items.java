package de.keksuccino.biomesinjars.item;

import de.keksuccino.biomesinjars.BiomesInJars;
import de.keksuccino.biomesinjars.item.items.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class Items {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Map<ResourceKey<Biome>, Item> JAR_ITEMS = new HashMap<>();

    public static final Item EMPTY_BIOME_JAR_ITEM = new EmptyBiomeJarItem(new FabricItemSettings().group(CreativeModeTab.TAB_MISC).maxCount(1).rarity(Rarity.UNCOMMON).fireproof());

    public static void registerAll() {

        Registry.register(Registry.ITEM, new ResourceLocation("biomesinjars", "empty_biome_jar"), EMPTY_BIOME_JAR_ITEM);

        try {

            ResourceKey<Biome> b = Biomes.PLAINS;
            RegistryAccess.Writable writable = RegistryAccess.builtinCopy();
            RegistryAccess access = writable.freeze();

            if (access != null) {
                for (Map.Entry<ResourceKey<Biome>, Biome> e : access.registryOrThrow(Registry.BIOME_REGISTRY).entrySet()) {
                    String namespace = e.getKey().location().getNamespace();
                    String path = e.getKey().location().getPath();
                    String jarItemPath = namespace + "_" + path + "_in_a_jar";
                    BiomeInAJarItem item = new BiomeInAJarItem(new FabricItemSettings().group(CreativeModeTab.TAB_MISC).maxCount(1).rarity(Rarity.UNCOMMON).fireproof().maxDamage(BiomesInJars.config.getOrDefault("filled_jar_uses", 250)), e.getKey());
                    registerJarItem(item, new ResourceLocation("biomesinjars", jarItemPath));
                }
            } else {
                LOGGER.error("[BIOMES IN JARS] Unable to register jar items! RegistryAccess was NULL!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void registerJarItem(BiomeInAJarItem item, ResourceLocation location) {
        Registry.register(Registry.ITEM, location, item);
        JAR_ITEMS.put(item.biome, item);
    }

    @Nullable
    public static Item getJarItemByBiome(ResourceKey<Biome> biome) {
        return JAR_ITEMS.get(biome);
    }

}
