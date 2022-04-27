package me.nootnoot.albioncore.BLL.ecosystem.managers;

import lombok.Getter;
import me.nootnoot.albioncore.BLL.ecosystem.interfaces.Manager;
import me.nootnoot.albioncore.BLL.ecosystem.levels.WoodcuttingLevel;

import java.util.ArrayList;

public class WoodcuttingManager implements Manager<WoodcuttingLevel> {
	@Getter
	private final ArrayList<WoodcuttingLevel> levels;

	public WoodcuttingManager(){
		levels = new ArrayList<>();
	}

	@Override
	public WoodcuttingLevel GetLevel(int level) {
		for(WoodcuttingLevel w : levels){
			if(w.getLevel() == level){
				return w;
			}
		}
		return null;
	}

	@Override
	public void AddLevel(WoodcuttingLevel level) {
		levels.add(level);
	}

	@Override
	public void RemoveLevel(WoodcuttingLevel level) {
		levels.remove(level);
	}
}
