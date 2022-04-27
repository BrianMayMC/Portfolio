package me.nootnoot.albioncore.BLL.ecosystem.managers;

import lombok.Getter;
import me.nootnoot.albioncore.BLL.ecosystem.levels.CactusLevel;

import java.util.ArrayList;

public class CactusManager {
	@Getter
	private final ArrayList<CactusLevel> levels;

	public CactusManager(){
		levels = new ArrayList<>();
	}

	public CactusLevel GetLevel(int level){
		for(CactusLevel c : levels){
			if(c.getLevel() == level){
				return c;
			}
		}
		return null;
	}

	public void AddLevel(CactusLevel level){
		levels.add(level);
	}

	public void RemoveLevel(CactusLevel level){
		levels.remove(level);
	}

}
