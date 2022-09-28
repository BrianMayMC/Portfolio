package me.nootnoot.jackpotmccrashgambling.storage;

import com.google.common.reflect.TypeToken;
import me.nootnoot.framework.storagesystem.GsonFactory;
import me.nootnoot.jackpotmccrashgambling.JackpotMCCrashGambling;
import me.nootnoot.jackpotmccrashgambling.entities.CrashGame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CrashStorage {

	private ConcurrentMap<String, CrashGame> crashGames = new ConcurrentHashMap<>();

	public CrashStorage() {
		try {
			loadGames();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addGame(CrashGame game) {
		this.crashGames.put(UUID.randomUUID().toString(), game);
	}


	public void saveGames() {
		File f = new File(JackpotMCCrashGambling.getInstance().getDataFolder(), "crashgames.json");
		try (FileWriter fileWriter = new FileWriter(f)) {
			fileWriter.write(GsonFactory.getPrettyGson().toJson(crashGames));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void loadGames() throws IOException {
		File f = new File(JackpotMCCrashGambling.getInstance().getDataFolder(), "crashgames.json");
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		} else try (BufferedReader fileReader = new BufferedReader(new FileReader(f))) {
			ConcurrentHashMap<String, CrashGame> objectMap = GsonFactory.getCompactGson().fromJson(fileReader, new TypeToken<ConcurrentHashMap<String, CrashGame>>() {
			}.getType());
			crashGames = Objects.requireNonNullElseGet(objectMap, ConcurrentHashMap::new);
		}
	}

	public List<CrashGame> getGames() {
		return new ArrayList<>(crashGames.values());
	}
}
