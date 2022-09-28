package me.nootnoot.framework.storagesystem;

import com.google.common.reflect.TypeToken;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class JsonStorage <T>{


	public ConcurrentHashMap<String, T> load(String fileName, JavaPlugin instance) throws IOException {
		File f = new File(instance.getDataFolder(), fileName + ".json");
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		}
		else try (BufferedReader fileReader = new BufferedReader(new FileReader(f))) {
			ConcurrentHashMap<String, T> objectMap = GsonFactory.getCompactGson().fromJson(fileReader, new TypeToken<ConcurrentHashMap<String, T>>() {
			}.getType());

			if (objectMap == null) {
				return new ConcurrentHashMap<>();
			} else {
				return objectMap;
			}
		}
		return new ConcurrentHashMap<>();
	}

	public void save(String fileName, ConcurrentHashMap<String, T> objectMap) {
		try (FileWriter fileWriter = new FileWriter(fileName)) {
			fileWriter.write(GsonFactory.getPrettyGson().toJson(objectMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
