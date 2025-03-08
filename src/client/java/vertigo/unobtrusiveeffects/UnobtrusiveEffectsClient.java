package vertigo.unobtrusiveeffects;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnobtrusiveEffectsClient implements ClientModInitializer {

	public static final String MOD_ID = "unobtrusive-effects";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final vertigo.unobtrusiveeffects.ModConfig CONFIG = vertigo.unobtrusiveeffects.ModConfig.createAndLoad();

	@Override
	public void onInitializeClient() {
		LOGGER.info("Initializing");
	}

}