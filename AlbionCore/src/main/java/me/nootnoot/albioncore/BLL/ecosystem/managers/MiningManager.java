package me.nootnoot.albioncore.BLL.ecosystem.managers;

import lombok.Getter;
import me.nootnoot.albioncore.BLL.ecosystem.interfaces.Manager;
import me.nootnoot.albioncore.BLL.ecosystem.levels.MiningLevel;

import java.util.ArrayList;

public class MiningManager implements Manager<MiningLevel> {
	@Getter
	private final ArrayList<MiningLevel> levels;

	public MiningManager(){
		levels = new ArrayList<>();
	}
	@Override
	public MiningLevel GetLevel(int level) {
		for(MiningLevel m : levels){
			if(m.getLevel() == level){
				return m;
			}
		}
		return null;
	}

	@Override
	public void AddLevel(MiningLevel level) {
		levels.add(level);
	}

	@Override
	public void RemoveLevel(MiningLevel level) {
		levels.remove(level);
	}
}
