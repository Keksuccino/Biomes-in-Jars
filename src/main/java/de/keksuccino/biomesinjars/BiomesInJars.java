package de.keksuccino.biomesinjars;

import java.io.File;

import de.keksuccino.biomesinjars.biome.Biomes;
import de.keksuccino.biomesinjars.entity.Entities;
import de.keksuccino.biomesinjars.entity.EntitiesClient;
import de.keksuccino.konkrete.Konkrete;
import de.keksuccino.konkrete.config.Config;
import de.keksuccino.konkrete.config.exceptions.InvalidValueException;
import de.keksuccino.biomesinjars.commands.server.*;
import de.keksuccino.biomesinjars.item.Items;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomesInJars implements ModInitializer {

	public static final String VERSION = "1.0.1";
	public static final String MOD_LOADER = "fabric";

	public static final Logger LOGGER = LogManager.getLogger();

	public static Config config;

	public static final File MOD_DIR = new File("config/biomesinjars");
	public static final File INSTANCE_DATA_DIR = new File(".biomesinjars");
	public static final File TEMP_DIR = new File(INSTANCE_DATA_DIR.getPath() + "/temp");
	
    @Override
    public void onInitialize() {

    	try {

			if (!MOD_DIR.isDirectory()) {
				MOD_DIR.mkdirs();
			}
//			if (!INSTANCE_DATA_DIR.isDirectory()) {
//				INSTANCE_DATA_DIR.mkdirs();
//			}

			updateConfig();

			EventHandler.init();

			if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {

				LOGGER.info("[BIOMES IN JARS] Loading mod in client-side mode!");

				EntitiesClient.registerAll();

			} else {

				LOGGER.info("[BIOMES IN JARS] Loading mod in server-side mode!");

			}

			Biomes.registerAll();

			Entities.registerAll();

			Items.registerAll();

			registerServerCommands();

			Konkrete.getEventHandler().registerEventsFrom(this);
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

	public void registerServerCommands() {

		CommandRegistrationCallback.EVENT.register((dispatcher, context, environment) -> {

			ServerSetChunkBiomeCommand.register(dispatcher);

		});

	}

    public static void updateConfig() {
    	try {

			config = new Config(MOD_DIR.getPath() + "/config.txt");

    		config.registerValue("convert_to_dead_land", true, "general", "If chunks around the empty jar should get converted to dead land when extracting a biome.");
			config.registerValue("filled_jar_uses", 250, "general");
			config.registerValue("allow_overriding_dead_land", false, "general", "If it should be possible to override dead land biomes with filled jars.");
			
			config.syncConfig();
			
			config.clearUnusedValues();

		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
	}

	public static boolean isKonkreteLoaded() {
		try {
			Class.forName("de.keksuccino.konkrete.Konkrete");
			return true;
		} catch (Exception e) {}
		return false;
	}

	public static String getMinecraftVersion() {
		return SharedConstants.getCurrentVersion().getReleaseTarget();
	}

}