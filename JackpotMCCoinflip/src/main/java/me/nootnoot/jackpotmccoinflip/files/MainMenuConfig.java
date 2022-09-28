package me.nootnoot.jackpotmccoinflip.files;

import me.nootnoot.framework.utils.FileCreator;
import me.nootnoot.jackpotmccoinflip.JackpotMCCoinflip;

public class MainMenuConfig extends FileCreator {
	public MainMenuConfig() {
		super("/menus", "mainmenu", JackpotMCCoinflip.getInstance());
	}
}
