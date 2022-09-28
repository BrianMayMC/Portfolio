package me.nootnoot.luckyblockfactions.storage;

import com.google.common.reflect.TypeToken;
import me.nootnoot.framework.storagesystem.GsonFactory;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;
import me.nootnoot.luckyblockfactions.entities.Faction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FactionStorage {

	private ConcurrentMap<String, Faction> factions = new ConcurrentHashMap<>();

	public FactionStorage(){
		try {
			load();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void save() {
		File f = new File(LuckyblockFactions.getInstance().getDataFolder(), "factions.json");
		try (FileWriter fileWriter = new FileWriter(f)) {
			fileWriter.write(GsonFactory.getPrettyGson().toJson(factions));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void load() throws IOException {
		File f = new File(LuckyblockFactions.getInstance().getDataFolder(), "factions.json");
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		} else try (BufferedReader fileReader = new BufferedReader(new FileReader(f))) {
			ConcurrentHashMap<String, Faction> objectMap = GsonFactory.getCompactGson().fromJson(fileReader, new TypeToken<ConcurrentHashMap<String, Faction>>() {
			}.getType());
			if (objectMap == null) {
				factions = new ConcurrentHashMap<>();
			} else {
				factions = objectMap;
			}
		}
	}

	public void createFaction(Faction faction){
		factions.put(UUID.randomUUID().toString(), faction);
	}

	public void disbandFaction(Faction faction){
		String id = null;
		for(String s : factions.keySet()){
			Faction f = factions.get(s);
			if(f.getName().equalsIgnoreCase(faction.getName())){
				id = s;
			}
		}
		factions.remove(id);
	}

	public List<Faction> get(){
		return new ArrayList<>(factions.values());
	}

}
