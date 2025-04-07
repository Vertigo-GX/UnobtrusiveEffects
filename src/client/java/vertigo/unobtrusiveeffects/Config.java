package vertigo.unobtrusiveeffects;

import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class Config {

	private static final String SEPARATOR = " = ";
	private static final String DISABLE_FIRE_OVERLAY = "disableFireOverlay";
	private static final String SWIRL_CULLING_DISTANCE = "swirlCullingDistance";

	public boolean disableFireOverlay = true;
	public float swirlCullingDistance = 2.5f;

	public Config() {
		if (!read()) {
			write();
		}
	}

	public void write() {
		File file = getFile();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(DISABLE_FIRE_OVERLAY + SEPARATOR + disableFireOverlay + System.lineSeparator());
			writer.write(SWIRL_CULLING_DISTANCE + SEPARATOR + swirlCullingDistance);
		} catch (IOException e) {
			UnobtrusiveEffectsClient.LOGGER.error("Failed to write config ({})", file.getPath());
		}
	}

	public boolean read() {
		File file = getFile();
		if (!file.exists()) {
			return false;
		}
		try (BufferedReader reader = new BufferedReader((new FileReader(file)))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] segments = line.split(SEPARATOR);
				if (segments.length != 2 || segments[0].isEmpty() || segments[1].isEmpty()) {
					continue;
				}
				switch (segments[0]) {
					case DISABLE_FIRE_OVERLAY -> disableFireOverlay = segments[1].equals("true");
					case SWIRL_CULLING_DISTANCE -> {
						try {
							swirlCullingDistance = Float.parseFloat(segments[1]);
						} catch (NumberFormatException e) {
							continue;
						}
					}
				}
			}
		} catch (IOException e) {
			UnobtrusiveEffectsClient.LOGGER.error("Failed to read config ({})", file.getPath());
		}
		return true;
	}

	private File getFile() {
		return FabricLoader.getInstance().getGameDir().resolve("config").resolve(UnobtrusiveEffectsClient.MOD_ID + ".ini").toFile();
	}

}