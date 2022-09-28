package me.nootnoot.jackpotmcbountyhunters.files;

import me.nootnoot.framework.utils.FileCreator;
import me.nootnoot.jackpotmcbountyhunters.JackpotMCBountyHunters;

public class BountyTopMenuConfig extends FileCreator {
	public BountyTopMenuConfig() {
		super("/menus", "menu", JackpotMCBountyHunters.getInstance());
	}
}
