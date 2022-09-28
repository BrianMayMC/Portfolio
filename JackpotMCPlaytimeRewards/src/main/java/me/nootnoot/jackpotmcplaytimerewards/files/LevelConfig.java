package me.nootnoot.jackpotmcplaytimerewards.files;

import me.nootnoot.framework.utils.FileCreator;
import me.nootnoot.jackpotmcplaytimerewards.JackpotMCPlaytimeRewards;

public class LevelConfig extends FileCreator {
	public LevelConfig() {
		super("/items", "level", JackpotMCPlaytimeRewards.getInstance());
	}
}
