package me.nootnoot.jackpotmccoinflip.storage;

import com.google.common.reflect.TypeToken;
import me.nootnoot.framework.storagesystem.GsonFactory;
import me.nootnoot.jackpotmccoinflip.JackpotMCCoinflip;
import me.nootnoot.jackpotmccoinflip.entities.Coinflip;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CoinflipStorage {
	private ConcurrentMap<String, Coinflip> coinflips = new ConcurrentHashMap<>();

	public CoinflipStorage(){
		try {
			load();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void save() {
		File f = new File(JackpotMCCoinflip.getInstance().getDataFolder(), "coinflips.json");
		try (FileWriter fileWriter = new FileWriter(f)) {
			fileWriter.write(GsonFactory.getPrettyGson().toJson(coinflips));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void load() throws IOException {
		File f = new File(JackpotMCCoinflip.getInstance().getDataFolder(), "coinflips.json");
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		} else try (BufferedReader fileReader = new BufferedReader(new FileReader(f))) {
			ConcurrentHashMap<String, Coinflip> objectMap = GsonFactory.getCompactGson().fromJson(fileReader, new TypeToken<ConcurrentHashMap<String, Coinflip>>() {
			}.getType());
			if (objectMap == null) {
				coinflips = new ConcurrentHashMap<>();
			} else {
				coinflips = objectMap;
			}
		}
	}

	public void addCoinflip(Coinflip coinflip){
		coinflips.put(UUID.randomUUID().toString(), coinflip);
	}

	public void removeCoinflip(Coinflip coinflip){
		String uuid = "";
		for(String s : coinflips.keySet()){
			if(coinflips.get(s).equals(coinflip)){
				uuid = s;
			}
		}
		coinflips.remove(uuid);
	}

	public List<Coinflip> get(){
		return new ArrayList<>(coinflips.values());
	}
}
