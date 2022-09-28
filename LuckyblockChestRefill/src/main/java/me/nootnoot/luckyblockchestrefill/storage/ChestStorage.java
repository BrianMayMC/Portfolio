package me.nootnoot.luckyblockchestrefill.storage;

import com.google.gson.reflect.TypeToken;
import me.nootnoot.luckyblockchestrefill.LuckyblockChestRefill;
import me.nootnoot.luckyblockchestrefill.entities.RefillChest;
import me.nootnoot.luckyblockchestrefill.entities.RefillChestItem;
import me.nootnoot.luckyblockchestrefill.entities.SimpleLocation;
import org.bukkit.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ChestStorage {

	private ConcurrentMap<String, RefillChest> chests = new ConcurrentHashMap<>();
	private ConcurrentMap<String, RefillChestItem> chestItems = new ConcurrentHashMap<>();

	public ChestStorage(){
		try {
			loadChests();
			loadItems();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void addChest(RefillChest chest){
		chests.put(UUID.randomUUID().toString(), chest);
	}

	public void addItem(RefillChestItem item){
		chestItems.put(UUID.randomUUID().toString(), item);
	}

	public List<RefillChestItem> getItems(String type){
		List<RefillChestItem> newList = new ArrayList<>();
		for(String s : chestItems.keySet()){
			RefillChestItem item = chestItems.get(s);
			if(item.getType().equalsIgnoreCase(type)){
				newList.add(item);
			}
		}
		return newList;
	}

	public void removeChest(Location loc){
		String uuid = null;
		for(String id : chests.keySet()){
			RefillChest chest = chests.get(id);
			if(chest.getLocation().equals(new SimpleLocation(loc))){
				uuid = id;
			}
		}
		chests.remove(uuid);
		loc.getBlock().removeMetadata("refillchest", LuckyblockChestRefill.getInstance());
	}

	public void removeType(String type){
		List<String> ids = new ArrayList<>();
		for(String id : chestItems.keySet()){
			RefillChestItem item = chestItems.get(id);
			if(item.getType().equalsIgnoreCase(type)){
				ids.add(id);
			}
		}
		for(String s : ids){
			chestItems.remove(s);
		}
	}

	public void saveChests() {
		File f = new File(LuckyblockChestRefill.getInstance().getDataFolder(), "chests.json");
		try (FileWriter fileWriter = new FileWriter(f)) {
			fileWriter.write(GsonFactory.getPrettyGson().toJson(chests));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveItems(){
		File f = new File(LuckyblockChestRefill.getInstance().getDataFolder(), "chestitems.json");
		try (FileWriter fileWriter = new FileWriter(f)) {
			fileWriter.write(GsonFactory.getPrettyGson().toJson(chestItems));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void loadChests() throws IOException {
		File f = new File(LuckyblockChestRefill.getInstance().getDataFolder(), "chests.json");
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		} else try (BufferedReader fileReader = new BufferedReader(new FileReader(f))) {
			ConcurrentHashMap<String, RefillChest> objectMap = GsonFactory.getCompactGson().fromJson(fileReader, new TypeToken<ConcurrentHashMap<String, RefillChest>>() {
			}.getType());
			if (objectMap == null) {
				chests = new ConcurrentHashMap<>();
			} else {
				chests = objectMap;
			}
		}
	}

	public void loadItems() throws IOException {
		File f = new File(LuckyblockChestRefill.getInstance().getDataFolder(), "chestitems.json");
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		} else try (BufferedReader fileReader = new BufferedReader(new FileReader(f))) {
			ConcurrentHashMap<String, RefillChestItem> objectMap = GsonFactory.getCompactGson().fromJson(fileReader, new TypeToken<ConcurrentHashMap<String, RefillChestItem>>() {
			}.getType());
			if (objectMap == null) {
				chestItems = new ConcurrentHashMap<>();
			} else {
				chestItems = objectMap;
			}
		}
	}


	public List<RefillChest> getChests(){
		return new ArrayList<>(chests.values());
	}
}
