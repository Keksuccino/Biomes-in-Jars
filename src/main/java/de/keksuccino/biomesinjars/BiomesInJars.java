package de.keksuccino.biomesinjars;

import java.io.File;

import de.keksuccino.biomesinjars.biome.Biomes;
import de.keksuccino.biomesinjars.commands.server.ServerBiomeJarCommand;
import de.keksuccino.biomesinjars.commands.server.ServerSetChunkBiomeCommand;
import de.keksuccino.biomesinjars.entity.EntitiesClient;
import de.keksuccino.biomesinjars.entity.Entities;
import de.keksuccino.biomesinjars.item.Items;
import de.keksuccino.konkrete.config.Config;
import de.keksuccino.konkrete.config.exceptions.InvalidValueException;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("biomesinjars")
public class BiomesInJars {

	public static final String VERSION = "1.1.0";
	public static final String MOD_LOADER = "forge";

	public static final Logger LOGGER = LogManager.getLogger();

	public static final File MOD_DIR = new File("config/biomesinjars");
	public static final File INSTANCE_DATA_DIR = new File(".biomesinjars");
	public static final File TEMP_DIR = new File(INSTANCE_DATA_DIR.getPath() + "/temp");

	public static Config config;
	
    public BiomesInJars() {

		try {

			if (!MOD_DIR.isDirectory()) {
				MOD_DIR.mkdirs();
			}
//			if (!INSTANCE_DATA_DIR.isDirectory()) {
//				INSTANCE_DATA_DIR.mkdirs();
//			}

			updateConfig();

			EventHandler.init();

			if (FMLEnvironment.dist == Dist.CLIENT) {

				LOGGER.info("[BIOMES IN JARS] Loading mod in client-side mode!");

			} else {

				LOGGER.info("[BIOMES IN JARS] Loading mod in server-side mode!");

			}

			Entities.registerAll();

			EntitiesClient.registerAll();

			Biomes.registerAll();

			Items.registerAll();

			MinecraftForge.EVENT_BUS.register(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

	@SubscribeEvent
	public void onRegisterServerCommands(RegisterCommandsEvent e) {

		ServerSetChunkBiomeCommand.register(e.getDispatcher());
		ServerBiomeJarCommand.register(e.getDispatcher());

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

}
