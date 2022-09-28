package me.nootnoot.jackpotmcplaytimerewards.files;

import me.nootnoot.framework.utils.FileCreator;
import me.nootnoot.jackpotmcplaytimerewards.JackpotMCPlaytimeRewards;
import org.bukkit.plugin.java.JavaPlugin;

public class LevelMenuConfig extends FileCreator {
	public LevelMenuConfig() {
		super("/menus", "levelmenu", JackpotMCPlaytimeRewards.getInstance());
	}
}
