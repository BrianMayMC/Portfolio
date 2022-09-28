package me.nootnoot.luckyblockoreregen.storage;

import com.google.common.reflect.TypeToken;
import lombok.Getter;
import me.nootnoot.luckyblockoreregen.LuckyBlockOreRegen;
import me.nootnoot.luckyblockoreregen.entities.RegenBlock;
import me.nootnoot.luckyblockoreregen.utils.GsonFactory;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class RegenBlockStorage {

	@Getter
	public ConcurrentHashMap<String, RegenBlock> blocks = new ConcurrentHashMap<>();
	private final File fileName;
	private int nextID;

	public RegenBlockStorage(){
		this.fileName = new File(LuckyBlockOreRegen.getInstance().getDataFolder(), "blocks.json");
		nextID = 1;
		try{
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() throws IOException {
		if (!fileName.exists()) {
			fileName.getParentFile().mkdirs();
			fileName.createNewFile();
		} else try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
			ConcurrentHashMap<String, RegenBlock> objectMap = GsonFactory.getCompactGson().fromJson(fileReader, new TypeToken<ConcurrentHashMap<String, RegenBlock>>() {
			}.getType());

			if (objectMap == null) {
				blocks = new ConcurrentHashMap<>();
			} else {
				blocks = objectMap;
			}
		}
	}

	public void createBlock(Material material, Location location){
		String ID = getNextID();
		RegenBlock regenBlock = new RegenBlock(ID, material, location);
		blocks.put(ID, regenBlock);
	}

	public void removeBlock(RegenBlock regenBlock){
		for(RegenBlock regenBlockFind : blocks.values()){
			if(regenBlock.getId().equalsIgnoreCase(regenBlockFind.getId())){
				blocks.remove(regenBlockFind.getId());
			}
		}
	}

	public void save() {
		try (FileWriter fileWriter = new FileWriter(fileName)) {
			fileWriter.write(GsonFactory.getPrettyGson().toJson(blocks));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getNextID() {
		// O(n) -> Iterates 60 times to find ID
		// O(1) -> Is this ID Free? Yes, return
		while(!checkID(this.nextID)) {
			this.nextID++;
		}
		return Integer.toString(nextID);
	}

	public boolean checkID(String id) {
		return !this.blocks.containsKey(id);
	}

	public boolean checkID(int id) {
		return this.checkID(Integer.toString(id));
	}
}
