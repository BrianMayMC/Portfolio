package me.nootnoot.jackpotmccrashgambling.files;

import me.nootnoot.framework.utils.FileCreator;
import me.nootnoot.jackpotmccrashgambling.JackpotMCCrashGambling;

public class MainMenuConfig extends FileCreator {
	public MainMenuConfig() {
		super("/menus", "mainmenu", JackpotMCCrashGambling.getInstance());
	}
}
