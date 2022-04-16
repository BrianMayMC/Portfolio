package me.nootnoot.headhunting.managers;

import lombok.Getter;
import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class LevelManager {

	@Getter
	private final ArrayList<Level> levels;

	private static LevelManager instance;

	public static LevelManager getInstance(){
		if(instance != null)
			return instance;
		return instance = new LevelManager();
	}

	public LevelManager(){
		levels = new ArrayList<>();
		fillLevels();
	}

	public void fillLevels(){
		for(String path : HeadHunting.getInstance().getConfig().getConfigurationSection("levels.").getKeys(false)){
			String fullpath = "levels." + path;

			ArrayList<String> acceptedEntities = new ArrayList<>(HeadHunting.getInstance().getConfig().getStringList(fullpath + ".acceptedEntities"));
			ArrayList<String> rewards = new ArrayList<>(HeadHunting.getInstance().getConfig().getStringList(fullpath + ".rewards"));

			levels.add(new Level(
					HeadHunting.getInstance().getConfig().getInt(fullpath + ".requiredHeads"),
					acceptedEntities,
					rewards,
					HeadHunting.getInstance().getConfig().getInt(fullpath + ".cost"),
					Integer.parseInt(path)
			));
		}
	}

	public Level byInt(int level){
		for(Level levels : levels){
			if(levels.getLevel() == level){
				return levels;
			}
		}
		return null;
	}
}
