package me.nootnoot.albioncore.BLL.ecosystem.managers;

import lombok.Getter;
import me.nootnoot.albioncore.BLL.ecosystem.interfaces.Manager;
import me.nootnoot.albioncore.BLL.ecosystem.levels.FarmingLevel;

import java.util.ArrayList;

public class FarmingManager implements Manager<FarmingLevel> {
	@Getter
	private final ArrayList<FarmingLevel> levels;

	public FarmingManager(){
		levels = new ArrayList<>();
	}

	public FarmingLevel GetLevel(int level){
		for(FarmingLevel f : levels){
			if(f.getLevel() == level){
				return f;
			}
		}
		return null;
	}

	@Override
	public void AddLevel(FarmingLevel level) {
		levels.add(level);
	}

	@Override
	public void RemoveLevel(FarmingLevel level) {
		levels.remove(level);
	}
}
