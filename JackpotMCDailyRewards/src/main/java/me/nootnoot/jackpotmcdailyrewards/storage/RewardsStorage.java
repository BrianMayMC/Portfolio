package me.nootnoot.jackpotmcdailyrewards.storage;

import com.google.gson.reflect.TypeToken;
import me.nootnoot.framework.storagesystem.GsonFactory;
import me.nootnoot.jackpotmcdailyrewards.JackpotMCDailyRewards;
import me.nootnoot.jackpotmcdailyrewards.entities.SimpleItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RewardsStorage {

	private ConcurrentHashMap<String, SimpleItem> items;

	public RewardsStorage(){
		try {
			load();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void load() throws IOException {
		File f = new File(JackpotMCDailyRewards.getInstance().getDataFolder(), "rewards.json");
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		} else try (BufferedReader fileReader = new BufferedReader(new FileReader(f))) {
			ConcurrentHashMap<String, SimpleItem> objectMap = GsonFactory.getCompactGson().fromJson(fileReader, new TypeToken<ConcurrentHashMap<String, SimpleItem>>() {
			}.getType());
			if (objectMap == null) {
				items = new ConcurrentHashMap<>();
			} else {
				items = objectMap;
			}
		}
	}

	public void save(){
		File f = new File(JackpotMCDailyRewards.getInstance().getDataFolder(), "rewards.json");
		try (FileWriter fileWriter = new FileWriter(f)) {
			fileWriter.write(GsonFactory.getPrettyGson().toJson(items));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void add(SimpleItem item){
		items.put(UUID.randomUUID().toString(), item);
	}

	public void remove(SimpleItem item){
		String id = null;
		for(String s : items.keySet()){
			SimpleItem i = items.get(s);
			if(i.equals(item)){
				id = s;
			}
		}
		if(id != null) {
			items.remove(id);
		}
	}

	public List<SimpleItem> get(){
		return new ArrayList<>(items.values());
	}


}
