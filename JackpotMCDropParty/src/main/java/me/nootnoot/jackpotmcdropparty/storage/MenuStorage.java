package me.nootnoot.jackpotmcdropparty.storage;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import me.nootnoot.jackpotmcdropparty.JackpotMCDropParty;
import me.nootnoot.jackpotmcdropparty.entities.SimpleItem;
import me.nootnoot.jackpotmcdropparty.utils.GsonFactory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MenuStorage {

	public ConcurrentHashMap<String, SimpleItem> menuItems = new ConcurrentHashMap<>();

	public List<SimpleItem> getMenuItems(){
		return new ArrayList<>(menuItems.values());
	}


	public MenuStorage(){
		try {
			load();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void AddItem(SimpleItem item){
		menuItems.put(UUID.randomUUID().toString(), item);
	}

	public void RemoveItem(SimpleItem item){
		String id = null;
		for(String s : menuItems.keySet()){
			SimpleItem i = menuItems.get(s);
			if(i.equals(item)){
				id = s;
			}
		}
		if(id != null) {
			menuItems.remove(id);
		}
	}

	public void load() throws IOException {
		File f = new File(JackpotMCDropParty.getInstance().getDataFolder(), "items.json");
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
		} else try (BufferedReader fileReader = new BufferedReader(new FileReader(f))) {
			ConcurrentHashMap<String, SimpleItem> objectMap = GsonFactory.getCompactGson().fromJson(fileReader, new TypeToken<ConcurrentHashMap<String, SimpleItem>>() {
			}.getType());
			if (objectMap == null) {
				menuItems = new ConcurrentHashMap<>();
			} else {
				menuItems = objectMap;
			}
		}
	}

	public void save(){
		File f = new File(JackpotMCDropParty.getInstance().getDataFolder(), "items.json");
		try (FileWriter fileWriter = new FileWriter(f)) {
			fileWriter.write(GsonFactory.getPrettyGson().toJson(menuItems));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
