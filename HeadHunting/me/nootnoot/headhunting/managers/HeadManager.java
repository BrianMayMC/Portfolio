package me.nootnoot.headhunting.managers;

import lombok.Getter;
import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.Head;

import java.util.ArrayList;

public class HeadManager {

	private static HeadManager instance;

	public static HeadManager getInstance(){
		if(instance != null)
			return instance;
		return instance = new HeadManager();
	}

	@Getter
	private final ArrayList<Head> heads;

	public HeadManager(){
		heads = new ArrayList<>();
		fillHeads();
	}

	public void fillHeads(){
		for(String path : HeadHunting.getInstance().getConfig().getConfigurationSection("heads.").getKeys(false)){
			path = "heads." + path;

			heads.add(new Head(
					HeadHunting.getInstance().getConfig().getString(path + ".name"),
					HeadHunting.getInstance().getConfig().getString(path + ".configname"),
					(ArrayList<String>) HeadHunting.getInstance().getConfig().getStringList(path + ".lore"),
					HeadHunting.getInstance().getConfig().getString(path + ".url"),
					HeadHunting.getInstance().getConfig().getInt(path + ".amount")
			));
		}
	}

	public Head byName(String name){
		for(Head head : getHeads()){
			if(head.getName().equalsIgnoreCase(name)){
				return head;
			}
		}
		return null;
	}
}
